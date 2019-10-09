package org.javaldap.btree;

/***********************************************************************
* B-Tree Test programm
*
*	by L.Horisberger & G.Schweizer
* last change: 26.02.1998 by horil
**/
import org.javaldap.server.syntax.*;

class StringComparator implements Comparator {
	protected String text;

	public StringComparator(String val) {
		text=val;
	}
	public int compareTo(Object obj) {
		return text.compareTo(((StringComparator)obj).text);
	}
	public Object getKey() {
		return (Object)text;
	}
}
