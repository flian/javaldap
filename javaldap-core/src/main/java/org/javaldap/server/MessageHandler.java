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
 * The MessageHandler is responsible for looking at BER-decoded objects to
 * understand the LDAP operation requests and call appropriate operations to
 * return results.
 *
 * @author: Clayton Donley
 */
import java.net.*;
import java.io.*;
import java.util.*;

import org.javaldap.ldapv3.*;
import org.javaldap.server.operation.*;
import org.javaldap.server.util.DirectoryBindException;
import org.javaldap.server.util.DirectoryException;

import org.javaldap.server.syntax.DirectoryString;

import com.ibm.asn1.*;

public class MessageHandler {
	private Connection connection = null;

	//private boolean debug = false;

	public MessageHandler() {
		setConnection(new Connection());
	}
	public MessageHandler(Connection connection) throws IOException {
		setConnection(connection);
	}
	public boolean answerRequest(LDAPMessage request) {

		switch(request.protocolOp.choiceId) {
		case LDAPMessageChoice.BINDREQUEST_CID :
			return doBind(request);
		case LDAPMessageChoice.SEARCHREQUEST_CID :
			return doSearch(request);
		case LDAPMessageChoice.MODIFYREQUEST_CID :
			return doModify(request);
		case LDAPMessageChoice.ADDREQUEST_CID :
			return doAdd(request);
		case LDAPMessageChoice.DELREQUEST_CID :
			return doDelete(request);
		case LDAPMessageChoice.MODDNREQUEST_CID :
			return doRename(request);
		case LDAPMessageChoice.COMPAREREQUEST_CID :
			return doCompare(request);
		case LDAPMessageChoice.ABANDONREQUEST_CID :
			return true;
		case LDAPMessageChoice.UNBINDREQUEST_CID :
			return false;
		}
		// Unrecognized request
		return false;
	}
	public boolean doAdd(LDAPMessage request) {
		AddOperation addop = new AddOperation(connection.getAuthCred(),request);
		addop.perform();
		try {
			sendResponse(addop.getResponse());
		} catch (DirectoryException de) {
			return false;
		}
		return true;
	}
	public boolean doBind(LDAPMessage request) {
		BindOperation bindop = new BindOperation(request);
		try {
			bindop.perform();
		} catch (DirectoryBindException dbe) {
			try {
				sendResponse(bindop.getResponse());
			} catch (DirectoryException de) {
				return false;
			}
			return false;
		}
		try {
			sendResponse(bindop.getResponse());
		} catch (DirectoryException de) {
			return false;
		}
		getConnection().setAuthCred(bindop.getCreds());
		return true;
	}
	public boolean doCompare(LDAPMessage request) {
		CompareOperation compop = new CompareOperation(request);
		compop.perform();
		try {
			sendResponse(compop.getResponse());
		} catch (DirectoryException de) {
			return false;
		}
		return true;
	}
	public boolean doDelete(LDAPMessage request) {
		DeleteOperation delop = new DeleteOperation(connection.getAuthCred(),request);
		delop.perform();
		try {
			sendResponse(delop.getResponse());
		} catch (DirectoryException de) {
			return false;
		}
		return true;
	}
	public boolean doModify(LDAPMessage request) {
		ModifyOperation modop = new ModifyOperation(connection.getAuthCred(),request);
		modop.perform();
		try {
			sendResponse(modop.getResponse());
		} catch (DirectoryException de) {
			return false;
		}
		return true;
	}
	public boolean doRename(LDAPMessage request) {
		RenameOperation renop = new RenameOperation(connection.getAuthCred(),request);
		renop.perform();

		try {
			sendResponse(renop.getResponse());
		} catch (DirectoryException de) {
			return false;
		}
		return true;
	}
	public boolean doSearch(LDAPMessage request) {
		SearchOperation searchop = new SearchOperation(connection.getAuthCred(),request);
		while (searchop.isMore() == true) {
			searchop.perform();
			try {
				sendResponse(searchop.getResponse());
			} catch (DirectoryException de) {
				return false;
			}
		}
		return true;
	}
	public Connection getConnection() {
		return this.connection;
	}
	public LDAPMessage getNextRequest() {
		LDAPMessage request = null;

		try {
			request = getConnection().getNextRequest();
		} catch (ASN1Exception e) {
			System.out.println("Error: " + e);
			return null;
		}

		if (getConnection().getDebug() == true && request.messageID != null) {
			request.print(System.out);
		}

		return request;
	}
	public void reset() {
		getConnection().setAuthCred(new org.javaldap.server.Credentials());
	}
	public void sendResponse(LDAPMessage response) throws DirectoryException {
		try {
			getConnection().sendResponse(response);
		} catch (Exception e) {
			// IO or ASN1
			throw new DirectoryException("Error Communicating with Client: " + e);
		}

		if (getConnection().getDebug() == true && response.messageID != null) {
			response.print(System.out);
		}
	}
	//private BEREncoder berEncoder = null;
	//private BERDecoder berDecoder = null;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
