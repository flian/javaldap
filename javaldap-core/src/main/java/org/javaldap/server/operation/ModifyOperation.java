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
import org.javaldap.server.EntryChange;
import org.javaldap.server.Credentials;
import org.javaldap.server.backend.BackendHandler;
import org.javaldap.server.util.DirectorySchemaViolation;
import org.javaldap.server.util.DirectoryException;
import org.javaldap.server.syntax.DirectoryString;


public class ModifyOperation implements Operation {

	LDAPMessage request = null;
	LDAPMessage response = null;
	Credentials creds = null;

	public ModifyOperation(Credentials creds,LDAPMessage request) {
		this.request = request;
		this.creds = creds;
	}
	public LDAPMessage getResponse() {
		return this.response;
	}
	public void perform() {

		DirectoryString name = new DirectoryString(this.request.protocolOp.modifyRequest.object);

		Vector changeVector = new Vector();
		for (Enumeration enumMods = this.request.protocolOp.modifyRequest.modification.elements();
				enumMods.hasMoreElements();) {
			ModifyRequestSeqOfSeq oneMod = (ModifyRequestSeqOfSeq)enumMods.nextElement();
			int modType = oneMod.operation.value;
			AttributeTypeAndValues modification = oneMod.modification;
			DirectoryString modAttr = new DirectoryString(modification.type);
			Vector modValues = new Vector();
			for (Enumeration enumVals = modification.vals.elements(); enumVals.hasMoreElements();) {
				DirectoryString modValue = new DirectoryString((byte[])enumVals.nextElement());
				modValues.addElement(modValue);
			}
			//BackendHandler.Handler().oneChange(name,modType,modAttr,modValues);
			EntryChange oneChange = new EntryChange(modType,modAttr,modValues);
			changeVector.addElement(oneChange);
		}

		this.response = new LDAPMessage();
		ModifyResponse modifyResponse = new ModifyResponse();
		modifyResponse.resultCode = new LDAPResultEnum(0);
		modifyResponse.matchedDN = new byte[0];
		modifyResponse.errorMessage = new byte[0];
		modifyResponse.referral = new Referral();

		try {
			BackendHandler.Handler().modify(creds,org.javaldap.server.util.DNUtility.getInstance().normalize(name),changeVector);
		} catch (DirectoryException de) {
			modifyResponse.resultCode = new LDAPResultEnum(de.getLDAPErrorCode());
			if (de.getMessage() != null) {
				modifyResponse.errorMessage = de.getMessage().getBytes();
			}
		}

		LDAPMessageChoice op = new LDAPMessageChoice();
		op.choiceId = LDAPMessageChoice.MODIFYRESPONSE_CID;
		op.modifyResponse = modifyResponse;

		this.response.messageID = this.request.messageID;
		this.response.protocolOp = op;

	}
}
