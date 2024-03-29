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
 * Class for reading and retrieving the server's file-based configuration
 *
 * @author: Clayton Donley
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ServerConfig extends java.util.Properties {

	private static final String JAVALDAP_PROP = "javaldap.prop";
	private static final String JAVALDAP_PROP_DESC = "JavaLDAP Server Properties";

	public static final String JAVALDAP_SERVER_NAME = "javaldap.server.name";
	public static final String JAVALDAP_SERVER_PORT = "javaldap.server.port";
	public static final String JAVALDAP_STDSCHEMA = "javaldap.schema.std";
	public static final String JAVALDAP_USERSCHEMA = "javaldap.schema.user";
	public static final String JAVALDAP_SERVER_THREADS = "javaldap.server.threads";
	public static final String JAVALDAP_SERVER_BACKENDS = "javaldap.server.backends";
	public static final String JAVALDAP_SCHEMACHECK = "javaldap.schemacheck";
	public static final String JAVALDAP_ACLCHECK = "javaldap.aclcheck";
	public static final String JAVALDAP_ROOTUSER = "javaldap.rootuser";
	public static final String JAVALDAP_ROOTPW = "javaldap.rootpw";
	public static final String JAVALDAP_DEBUG = "javaldap.debug";

	// Backend Config should go in the backend config, not this...however this will do for now.
	public static final String JAVALDAP_BACKENDJDBC_LONGVARCHAR = "javaldap.backendjdbc.longvarchar";
	public static final String JAVALDAP_BACKENDJDBC_CREATETABLE = "javaldap.backendjdbc.createtable";
	public static final String JAVALDAP_BACKENDJDBC_DBDRIVER = "javaldap.backendjdbc.dbdriver";
	public static final String JAVALDAP_BACKENDJDBC_DBURL = "javaldap.backendjdbc.dburl";
	public static final String JAVALDAP_BACKENDJDBC_DBUSER = "javaldap.backendjdbc.dbuser";
	public static final String JAVALDAP_BACKENDJDBC_DBPASS = "javaldap.backendjdbc.dbpass";
	
	private static ServerConfig instance;

	/**
	 * ServerConfig constructor comment.
	 */
	private ServerConfig() {
		super();
	}
	/**
	 * ServerConfig constructor comment.
	 * @param defaults java.util.Properties
	 */
	private ServerConfig(java.util.Properties defaults) {
		super(defaults);
	}
	public static ServerConfig getInstance() {
		if (instance == null) {
			instance = new ServerConfig();
		}
		return instance;
	}
	public void init() {
		try {
			FileInputStream is = new FileInputStream(JAVALDAP_PROP);
			load(is);
			is.close();
		} catch (java.io.FileNotFoundException fnfe) {
			Logger.getInstance().log(Logger.LOG_NORMAL,"Configuration Not Found: " + JAVALDAP_PROP);
		}
		catch (java.io.IOException ioe) {
			Logger.getInstance().log(Logger.LOG_NORMAL,"IO Error Reading " + JAVALDAP_PROP);
		}
	}
	public void write() {
		try {
			FileOutputStream os = new FileOutputStream(JAVALDAP_PROP);
			save(os,JAVALDAP_PROP_DESC);
			os.close();
		} catch (java.io.IOException ioe) {
			Logger.getInstance().log(Logger.LOG_NORMAL,"IO Error Writing " + JAVALDAP_PROP);
		}
	}
}
