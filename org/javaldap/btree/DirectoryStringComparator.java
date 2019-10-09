package org.javaldap.btree;

import org.javaldap.server.syntax.DirectoryString;

class DirectoryStringComparator implements Comparator {
	protected DirectoryString ds;
	public DirectoryStringComparator(DirectoryString ds) {
		this.ds = ds;
		;
	}
	public int compareTo(Object obj) {
		return ds.compareTo(((DirectoryStringComparator)obj).ds);
	}
}
