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
 * DNUtility contains methods for manipulating distinguished names
 *
 * @author: Clayton Donley
 */

import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Enumeration;
import org.javaldap.server.syntax.DirectoryString;

import org.javaldap.server.util.InvalidDNException;

public class DNUtility {
	private static DNUtility instance = null;

	private DNUtility() {}
	public DirectoryString createDN(Vector rdnComponents) throws InvalidDNException {
		StringBuffer dn = new StringBuffer(64);
		Enumeration rdnEnum = rdnComponents.elements();

		while (rdnEnum.hasMoreElements()) {
			String rdn = (String)rdnEnum.nextElement();
			if (rdn.indexOf("=") < 0) {
				throw new InvalidDNException();
			}
			dn.append(rdn.trim());
			if (rdnEnum.hasMoreElements()) {
				dn.append(",");
			}
		}
		return new DirectoryString(dn.toString());
	}
	public Vector explodeDN(DirectoryString dn) {
		Vector rdnComponents = new Vector();

		StringTokenizer dnTok = new StringTokenizer(dn.toString(),",");

		while (dnTok.hasMoreTokens()) {
			String rdn = dnTok.nextToken();
			while (rdn.endsWith("\\") && !rdn.endsWith("\\\\") && dnTok.hasMoreTokens()) {
				rdn = rdn.concat(",");
				rdn = rdn.concat(dnTok.nextToken());
			}
			rdn.trim();
			rdnComponents.addElement(rdn);
		}
		return rdnComponents;
	}
	public static DNUtility getInstance() {
		if (instance == null) {
			instance = new DNUtility();
		}
		return instance;
	}
	public DirectoryString normalize(DirectoryString dn) throws InvalidDNException {
		return createDN(explodeDN(dn));
	}
}
