package org.javaldap.server.backend;

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
 * The BackendHandler is responsible for determining which backend (or backends) need
 * to be called to provide or store information.
 *
 * @author: Clayton Donley
 */
 
import java.util.*;
import org.javaldap.ldapv3.*;
import org.javaldap.server.Entry;
import org.javaldap.server.EntrySet;
import org.javaldap.server.EntryChange;
import org.javaldap.server.Credentials;
import org.javaldap.server.util.Logger;
import org.javaldap.server.util.DirectorySchemaViolation;
import org.javaldap.server.util.DirectoryException;
import org.javaldap.server.util.ServerConfig;
import org.javaldap.server.schema.SchemaChecker;
import org.javaldap.server.acl.ACLChecker;
import org.javaldap.server.syntax.DirectoryString;

public class BackendHandler {

	private static BackendHandler handler = null;
	private static Hashtable handlerTable = null;

	private BackendHandler() {
		super();
		handlerTable = new Hashtable();
		//handlerTable.put(new DirectoryString("dc=manning,dc=com"),new BackendMemory());
		//handlerTable.put(new DirectoryString("dc=javaldap,dc=com"),new BackendMemory());
		//handlerTable.put(new DirectoryString("dc=test,dc=javaldap,dc=com"),new BackendMemory());
		handlerTable.put(new DirectoryString("cn=schema"),new BackendSchema());
		handlerTable.put(new DirectoryString(""),new BackendRoot());

		// Read Backend Properties File
		Properties schemaProp = new Properties();
		try {
			java.io.FileInputStream is = new java.io.FileInputStream((String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_SERVER_BACKENDS));
			schemaProp.load(is);
			is.close();
		} catch (Exception e) {
		}

		// Create backends accordingly
		int numBackends = new Integer((String)schemaProp.get("backend.num")).intValue();
		for (int beCount = 0; beCount <numBackends; beCount++) {
			String suffix = (String)schemaProp.get("backend." + beCount + ".root");
			String backendType = (String)schemaProp.get("backend." + beCount + ".type");
			System.out.println("Backend root: " + suffix + " type: " + backendType);
			try {
				handlerTable.put(new DirectoryString(suffix),Class.forName(backendType).newInstance());
			} catch (Exception e) {
			}
		}

	}
	public LDAPResultEnum add(Credentials creds,Entry entry) throws DirectorySchemaViolation {
		SchemaChecker.getInstance().checkEntry(entry);
		if (!ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_ADD,entry.getName())) {
			return new LDAPResultEnum(50);
		}
		
		Backend backend = pickBackend(entry.getName());

		if (backend != null) {
			return backend.add(entry);
		} else {
			return new LDAPResultEnum(32);
		}
	}
	public LDAPResultEnum delete(Credentials creds,DirectoryString name) {
		Backend backend = pickBackend(name);
		if (!ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_DELETE,name)) {
			return new LDAPResultEnum(50);
		}
		return backend.delete(name);
	}
	public Vector get(DirectoryString base, int scope, Filter filter,
						 boolean typesOnly, Vector attributes) throws DirectoryException {

		Vector results = new Vector();
		Vector backends = pickBackends(base,scope);
		Enumeration backEnum = backends.elements();
		while (backEnum.hasMoreElements()) {
			Backend backend = (Backend)backEnum.nextElement();
			EntrySet partResults = backend.get(base,scope,filter,typesOnly,attributes);
			if (partResults.hasMore()) {
				results.addElement(partResults);
			}
		}
		return results;
	}
	public Entry getByDN(DirectoryString dn) throws DirectoryException {
		Backend backend = pickBackend(dn);
		return backend.getByDN(dn);
	}
	Hashtable getHandlerTable() {
		return handlerTable;
	}
	public static BackendHandler Handler() {
		if (handler == null) {
			handler = new BackendHandler();
		}
		return handler;
	}
	public void modify(Credentials creds, DirectoryString name,Vector changeEntries) throws DirectoryException {
		Enumeration changeEnum = changeEntries.elements();
		while (changeEnum.hasMoreElements()) {
			EntryChange change = (EntryChange) changeEnum.nextElement();
			int changeType = change.getModType();
			DirectoryString attr = change.getAttr();
			if (changeType == ModifyRequestSeqOfSeqEnum.ADD && !ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_WRITE,name,attr)) {
				throw new DirectoryException(50);
			}
			if (changeType == ModifyRequestSeqOfSeqEnum.DELETE && !ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_OBLITERATE,name,attr)) {
				throw new DirectoryException(50);
			}
			if (changeType == ModifyRequestSeqOfSeqEnum.REPLACE && (!ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_WRITE,name,attr) ||
				!ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_OBLITERATE,name,attr))) {
				throw new DirectoryException(50);
			}
		}	
		Backend backend = pickBackend(name);
		backend.modify(name,changeEntries);
	}
	private Backend pickBackend(DirectoryString entryName) {
		Backend selected = null;
		int selLength = -1;
		Enumeration backEnum = handlerTable.keys();
		while (backEnum.hasMoreElements()) {
			DirectoryString base = (DirectoryString)backEnum.nextElement();
			if (entryName.endsWith(base) && base.length() > selLength) {
				selected = (Backend)handlerTable.get(base);
				selLength = base.length();
				//Logger.getInstance().log(Logger.LOG_DEBUG,"Switched to " + selected.getClass().getName() + " backend for: " + base);
			}
		}
		return selected;
	}
	private Vector pickBackends(DirectoryString entryName,int scope) {
		Vector backs = new Vector();
		Enumeration backEnum = handlerTable.keys();
		while (backEnum.hasMoreElements()) {
			DirectoryString base = (DirectoryString)backEnum.nextElement();
			if (entryName.endsWith(base) || (scope != SearchRequestEnum.BASEOBJECT && base.endsWith(entryName))) {
				backs.addElement((Backend)handlerTable.get(base));
				//Logger.getInstance().log(Logger.LOG_DEBUG,"Selected Backend for: " + base);
			}
		}
		return backs;
	}
	public LDAPResultEnum rename(Credentials creds,DirectoryString oldname, DirectoryString newname) throws DirectoryException {
		if (!ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_RENAMEDN,oldname) ||
			  !ACLChecker.getInstance().isAllowed(creds,ACLChecker.PERM_ADD,newname)) {
			return new LDAPResultEnum(50);
		}
		Backend backend = pickBackend(oldname);
		Backend newBackend = pickBackend(newname);
		if (backend == newBackend) {
			return backend.rename(oldname,newname);
		}
		Entry oldEntry = backend.getByDN(oldname);
		oldEntry.setName(newname);
		newBackend.add(oldEntry);
		backend.delete(oldname);
		return new LDAPResultEnum(0);
	}
}
