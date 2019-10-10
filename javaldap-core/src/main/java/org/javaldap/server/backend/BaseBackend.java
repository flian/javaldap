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
 * A simple backend that rejects all attempted operations. A useful
 * base for developing backends that only allow particular operations.
 *
 * @author: Clayton Donley
 */

import java.util.Vector;

import org.javaldap.ldapv3.*;

import org.javaldap.server.EntrySet;
import org.javaldap.server.util.DirectoryException;
import org.javaldap.server.Entry;
import org.javaldap.server.syntax.DirectoryString;

public class BaseBackend implements Backend {


	/**
	 * add method comment.
	 */
	public org.javaldap.ldapv3.LDAPResultEnum add(org.javaldap.server.Entry entry) {
		return new LDAPResultEnum(53);
	}
	/**
	 * delete method comment.
	 */
	public org.javaldap.ldapv3.LDAPResultEnum delete(DirectoryString name) {
		return new LDAPResultEnum(53);
	}
	/**
	 * get method comment.
	 */
	public EntrySet get(DirectoryString base, int scope, org.javaldap.ldapv3.Filter filter,
								boolean typesOnly, java.util.Vector attributes) throws DirectoryException {
		return (EntrySet)new GenericEntrySet(this,new Vector());
	}
	public Entry getByDN(DirectoryString dn) throws DirectoryException {
		throw new DirectoryException(32);
	}
	public Entry getByID(Long id) {
		return new Entry();
	}
	/**
	 * modify method comment.
	 */
	public void modify(DirectoryString name, java.util.Vector changeEntries) throws org.javaldap.server.util.DirectoryException {
		throw new DirectoryException(53);
	}
	/**
	 * rename method comment.
	 */
	public org.javaldap.ldapv3.LDAPResultEnum rename(DirectoryString oldname, DirectoryString newname) {
		return new LDAPResultEnum(53);
	}
}
