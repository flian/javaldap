package org.javaldap.server.util;

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
 * General logging class for use in printing debug output
 *
 * @author: Clayton Donley
 */
public class Logger {
	private static Logger instance;
	private int logLevel = 0;
	private java.io.PrintStream logStream = System.out;

	public static final int LOG_NORMAL = 0;
	public static final int LOG_DEBUG = 9;

	private Logger() {
		logLevel = new Integer((String)ServerConfig.getInstance().get(ServerConfig.JAVALDAP_DEBUG)).intValue();
		
	}
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	public void log(int level,String message) {
		if (logLevel >= level) {
			logStream.println(message);
		}
	}
	public void setLogLevel(int level) {
		this.logLevel = level;
	}
	public void setLogWriter(java.io.PrintStream logStream) {
		this.logStream = logStream;
	}
}
