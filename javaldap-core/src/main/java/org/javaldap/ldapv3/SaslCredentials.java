package org.javaldap.ldapv3;

//-----------------------------------------------------------------------------
//   NOTE: this is a machine generated file - editing not recommended
//
//   File: ./src/org/javaldap/ldapv3/SaslCredentials.java
//
//   Java class for ASN.1 definition SaslCredentials as defined in
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

/** This class represents the ASN.1 SEQUENCE type <tt>SaslCredentials</tt>.
  * For each sequence member, sequence classes contain a
  * public member variable of the corresponding Java type.
  * @author Snacc for Java
  * @version Fri Jul  2 18:01:43 1999
 
  */

public class SaslCredentials implements LDAPv3 {

	/** member variable representing the sequence member mechanism of type byte[] */
	public byte[] mechanism;
	/** member variable representing the sequence member credentials of type byte[] */
	public byte[] credentials = null;

	/** default constructor */
	public SaslCredentials() {}
	/** copy constructor */
	public SaslCredentials (SaslCredentials arg) {
		mechanism = new byte[arg.mechanism.length];
		System.arraycopy(arg.mechanism,0,mechanism,0,arg.mechanism.length);
		credentials = new byte[arg.credentials.length];
		System.arraycopy(arg.credentials,0,credentials,0,arg.credentials.length);
	}
	/** decoding method.
	* @param dec
	*        decoder object derived from com.ibm.asn1.ASN1Decoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            decoding error
	*/
	public void decode (ASN1Decoder dec) throws ASN1Exception {
		int seq_nr = dec.decodeSequence();
		mechanism = dec.decodeOctetString();
		if (!dec.nextIsOptional(dec.makeTag(dec.UNIVERSAL_TAG_CLASS,4))) {
			credentials = dec.decodeOctetString();
		}
		dec.endOf(seq_nr);
	}
	/** encoding method.
	* @param enc
	*        encoder object derived from com.ibm.asn1.ASN1Encoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            encoding error
	*/
	public void encode (ASN1Encoder enc) throws ASN1Exception {
		int seq_nr = enc.encodeSequence();
		enc.encodeOctetString(mechanism);
		if (credentials != null) {
			enc.encodeOctetString(credentials);
		}
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
		os.print("mechanism = ");
		try {
			(new HexOutputStream(os)).write(mechanism);
		} catch (java.io.IOException ex) {
			os.print("( unprintable OCTET STRING value )");
		}

		os.println(',');
		if (credentials != null) {
			for(int ii = 0; ii < indent+2; ii++) os.print(' ');
			os.print("credentials = ");
			try {
				(new HexOutputStream(os)).write(credentials);
			} catch (java.io.IOException ex) {
				os.print("( unprintable OCTET STRING value )");
			}
		}

		os.println();
		for(int ii = 0; ii < indent; ii++) os.print(' ');
		os.print('}');
	}
}
