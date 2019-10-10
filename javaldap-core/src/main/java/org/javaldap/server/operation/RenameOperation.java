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

import java.util.*;
import org.javaldap.ldapv3.*;
import org.javaldap.server.Entry;
import org.javaldap.server.Credentials;
import org.javaldap.server.backend.BackendHandler;
import org.javaldap.server.syntax.DirectoryString;


public class RenameOperation implements Operation {

	LDAPMessage request = null;
	LDAPMessage response = null;
	Credentials creds = null;

	public RenameOperation(Credentials creds,LDAPMessage request) {
		this.request = request;
		this.creds = creds;
	}
	public LDAPMessage getResponse() {
		return this.response;
	}
	public void perform() {

		DirectoryString name =
			new DirectoryString(this.request.protocolOp.modDNRequest.entry);
		DirectoryString newname =
			new DirectoryString(this.request.protocolOp.modDNRequest.newrdn);
		boolean deleterdn =
			this.request.protocolOp.modDNRequest.deleteoldrdn;
		DirectoryString newSuperior =
			new DirectoryString(this.request.protocolOp.modDNRequest.newSuperior);

		this.response = new LDAPMessage();
		ModifyDNResponse modDNResponse = new ModifyDNResponse();
		modDNResponse.resultCode = new LDAPResultEnum(0);
		modDNResponse.matchedDN = new byte[0];
		modDNResponse.errorMessage = new byte[0];
		modDNResponse.referral = new Referral();

		LDAPMessageChoice op = new LDAPMessageChoice();
		op.choiceId = LDAPMessageChoice.MODDNRESPONSE_CID;
		op.modDNResponse = modDNResponse;

		try {
			BackendHandler.Handler().rename(creds,name,newname);
		} catch (org.javaldap.server.util.DirectoryException de) {
			modDNResponse.resultCode = new LDAPResultEnum(de.getLDAPErrorCode());
			if (de.getMessage() != null) {
				modDNResponse.errorMessage = de.getMessage().getBytes();
			}
		}

		this.response.messageID = this.request.messageID;
		this.response.protocolOp = op;

	}
}
