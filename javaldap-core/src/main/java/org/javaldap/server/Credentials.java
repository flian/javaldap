package org.javaldap.server;

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
 * Class holding a session's credentials
 * @author: Administrator
 */

import org.javaldap.server.syntax.DirectoryString;

public class Credentials {

	private DirectoryString user = null;
	private byte authType = AUTH_NONE;
	private String saslMech = null;

	public static final byte AUTH_NONE = 0;
	public static final byte AUTH_SIMPLE = 1;
	public static final byte AUTH_SASL = 2;
		
/**
 * Credentials constructor comment.
 */
public Credentials() {
	super();
	setUser(new DirectoryString(""));
}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:03:58 AM)
 * @return byte
 */
public byte getAuthType() {
	return authType;
}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:03:58 AM)
 * @return java.lang.String
 */
public java.lang.String getSaslMech() {
	return saslMech;
}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:03:58 AM)
 * @return org.javaldap.server.syntax.DirectoryString
 */
public org.javaldap.server.syntax.DirectoryString getUser() {
	return user;
}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:03:58 AM)
 * @param newAuthType byte
 */
public void setAuthType(byte newAuthType) {
	authType = newAuthType;
}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:03:58 AM)
 * @param newSaslMech java.lang.String
 */
public void setSaslMech(java.lang.String newSaslMech) {
	saslMech = newSaslMech;
}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:03:58 AM)
 * @param newUser org.javaldap.server.syntax.DirectoryString
 */
public void setUser(org.javaldap.server.syntax.DirectoryString newUser) {
	user = newUser;
}
}
