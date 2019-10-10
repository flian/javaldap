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
 * Backend for Queries to a subschema entry
 *
 * @author: Clayton Donley
 */

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

import org.javaldap.ldapv3.*;

import org.javaldap.server.syntax.DirectoryString;
import org.javaldap.server.Entry;
import org.javaldap.server.EntrySet;
import org.javaldap.server.schema.SchemaChecker;
import org.javaldap.server.schema.ObjectClass;
import org.javaldap.server.util.InvalidDNException;
import org.javaldap.server.util.DirectoryException;

public class BackendSchema extends BaseBackend {

	public EntrySet get(DirectoryString base, int scope, Filter filter, boolean attrsOnly, Vector attrs) throws DirectoryException {
		if (scope == SearchRequestEnum.BASEOBJECT  && base.equals(new DirectoryString("cn=schema"))) {
			Vector entries = new Vector();
			entries.addElement(new Long(1));
			return (EntrySet)new GenericEntrySet(this,entries);
		}
		return (EntrySet)new GenericEntrySet(this,new Vector());
	}
	public Entry getByID(Long id) {

		Entry schemaEntry = null;
		try {
			schemaEntry = new Entry(new DirectoryString("cn=schema"));
		} catch (InvalidDNException ide) {}


		Vector objClasses = new Vector();
		Hashtable classes = SchemaChecker.getInstance().getObjectClasses();
		Enumeration classEnum = classes.keys();
		while (classEnum.hasMoreElements()) {
			ObjectClass aClass = (ObjectClass)classes.get((DirectoryString)classEnum.nextElement());
			objClasses.addElement(new DirectoryString(aClass.toString()));
		}
		schemaEntry.put(new DirectoryString("objectclasses"),objClasses);
		return schemaEntry;
	}
}
