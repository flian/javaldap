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
 * DeleteOperation attempts to delete an entry
 *
 * @author: Clayton Donley
 */
 
import java.util.*;
import org.javaldap.ldapv3.*;
import org.javaldap.server.Entry;
import org.javaldap.server.Credentials;
import org.javaldap.server.backend.BackendHandler;
import org.javaldap.server.syntax.DirectoryString;

public class DeleteOperation implements Operation {

	LDAPMessage request = null;
	LDAPMessage response = null;
	Credentials creds = null;

	public DeleteOperation(Credentials creds,LDAPMessage request) {
		this.request = request;
		this.creds = creds;
	}
	public LDAPMessage getResponse() {
		return this.response;
	}
	public void perform() {

		LDAPResultEnum resultCode = null;
		try {
			resultCode =
				BackendHandler.Handler().delete(creds,org.javaldap.server.util.DNUtility.getInstance().normalize(new DirectoryString(this.request.protocolOp.delRequest.value)));
		} catch (org.javaldap.server.util.InvalidDNException ide) {
			resultCode = new LDAPResultEnum(34);
		}
		this.response = new LDAPMessage();
		LDAPMessageChoice op = new LDAPMessageChoice();
		DelResponse delResponse = new DelResponse();

		delResponse.resultCode = resultCode;
		delResponse.matchedDN = new byte[0];
		delResponse.errorMessage = new byte[0];
		delResponse.referral = new Referral();

		op.choiceId = LDAPMessageChoice.DELRESPONSE_CID;
		op.delResponse = delResponse;

		this.response.messageID = this.request.messageID;
		this.response.protocolOp = op;

	}
}
