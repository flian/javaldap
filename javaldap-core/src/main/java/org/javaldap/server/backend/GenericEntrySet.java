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
 * A generic entry set contains a Vector of Entry objects with the entire
 * result set.
 * 
 * @author: Clayton Donley
 */

import java.util.Vector;
import org.javaldap.server.Entry;
import org.javaldap.server.EntrySet;

public class GenericEntrySet implements EntrySet {
	private Vector entries = null;
	private Backend myBackend = null;
	private int entryCount = 0;
	private boolean hasMore = false;
	
	public GenericEntrySet() {
		super();
	}
/**
 * GenericEntrySet constructor comment.
 */
	public GenericEntrySet(Backend myBackend,Vector entries) {
		super();
		this.myBackend = myBackend;
		this.entries = entries;
		if (!entries.isEmpty()) {
			this.hasMore = true;
			this.entryCount = 0;
		}
	}
	public Entry getNext() {
		if (hasMore == false) {
			return null;
		}
		
		Entry current = (Entry)myBackend.getByID((Long)entries.elementAt(entryCount));
		if (entryCount < entries.size()-1) {
			entryCount++;
		} else {
			hasMore = false;
		}
			
		return current;
	}
	public boolean hasMore() {
		return hasMore;
	}
}
