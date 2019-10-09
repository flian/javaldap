package org.javaldap.ldapv3;

//-----------------------------------------------------------------------------
//   NOTE: this is a machine generated file - editing not recommended
//
//   File: ./src/org/javaldap/ldapv3/CompareResponse.java
//
//   Java class for ASN.1 definition CompareResponse as defined in
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

/** This class represents the ASN.1 simple definition <tt>CompareResponse</tt>.
  * Simple classes contain a member variable <tt>value</tt> of the
  * type that is FINALLY referred to.
  * @author Snacc for Java
  * @version Fri Jul  2 18:01:43 1999
 
  */

public class CompareResponse extends LDAPResult implements LDAPv3 {

	/** default constructor */
	public CompareResponse() {}
	/** copy constructor */
	public CompareResponse (CompareResponse arg) {
		super(arg);
	}
	/** decoding method.
	* @param dec
	*        decoder object derived from com.ibm.asn1.ASN1Decoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            decoding error
	*/
	public void decode (ASN1Decoder dec) throws ASN1Exception {
		dec.nextIsImplicit(dec.makeTag(dec.APPLICATION_TAG_CLASS,15));
		super.decode(dec);
	}
	/** encoding method.
	* @param enc
	*        encoder object derived from com.ibm.asn1.ASN1Encoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            encoding error
	*/
	public void encode (ASN1Encoder enc) throws ASN1Exception {
		enc.nextIsImplicit(enc.makeTag(enc.APPLICATION_TAG_CLASS,15));
		super.encode(enc);
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
		super.print(os, indent+2);
	}
}
