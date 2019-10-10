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
 * AddOperation calls the BackendHandler to add a new entry and builds an
 * appropriate LDAP Result.
 *
 * @author: Clayton Donley
 */
 
import java.util.*;
import java.math.BigInteger;

import org.javaldap.ldapv3.*;
import org.javaldap.server.Entry;
import org.javaldap.server.Credentials;
import org.javaldap.server.backend.BackendHandler;
import org.javaldap.server.util.DirectorySchemaViolation;
import org.javaldap.server.util.InvalidDNException;
import org.javaldap.server.syntax.DirectoryString;

public class AddOperation implements Operation {

	LDAPMessage request = null;
	LDAPMessage response = null;
	Credentials creds = null;

	public AddOperation(Credentials creds,LDAPMessage request) {
		this.request = request;
		this.creds = creds;
	}
	public LDAPMessage getResponse() {
		return this.response;
	}
	public void perform() {

		this.response = new LDAPMessage();
		AddResponse addResponse = new AddResponse();
		addResponse.matchedDN = new byte[0];
		addResponse.errorMessage = new byte[0];
		addResponse.referral = new Referral();

		try {
			addResponse.resultCode = BackendHandler.Handler().add(creds,requestToEntry());
		} catch (DirectorySchemaViolation dsv) {
			addResponse.resultCode = new LDAPResultEnum(65);
			addResponse.errorMessage = dsv.getMessage().getBytes();
		}
		catch (InvalidDNException ide) {
			addResponse.resultCode = new LDAPResultEnum(34);
			if (ide.getMessage() != null) {
				addResponse.errorMessage = ide.getMessage().getBytes();
			}
		}

		LDAPMessageChoice op = new LDAPMessageChoice();
		op.choiceId = LDAPMessageChoice.ADDRESPONSE_CID;
		op.addResponse = addResponse;

		this.response.messageID = new BigInteger(this.request.messageID.toString());
		this.response.protocolOp = op;

	}
	public Entry requestToEntry() throws InvalidDNException {

		Entry entry = new Entry(new DirectoryString(this.request.protocolOp.addRequest.entry));
		AttributeList attrList = this.request.protocolOp.addRequest.attributes;

		for (Enumeration enumAttr = attrList.elements(); enumAttr.hasMoreElements();) {
			AttributeListSeq als = (AttributeListSeq)enumAttr.nextElement();
			DirectoryString type = new DirectoryString(als.type);
			als.type = null;
			Vector values = new Vector();
			for (Enumeration enumVal = als.vals.elements(); enumVal.hasMoreElements();) {
				byte[] thisVal = (byte[])enumVal.nextElement();
				if (thisVal.length > 0)
					values.addElement(new DirectoryString(thisVal));
			}
			als.vals = null;
			if (values.size() > 0) {
				entry.put(type,values);
			}
		}
		return entry;
	}
}
