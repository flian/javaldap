package org.javaldap.server.acl;

/*
The JavaLDAP Server
Copyright (C) 2000  Clayton Donley (donley@linc-dev.com) - All Rights Reserved

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/

/**
 * Class for checking access control lists (ACLs)
 * @author: Clayton Donley
 */

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.javaldap.server.syntax.DirectoryString;
import org.javaldap.server.Credentials;
import org.javaldap.server.util.ServerConfig;
import org.javaldap.server.util.Logger;

public class ACLChecker {

	private static Hashtable acls = null;
	private static ACLChecker instance = null;
	private boolean aclCheck = true;
	private DirectoryString rootUser = null;

	private static final DirectoryString ATTR_ALL = new DirectoryString("[all]");
	private static final DirectoryString ROOTENTRY = new DirectoryString("[root]");

	public static final Character PERM_ADD = new Character('a');
	public static final Character PERM_DELETE = new Character('d');
	public static final Character PERM_EXPORT = new Character('e');
	public static final Character PERM_IMPORT = new Character('i');
	public static final Character PERM_RENAMEDN = new Character('n');
	public static final Character PERM_BROWSEDN = new Character('b');
	public static final Character PERM_RETURNDN = new Character('t');
	public static final Character PERM_READ = new Character('r');
	public static final Character PERM_SEARCH = new Character('s');
	public static final Character PERM_WRITE = new Character('w');
	public static final Character PERM_OBLITERATE = new Character('o');
	public static final Character PERM_COMPARE = new Character('c');
	public static final Character PERM_MAKE = new Character('m');

/**
 * ACLChecker constructor comment.
 */
private ACLChecker() {
	super();
	if (((String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_ACLCHECK)).equals("0")) {
		aclCheck = false;
	} else {
		rootUser = new DirectoryString((String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_ROOTUSER));
	}
	
}
	public static ACLChecker getInstance() {
		if (instance == null) {
			instance = new ACLChecker();
		}
		return instance;
	}
	public void initialize() {
		this.acls = new Hashtable();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("acls.prop"));
		} catch (FileNotFoundException fnfe) {
			Logger.getInstance().log(Logger.LOG_NORMAL,"File containing ACLs not found");
			return;
		}

		try {
			while (br.ready()) {
				String acl = br.readLine();
				if (acl != null && acl.indexOf('|') != -1) {
					StringTokenizer aclTok = new StringTokenizer(acl,"|");
					DirectoryString aclLoc = new DirectoryString(aclTok.nextToken());
					System.out.println(aclLoc);
					if (aclLoc.equals(ROOTENTRY)) {
						aclLoc = new DirectoryString("");
					}
					Vector myAcls = null;
					if (this.acls.containsKey(aclLoc)) {
						myAcls = (Vector)this.acls.get(aclLoc);
					} else {
						myAcls = new Vector();
					}
					myAcls.addElement(new ACL(aclTok.nextToken()));
					this.acls.put(aclLoc,myAcls);
				}
			}
			br.close();
		} catch (IOException ioe) {
		}
		
	}
	public boolean isAllowed(Credentials creds, Character action, DirectoryString target) {
		boolean allowed = false;
		ACL matchedACL = null;
		DirectoryString matchedLoc = null;
		DirectoryString subject = null;

		if (aclCheck == false) {
			return true;
		}
		
		if (creds != null) {
			subject = creds.getUser();
		}

		if (subject.equals(rootUser)) {
			return true;
		}
		
		Enumeration aclEnum = acls.keys();
		while (aclEnum.hasMoreElements()) {
			DirectoryString aclLoc = (DirectoryString)aclEnum.nextElement();
			if (target.endsWith(aclLoc)) {
				Enumeration oneAclSet = ((Vector)acls.get(aclLoc)).elements();
				while (oneAclSet.hasMoreElements()) {
					ACL oneAcl = (ACL)oneAclSet.nextElement();
					if (oneAcl.isScopeSubtree() || target.equals(aclLoc)) {
						if (oneAcl.getSubjectType() == oneAcl.SUBJECT_PUBLIC ||
							(oneAcl.getSubjectType() == oneAcl.SUBJECT_AUTHZID && oneAcl.isAuthzDN() &&
								oneAcl.getSubject().equals(subject)) ||
							(oneAcl.getSubjectType() == oneAcl.SUBJECT_THIS && subject.equals(target))) {
							if (oneAcl.isScopeSubtree() || subject.equals(aclLoc)) {
								if (oneAcl.getPermission().contains(action)) {
									if (matchedACL == null) {
										matchedACL = oneAcl;
										matchedLoc = aclLoc;
										if (oneAcl.isGrant()) {
											allowed = true;
										} else {
											allowed = false;
										}
									} else {
										if (!oneAcl.isScopeSubtree()) {
											if (!matchedACL.isScopeSubtree()) {
												if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
													if (!oneAcl.isGrant()) {
														allowed = false;
													} else {
														allowed = true;
													}
													matchedACL = oneAcl;
													matchedLoc = aclLoc;
												} else {
													if (!oneAcl.isGrant() || !matchedACL.isGrant()) {
														allowed = false;
													} else {
														allowed = true;
													}
												}
											} else {
												matchedACL = oneAcl;
												matchedLoc = aclLoc;
												if (oneAcl.isGrant()) {
													allowed = true;
												} else {
													allowed = false;
												}
											}
										} else if (matchedLoc.length() < aclLoc.length()) {
											matchedACL = oneAcl;
											matchedLoc = aclLoc;
											if (oneAcl.isGrant()) {
												allowed = true;
											} else {
												allowed = false;
											}
										} else if (matchedLoc.length() == aclLoc.length()) {
											if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
												matchedACL = oneAcl;
												matchedLoc = aclLoc;
												if (oneAcl.isGrant()) {
													allowed = true;
												} else {
													allowed = false;
												}
											} else if (matchedACL.getSubjectType() == oneAcl.getSubjectType()) {
												if (!matchedACL.isGrant() || !oneAcl.isGrant()) {
													allowed = false;
												} else {
													allowed = true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return allowed;
	}
	public boolean isAllowed(Credentials creds, Character action, DirectoryString target, DirectoryString attr) {
		boolean allowed = false;
		ACL matchedACL = null;
		DirectoryString matchedLoc = null;

		DirectoryString subject = null;

		if (aclCheck == false) {
			return true;
		}
		
		if (creds != null) {
			subject = creds.getUser();
		}

		if (subject.equals(rootUser)) {
			return true;
		}
			
		Enumeration aclEnum = acls.keys();
		while (aclEnum.hasMoreElements()) {
			DirectoryString aclLoc = (DirectoryString)aclEnum.nextElement();
			if (target.endsWith(aclLoc)) {
				Enumeration oneAclSet = ((Vector)acls.get(aclLoc)).elements();
				while (oneAclSet.hasMoreElements()) {
					ACL oneAcl = (ACL)oneAclSet.nextElement();
					if (oneAcl.isScopeSubtree() || target.equals(aclLoc)) {
						if (oneAcl.getSubjectType() == oneAcl.SUBJECT_PUBLIC ||
							(oneAcl.getSubjectType() == oneAcl.SUBJECT_AUTHZID && oneAcl.isAuthzDN() &&
								oneAcl.getSubject().equals(subject)) ||
							(oneAcl.getSubjectType() == oneAcl.SUBJECT_THIS && subject.equals(target))) {
							if (oneAcl.isScopeSubtree() || subject.equals(aclLoc)) {
								if (oneAcl.getPermission().contains(action)) {
									if (oneAcl.getAttr().contains(attr) || oneAcl.getAttr().contains(ATTR_ALL)) {
										if (matchedACL == null) {
											matchedACL = oneAcl;
											matchedLoc = aclLoc;
											if (oneAcl.isGrant()) {
												allowed = true;
											} else {
												allowed = false;
											}
										} else {
											if (!oneAcl.isScopeSubtree()) {
												if (!matchedACL.isScopeSubtree()) {
													if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
														if (!oneAcl.isGrant()) {
															allowed = false;
														} else {
															allowed = true;
														}
														matchedACL = oneAcl;
														matchedLoc = aclLoc;
													} else {
														if (!oneAcl.isGrant() || !matchedACL.isGrant()) {
															allowed = false;
														} else {
															allowed = true;
														}
													}
												} else {
													matchedACL = oneAcl;
													matchedLoc = aclLoc;
													if (oneAcl.isGrant()) {
														allowed = true;
													} else {
														allowed = false;
													}
												}
											} else if (matchedLoc.length() < aclLoc.length()) {
												matchedACL = oneAcl;
												matchedLoc = aclLoc;
												if (oneAcl.isGrant()) {
													allowed = true;
												} else {
													allowed = false;
												}
											} else if (matchedLoc.length() == aclLoc.length()) {
												if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
													matchedACL = oneAcl;
													matchedLoc = aclLoc;
													if (oneAcl.isGrant()) {
														allowed = true;
													} else {
														allowed = false;
													}
												} else if (matchedACL.getSubjectType() == oneAcl.getSubjectType()) {
													if (!matchedACL.isGrant() || !oneAcl.isGrant()) {
														allowed = false;
													} else {
														allowed = true;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return allowed;
	}
}
