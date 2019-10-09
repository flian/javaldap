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
 * A JDBCEntrySet contains a reference to a ResultSet and can be used to iterate
 * through the entries returned by BackendJDBC.
 * 
 * @author: Clayton Donley
 */

import java.util.Vector;
import java.sql.*;
import java.io.*;

import org.javaldap.server.Entry;
import org.javaldap.server.EntrySet;

public class JDBCEntrySet implements EntrySet {
	private ResultSet rs = null;
	private Connection dbcon = null;
	private Backend myBackend = null;
	private boolean hasMore = false;

/**
 * GenericEntrySet constructor comment.
 */
public JDBCEntrySet() {
	super();
}
/**
 * GenericEntrySet constructor comment.
 */
public JDBCEntrySet(Backend myBackend,ResultSet rs, Connection dbcon) {
	super();
	this.myBackend = myBackend;
	this.rs = rs;
	this.dbcon = dbcon;
	try {
		if (rs.next()) {
			this.hasMore = true;
		} else {
		        this.rs = null;
		}
	} catch (SQLException se) {
		se.printStackTrace();
	}
}
	protected void finalize() throws Throwable {
		super.finalize();
		if (dbcon != null) {
			BackendJDBCConnPool.getInstance().checkIn(dbcon);
		}
		rs = null;
	}
	public Entry getNext() {
		if (hasMore == false) {
			return null;
		}

		Entry entry = null;
		
		try {
			byte[] entryBytes = rs.getBytes(1);
			entry = new Entry(entryBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (!rs.next()) {
				hasMore = false;
				this.rs = null;
				BackendJDBCConnPool.getInstance().checkIn(dbcon);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			hasMore = false;
			this.rs = null;
			BackendJDBCConnPool.getInstance().checkIn(dbcon);
		}
		return entry;
		
	}
	public boolean hasMore() {
		return hasMore;
	}
}
