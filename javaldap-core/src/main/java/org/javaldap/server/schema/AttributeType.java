package org.javaldap.server.schema;

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
 * Class used to represent an LDAP attribute type definition
 *
 * @author: Clayton Donley
 */

import org.javaldap.server.syntax.DirectoryString;

public class AttributeType {

	private String oid = null;
	private DirectoryString name = null;
	private String description = null;
	private boolean obsolete = false;
	private DirectoryString superior = null;
	private String equalityMatch = null;
	private String orderingMatch = null;
	private String substrMatch = null;
	private String syntax = null;
	private boolean singleValue = false;
	private boolean collective = false;
	private boolean noUserModification = false;
	private String usage = "userApplications";

	/**
	 * AttributeClass constructor comment.
	 */
	public AttributeType() {
		super();
	}
	public String getDescription() {
		return this.description;
	}
	public String getEqualityMatch() {
		return this.equalityMatch;
	}
	public DirectoryString getName() {
		return this.name;
	}
	public String getOid() {
		return this.oid;
	}
	public String getOrderingMatch() {
		return this.orderingMatch;
	}
	public String getSubstrMatch() {
		return this.substrMatch;
	}
	public DirectoryString getSuperior() {
		return this.superior;
	}
	public String getSyntax() {
		return this.syntax;
	}
	public String getUsage() {
		return this.usage;
	}
	public boolean isCollective() {
		return this.collective;
	}
	public boolean isNoUserModification() {
		return this.noUserModification;
	}
	public boolean isObsolete() {
		return this.obsolete;
	}
	public boolean isSingleValue() {
		return this.singleValue;
	}
	public void setCollective(boolean collective) {
		this.collective = collective;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEqualityMatch(String equalityMatch) {
		this.equalityMatch = equalityMatch;
	}
	public void setName(DirectoryString name) {
		this.name = name;
	}
	public void setNoUserModification(boolean noUserModification) {
		this.noUserModification = noUserModification;
	}
	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public void setOrderingMatch(String orderingMatch) {
		this.orderingMatch = orderingMatch;
	}
	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}
	public void setSubstrMatch(String substrMatch) {
		this.substrMatch = substrMatch;
	}
	public void setSuperior(DirectoryString superior) {
		this.superior = superior;
	}
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
}
