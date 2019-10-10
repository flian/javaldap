package org.javaldap.ldapv3;

//-----------------------------------------------------------------------------
//   NOTE: this is a machine generated file - editing not recommended
//
//   File: ./src/org/javaldap/ldapv3/ModifyRequestSeqOfSeqEnum.java
//
//   Java class for ASN.1 definition ModifyRequestSeqOfSeqEnum as defined in
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

/** This class represents the ASN.1 simple definition <tt>ModifyRequestSeqOfSeqEnum</tt>.
  * Simple classes contain a member variable <tt>value</tt> of the
  * type that is FINALLY referred to.
  * @author Snacc for Java
  * @version Fri Jul  2 18:01:43 1999
 
  */

public class ModifyRequestSeqOfSeqEnum implements LDAPv3 {

	public int value;

	public static final int ADD = 0;
	public static final int DELETE = 1;
	public static final int REPLACE = 2;

	/** default constructor */
	public ModifyRequestSeqOfSeqEnum() {}
	public ModifyRequestSeqOfSeqEnum(int arg) {
		value = arg;
	}
	/** copy constructor */
	public ModifyRequestSeqOfSeqEnum (ModifyRequestSeqOfSeqEnum arg) {
		value = arg.value;
	}
	/** decoding method.
	* @param dec
	*        decoder object derived from com.ibm.asn1.ASN1Decoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            decoding error
	*/
	public void decode (ASN1Decoder dec) throws ASN1Exception {
		value = dec.decodeEnumeration();
	}
	/** encoding method.
	* @param enc
	*        encoder object derived from com.ibm.asn1.ASN1Encoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            encoding error
	*/
	public void encode (ASN1Encoder enc) throws ASN1Exception {
		enc.encodeEnumeration(value);
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
		os.print(value);
	}
}