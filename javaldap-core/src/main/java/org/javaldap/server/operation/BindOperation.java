package org.javaldap.server.operation;

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
 * BindOperation performs an LDAP Bind operation. It currently ignores credentials
 * and returns SUCCESS.
 *
 * @author: Clayton Donley
 */

import java.util.*;
import org.javaldap.ldapv3.*;
import org.javaldap.server.Entry;
import org.javaldap.server.Credentials;
import org.javaldap.server.backend.BackendHandler;
import org.javaldap.server.util.DirectoryBindException;
import org.javaldap.server.util.DirectoryException;
import org.javaldap.server.util.ServerConfig;
import org.javaldap.server.syntax.DirectoryString;

public class BindOperation implements Operation {

	LDAPMessage request = null;
	LDAPMessage response = null;
	boolean success = false;
	Credentials creds = null;

	private static final DirectoryString USERPASSWORD = new DirectoryString("userpassword");
	public BindOperation(LDAPMessage request) {
		this.request = request;
	}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 10:38:16 AM)
 * @return org.javaldap.server.Credentials
 */
public org.javaldap.server.Credentials getCreds() {
	return creds;
}
	public LDAPMessage getResponse() {
		return this.response;
	}
	public void perform() throws DirectoryBindException {
		
		this.response = new LDAPMessage();

		LDAPMessageChoice op = new LDAPMessageChoice();
		BindResponse bindResponse = new BindResponse();

		bindResponse.resultCode = new LDAPResultEnum(0);
		bindResponse.matchedDN = new byte[0];
		bindResponse.errorMessage = new byte[0];
		bindResponse.referral = new Referral();
		bindResponse.serverSaslCreds = new byte[0];

		op.choiceId = LDAPMessageChoice.BINDRESPONSE_CID;
		op.bindResponse = bindResponse;

		this.response.messageID = this.request.messageID;
		this.response.protocolOp = op;

		
		if (this.request.protocolOp.bindRequest != null) {
			AuthenticationChoice ac = this.request.protocolOp.bindRequest.authentication;
			DirectoryString subject = new DirectoryString(this.request.protocolOp.bindRequest.name);
			// Currently only checking Simple Auth, need to add SASL support and support for encrypted passwords
			if (ac.choiceId == ac.SIMPLE_CID) {
				// First check for the Root User
				String pw = new String(ac.simple);
				if (new DirectoryString((String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_ROOTUSER)).equals(subject)) {
					if (((String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_ROOTPW)).equals(pw)) {
						creds = new Credentials();
						creds.setUser(subject);
						return;
					}
					bindResponse.resultCode = new LDAPResultEnum(49);
					return;
				}
				Entry bindEnt = null;
				try {
					bindEnt = BackendHandler.Handler().getByDN(subject);
				} catch (DirectoryException de) {
				}
				if (bindEnt == null || !bindEnt.containsKey(USERPASSWORD)) {
					bindResponse.resultCode = new LDAPResultEnum(49);
				} else {
					if (pw.equals(((DirectoryString)((Vector)bindEnt.get(USERPASSWORD)).elementAt(0)).toString())) {
						creds = new Credentials();
						creds.setUser(subject);
					} else {
						bindResponse.resultCode = new LDAPResultEnum(49);
					}
				}
			}
		}
		return;
	}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 10:38:16 AM)
 * @param newCreds org.javaldap.server.Credentials
 */
public void setCreds(org.javaldap.server.Credentials newCreds) {
	creds = newCreds;
}
}
