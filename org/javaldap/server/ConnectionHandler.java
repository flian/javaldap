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
 * The ConnectionHandler is spawned when a new connection arrives. It retrieves a
 * suitable MessageHandler from the pool and uses it to parse incoming messages.
 *
 * @author: Clayton Donley
 */
import java.net.*;
import java.io.*;
import java.util.*;
import org.javaldap.ldapv3.*;

public class ConnectionHandler extends Thread {
	private MessageHandler msgHandler = null;

	public ConnectionHandler(Socket client) throws Exception {

		client.setTcpNoDelay(true);
		msgHandler = (MessageHandler)MessageHandlerPool.getInstance().checkOut();
		msgHandler.reset();
		msgHandler.getConnection().setClient(client);
		msgHandler.getConnection().setDebug(false);

		setPriority(NORM_PRIORITY-1);
	}
	public void run() {

		boolean continueSession = true;

		while (continueSession == true) {
			LDAPMessage request = msgHandler.getNextRequest();
			if (request != null) {
				continueSession = msgHandler.answerRequest(request);
				request = null;
			} else {
				continueSession = false;
			}
		}

		MessageHandlerPool.getInstance().checkIn(msgHandler);
	}
}
