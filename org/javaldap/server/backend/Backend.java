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
 * Backends must implement this interface or subclass another class that implements
 * this interface.
 *
 * @author: Clayton Donley
 */
 
import java.util.*;
import org.javaldap.ldapv3.*;
import org.javaldap.server.*;
import org.javaldap.server.util.DirectoryException;
import org.javaldap.server.syntax.DirectoryString;

public interface Backend {

	public abstract LDAPResultEnum add(Entry entry);
	public abstract LDAPResultEnum delete(DirectoryString name);
	public abstract EntrySet get(DirectoryString base, int scope, Filter filter,
							   boolean typesOnly, Vector attributes) throws DirectoryException;
	public abstract Entry getByDN(DirectoryString dn) throws DirectoryException;
	public abstract Entry getByID(Long id);
	public abstract void modify(DirectoryString name,Vector changeEntries) throws DirectoryException;
	public abstract LDAPResultEnum rename(DirectoryString oldname, DirectoryString newname);
}
