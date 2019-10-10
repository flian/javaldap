package org.javaldap.ldapv3;

//-----------------------------------------------------------------------------
//   NOTE: this is a machine generated file - editing not recommended
//
//   File: ./src/org/javaldap/ldapv3/AddRequest.java
//
//   Java class for ASN.1 definition AddRequest as defined in
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

/** This class represents the ASN.1 SEQUENCE type <tt>AddRequest</tt>.
  * For each sequence member, sequence classes contain a
  * public member variable of the corresponding Java type.
  * @author Snacc for Java
  * @version Fri Jul  2 18:01:43 1999
 
  */

public class AddRequest implements LDAPv3 {

	/** member variable representing the sequence member entry of type byte[] */
	public byte[] entry;
	/** member variable representing the sequence member attributes of type AttributeList */
	public AttributeList attributes = new AttributeList();

	/** default constructor */
	public AddRequest() {}
	/** copy constructor */
	public AddRequest (AddRequest arg) {
		entry = new byte[arg.entry.length];
		System.arraycopy(arg.entry,0,entry,0,arg.entry.length);
		attributes = new AttributeList(arg.attributes);
	}
	/** decoding method.
	* @param dec
	*        decoder object derived from com.ibm.asn1.ASN1Decoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            decoding error
	*/
	public void decode (ASN1Decoder dec) throws ASN1Exception {
		dec.nextIsImplicit(dec.makeTag(dec.APPLICATION_TAG_CLASS,8));
		int seq_nr = dec.decodeSequence();
		entry = dec.decodeOctetString();
		attributes.decode(dec);
		dec.endOf(seq_nr);
	}
	/** encoding method.
	* @param enc
	*        encoder object derived from com.ibm.asn1.ASN1Encoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            encoding error
	*/
	public void encode (ASN1Encoder enc) throws ASN1Exception {
		enc.nextIsImplicit(enc.makeTag(enc.APPLICATION_TAG_CLASS,8));
		int seq_nr = enc.encodeSequence();
		enc.encodeOctetString(entry);
		attributes.encode(enc);
		enc.endOf(seq_nr);
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
		os.println("{ -- SEQUENCE --");
		for(int ii = 0; ii < indent+2; ii++) os.print(' ');
		os.print("entry = ");
		try {
			(new HexOutputStream(os)).write(entry);
		} catch (java.io.IOException ex) {
			os.print("( unprintable OCTET STRING value )");
		}
		os.println(',');

		for(int ii = 0; ii < indent+2; ii++) os.print(' ');
		os.print("attributes = ");
		attributes.print(os, indent+2);

		os.println();
		for(int ii = 0; ii < indent; ii++) os.print(' ');
		os.print('}');
	}
}