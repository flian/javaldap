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
 * LDAPServer initializes configuration, schema, and the BackendHandler.
 * It then creates a ServerSocket and spawns ConnectionHandlers on incoming
 * connections.
 *
 * @author: Clayton Donley
 */
import java.net.*;
import java.io.*;
import java.util.*;
import org.javaldap.server.schema.InitSchema;
import org.javaldap.server.util.Logger;
import org.javaldap.server.util.ServerConfig;

public class LDAPServer {



	public static void main(java.lang.String[] args) throws Exception {

		// Initialize Server Configuration
		ServerConfig.getInstance().init();

		// Initialize the Server Schema
		new InitSchema().init();

		org.javaldap.server.acl.ACLChecker.getInstance().initialize();

		// Read LDAP Server Port from Configuration
		String configPort = (String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_SERVER_PORT);
		int serverPort = new Integer(configPort).intValue();

		// Initialize Backend Handler
		org.javaldap.server.backend.BackendHandler.Handler();

		// Start Listening on the LDAP Port
		Logger.getInstance().log(Logger.LOG_NORMAL,
								 "Server Starting on port " + serverPort);
		ServerSocket serverSock = new ServerSocket(serverPort);

		// Loop infinitely and create new ConnectionHandler threads
		// when new connections are received
		while (true) {
			Logger.getInstance().log(Logger.LOG_DEBUG,"Connection Initiated.");
			//ConnectionHandler cHandle = (ConnectionHandler)ConnectionHandlerPool.getInstance().checkOut();
			//cHandle.prepare(serverSock.accept());
			//cHandle.start();
			new ConnectionHandler(serverSock.accept()).start();
		}



	}
}
