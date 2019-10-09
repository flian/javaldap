package org.javaldap.ldapv3;

//-----------------------------------------------------------------------------
//   NOTE: this is a machine generated file - editing not recommended
//
//   File: ./src/org/javaldap/ldapv3/Referral.java
//
//   Java class for ASN.1 definition Referral as defined in
//   module LDAPv3.
//   This file was generated by Snacc for Java at Fri Jul  2 18:01:43 1999
//   Snacc for Java - Andreas Schade (SAN/ZRL)
//-----------------------------------------------------------------------------

// Import PrintStream class for print methods
import java.io.PrintStream;

// Import ASN.1 basic type representations
import com.ibm.util.*;

// Import ASN.1 decoding/encoding classes
import com.ibm.asn1.*;

/** This class represents the ASN.1 SEQUENCE OF type <tt>Referral</tt>.
  * Sequence-of classes inherit from java.util.Vector.
  * As a subtype of the Vector class this class also 
  * preserves the order of the contained elements.
  * @author Snacc for Java
  * @version Fri Jul  2 18:01:43 1999
 
  */

public class Referral extends java.util.Vector implements LDAPv3 {

	/** default constructor */
	public Referral() {}
	/** copy constructor */
	public Referral (Referral arg) {
		for (java.util.Enumeration e = arg.elements(); e.hasMoreElements();) {
			addElement(((byte[])(e.nextElement())));
		}
	}
	/** decoding method.
	* @param dec
	*        decoder object derived from com.ibm.asn1.ASN1Decoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            decoding error
	*/
	public void decode (ASN1Decoder dec) throws ASN1Exception {
		int seq_of_nr = dec.decodeSequenceOf();
		while (!dec.endOf(seq_of_nr)) {
			byte[] tmp;
			tmp = dec.decodeOctetString();
			addElement(tmp);
		}
	}
	/** encoding method.
	* @param enc
	*        encoder object derived from com.ibm.asn1.ASN1Encoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            encoding error
	*/
	public void encode (ASN1Encoder enc) throws ASN1Exception {
		int seq_of_nr = enc.encodeSequenceOf();
		for (java.util.Enumeration e = elements(); e.hasMoreElements();) {
			enc.encodeOctetString(((byte[])(e.nextElement())));
		}
		enc.endOf(seq_of_nr);
	}
	/** default print method (variable indentation)
	* @param os
	*        PrintStream representing the print destination (file, etc)
	*/
	public void print (PrintStream os) {
		print(os,0);
	}
	/** print method (variable indentation)
	* @param os
	*        PrintStream representing the print destination (file, etc)
	* @param indent
	*        number of blanks that preceed each output line.
	*/
	public void print (PrintStream os, int indent) {
		boolean nonePrinted = true;
		os.println("{ -- SEQUENCE OF --");
		for (java.util.Enumeration e = elements(); e.hasMoreElements();) {
			if (nonePrinted == false)
				os.println(',');
			nonePrinted = false;
			for(int ii = 0; ii < indent+2; ii++) os.print(' ');
			try {
				(new HexOutputStream(os)).write(((byte[])(e.nextElement())));
			} catch (java.io.IOException ex) {
				os.print("( unprintable OCTET STRING value )");
			}
			if (!e.hasMoreElements())
				os.println();
		}
		for(int ii = 0; ii < indent; ii++) os.print(' ');
		os.print('}');
	}
}
