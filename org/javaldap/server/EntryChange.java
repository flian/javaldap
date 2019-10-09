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
 * Class containing a single LDAP modification
 * @author: Clayton Donley
 */

import java.util.Vector;
import org.javaldap.server.syntax.DirectoryString;

public class EntryChange {

	private int modType;
	public DirectoryString attr;
	public java.util.Vector values;
	/**
	 * EntryChange constructor comment.
	 */
	public EntryChange() {
		super();
	}
	/**
	 * EntryChange constructor comment.
	 */
	public EntryChange(int modType,DirectoryString attr,Vector values) {
		super();
		setModType(modType);
		setAttr(attr);
		setValues(values);
	}
	/**
	 * @return java.lang.String
	 */
	public DirectoryString getAttr() {
		return attr;
	}
	/**
	 * @return int
	 */
	public int getModType() {
		return modType;
	}
	/**
	 * @return java.util.Vector
	 */
	public java.util.Vector getValues() {
		return values;
	}
	/**
	 * @param newAttr java.lang.String
	 */
	public void setAttr(DirectoryString newAttr) {
		attr = newAttr;
	}
	/**
	 * @param newModType int
	 */
	public void setModType(int newModType) {
		modType = newModType;
	}
	/**
	 * @param newValues java.util.Vector
	 */
	public void setValues(java.util.Vector newValues) {
		values = newValues;
	}
}
