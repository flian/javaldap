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
 * Exception thrown when the Bind operation is unsuccessful
 *
 * @author: Clayton Donley
 */
public class DirectoryBindException extends DirectoryException {
	/**
	 * DirectoryBindException constructor comment.
	 */
	public DirectoryBindException() {
		super();
		setLDAPErrorCode(49);
	}
	/**
	 * DirectoryBindException constructor comment.
	 * @param s java.lang.String
	 */
	public DirectoryBindException(String s) {
		super(s);
		setLDAPErrorCode(49);
	}
}
