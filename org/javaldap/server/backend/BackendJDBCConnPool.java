package org.javaldap.server.backend;

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
 * A connection pool for JDBC Connections. Needs improvement.
 *
 * @author: Clayton Donley
 */

import java.sql.*;
import org.javaldap.server.util.ServerConfig;

class BackendJDBCConnPool extends org.javaldap.server.util.ObjectPool {
	private static BackendJDBCConnPool bjcp = null;

	/**
	 * ConnectionHandlerPool constructor comment.
	 */
	private BackendJDBCConnPool() {
		super();
	}
	/**
	 * create method comment.
	 */
	public Object create() throws Exception {
		String backjdbcURL = (String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_BACKENDJDBC_DBURL);
		String backjdbcUserID = (String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_BACKENDJDBC_DBUSER);
		String backjdbcPasswd = (String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_BACKENDJDBC_DBPASS);
		Connection dbcon = DriverManager.getConnection(backjdbcURL,backjdbcUserID,backjdbcPasswd);
		dbcon.setAutoCommit(false);
		return dbcon;
	}
	/**
	 * expire method comment.
	 */
	public void expire(Object o) {
	}
	public static BackendJDBCConnPool getInstance() {
		if (bjcp == null) {
			bjcp = new BackendJDBCConnPool();
		}
		return bjcp;
	}
	/**
	 * validate method comment.
	 */
	public boolean validate(Object o) {
		return true;
	}
}
