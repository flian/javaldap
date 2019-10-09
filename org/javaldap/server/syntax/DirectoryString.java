package org.javaldap.server.syntax;

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
 * Class representing a Directory String syntax (case insensitive string)
 *
 * @author: Clayton Donley
 */
public class DirectoryString implements java.io.Serializable {

	byte[] directoryBytes;
	int hashCode = 0;

	/**
	 * DirectoryString constructor comment.
	 */
	public DirectoryString() {
		super();
	}
	/**
	 * DirectoryString constructor comment.
	 */
	public DirectoryString(byte[] bytes) {
		super();
		setDirectoryBytes(bytes);
	}
	/**
	 * DirectoryString constructor comment.
	 */
	public DirectoryString(String data) {
		super();
		setDirectoryString(data);
	}
	public int compareTo(Object obj) {
		return compareToIgnoreCase((DirectoryString)obj);
	}
	public int compareTo(DirectoryString ds) {
		return compareToIgnoreCase(ds);
	}
	private int compareToIgnoreCase(DirectoryString compDS) {
		int equal = 0;
		byte[] stringOne = getDirectoryBytes();
		byte[] stringTwo = compDS.getDirectoryBytes();
		int stwoSize = stringTwo.length;
		for (int byteNo = 0; byteNo < stringOne.length; byteNo++) {
			if (byteNo >= stwoSize) {
				equal = 1;
				break;
			}
			equal = Character.toLowerCase((char)stringOne[byteNo])-Character.toLowerCase((char)stringTwo[byteNo]);
			if (equal != 0) {
				break;
			}
		}
		if (equal == 0 && stwoSize > stringOne.length) {
			equal = -1;
		}

		return equal;
	}
	public boolean endsWith(DirectoryString endsString) {
		int equal = 0;
		byte[] stringOne = getDirectoryBytes();
		byte[] stringTwo = endsString.getDirectoryBytes();
		int soneSize = stringOne.length;
		int stwoSize = stringTwo.length;
		if (stwoSize > soneSize) {
			return false;
		}
		int startOne = soneSize - stwoSize;
		for (int byteNo = stwoSize-1; byteNo >= 0 ; byteNo--) {
			equal = Character.toLowerCase((char)stringOne[startOne+byteNo])-Character.toLowerCase((char)stringTwo[byteNo]);
			if (equal != 0) {
				break;
			}
		}
		if (equal == 0) {
			return true;
		}

		return false;
	}
	public boolean equals(Object obj) {
		if (compareToIgnoreCase((DirectoryString)obj) == 0) return true;
		return false;
	}
	public boolean equals(DirectoryString ds) {
		if (compareToIgnoreCase(ds) == 0) return true;
		return false;
	}
	public byte[] getBytes() {
		return this.directoryBytes;
	}
	public byte[] getDirectoryBytes() {
		return this.directoryBytes;
	}
	public String getDirectoryString() {
		return new String(getDirectoryBytes());
	}
	public int hashCode() {
		return hashCode;
	}
	public int indexOf(DirectoryString indexString) {
		return getDirectoryString().indexOf(indexString.getDirectoryString());
	}
	public int length() {
		return getDirectoryBytes().length;
	}
	public String normalize() {
		return new String(directoryBytes).toUpperCase();
	}
	public void setDirectoryBytes(byte[] directoryBytes) {
		this.directoryBytes = directoryBytes;
		hashCode = 0;
		int byteNo = 0;
		char currentChar;
		byte aBitNo = 0;
		for (byteNo = 0; byteNo < directoryBytes.length; byteNo++) {
			hashCode = hashCode + Character.toLowerCase((char)directoryBytes[byteNo])<<(byteNo&15); //31^(directoryBytes.length-1);
		}

	}
	public void setDirectoryString(String directoryString) {
		setDirectoryBytes(directoryString.getBytes());
		//this.lowerString = directoryString.toLowerCase();
	}
	public String toString() {
		return new String(getDirectoryBytes());
	}
}
