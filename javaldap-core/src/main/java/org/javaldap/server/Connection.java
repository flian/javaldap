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

import java.net.*;
import java.io.*;
import java.util.*;

import org.javaldap.ldapv3.*;

import com.ibm.asn1.*;

import org.javaldap.server.syntax.DirectoryString;

/**
 * A MessageHandler uses a Connection object to communicate with the client.
 * The Connection class not only manages the communications socket, it also
 * manages BER encoding and decoding.
 *
 * @author: Clayton Donley
 */
public class Connection {
	private Socket client = null;
	private boolean debug;

	private Credentials authCred = null;
	
	private int lastOp = 0;

	private BufferedInputStream bufIn = null;
	private BufferedOutputStream bufOut = null;

	private BEREncoder berEncoder = null;
	BERDecoder berDecoder = null;

	public Connection() {}
	public void close() {
		try {
			getClient().close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:05:35 AM)
 * @return org.javaldap.server.Credentials
 */
public Credentials getAuthCred() {
	return authCred;
}
	public BERDecoder getBERDecoder() {
		return this.berDecoder;
	}
	public BEREncoder getBEREncoder() {
		return this.berEncoder;
	}
	public Socket getClient() {
		return this.client;
	}
	public boolean getDebug() {
		return this.debug;
	}
	public int getLastOp() {
		return this.lastOp;
	}
	public LDAPMessage getNextRequest() throws ASN1Exception {
		LDAPMessage request = new LDAPMessage();
		request.decode(getBERDecoder());
		return request;
	}
	public void sendResponse(LDAPMessage response) throws Exception {
		getBEREncoder().init();
		response.encode(getBEREncoder());
		getBEREncoder().finish();
		getBEREncoder().writeTo(this.bufOut);
		bufOut.flush();
	}
/**
 * Insert the method's description here.
 * Creation date: (8/18/2000 9:05:35 AM)
 * @param newAuthCred org.javaldap.server.Credentials
 */
public void setAuthCred(Credentials newAuthCred) {
	authCred = newAuthCred;
}
	private void setBERDecoder(BERDecoder berDecoder) {
		this.berDecoder = berDecoder;
	}
	private void setBEREncoder(BEREncoder berEncoder) {
		this.berEncoder = berEncoder;
	}
	public void setClient(Socket client) throws IOException {
		this.client = client;

		if (getBEREncoder() == null) {
			setBEREncoder(new BEREncoder());
		}
		this.bufOut = new BufferedOutputStream(client.getOutputStream());
		this.bufIn = new BufferedInputStream(client.getInputStream());

		if (getBERDecoder() == null) {
			setBERDecoder(new BERDecoder(this.bufIn));
		} else {
			getBERDecoder().setInputStream(this.bufIn);
		}

	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public void setLastOp(int lastOp) {
		this.lastOp = lastOp;
	}
}
