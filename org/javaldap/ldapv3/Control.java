package org.javaldap.ldapv3;

//-----------------------------------------------------------------------------
//   NOTE: this is a machine generated file - editing not recommended
//
//   File: ./src/org/javaldap/ldapv3/Control.java
//
//   Java class for ASN.1 definition Control as defined in
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

/** This class represents the ASN.1 SEQUENCE type <tt>Control</tt>.
  * For each sequence member, sequence classes contain a
  * public member variable of the corresponding Java type.
  * @author Snacc for Java
  * @version Fri Jul  2 18:01:43 1999
 
  */

public class Control implements LDAPv3 {

	/** member variable representing the sequence member controlType of type byte[] */
	public byte[] controlType;
	/** member variable representing the sequence member criticality of type boolean */
	public boolean criticality = false;
	/** member variable representing the sequence member controlValue of type byte[] */
	public byte[] controlValue = null;

	/** default constructor */
	public Control() {}
	/** copy constructor */
	public Control (Control arg) {
		controlType = new byte[arg.controlType.length];
		System.arraycopy(arg.controlType,0,controlType,0,arg.controlType.length);
		criticality = arg.criticality;
		controlValue = new byte[arg.controlValue.length];
		System.arraycopy(arg.controlValue,0,controlValue,0,arg.controlValue.length);
	}
	/** decoding method.
	* @param dec
	*        decoder object derived from com.ibm.asn1.ASN1Decoder
	* @exception com.ibm.asn1.ASN1Exception 
	*            decoding error
	*/
	public void decode (ASN1Decoder dec) throws ASN1Exception {
		int seq_nr = dec.decodeSequence();
		controlType = dec.decodeOctetString();
		if (!dec.nextIsDefault(dec.makeTag(dec.UNIVERSAL_TAG_CLASS,1))) {
			criticality = dec.decodeBoolean();
		}
		if (!dec.nextIsOptional(dec.makeTag(dec.UNIVERSAL_TAG_CLASS,4))) {
			controlValue = dec.decodeOctetString();
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
		enc.encodeOctetString(controlType);
		if (enc.encodeDefault() || criticality != false) {
			enc.encodeBoolean(criticality);
		}
		if (controlValue != null) {
			enc.encodeOctetString(controlValue);
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
		os.print("controlType = ");
		try {
			(new HexOutputStream(os)).write(controlType);
		} catch (java.io.IOException ex) {
			os.print("( unprintable OCTET STRING value )");
		}

		os.println(',');
		for(int ii = 0; ii < indent+2; ii++) os.print(' ');
		os.print("criticality = ");
		os.print(criticality);

		os.println(',');
		if (controlValue != null) {
			for(int ii = 0; ii < indent+2; ii++) os.print(' ');
			os.print("controlValue = ");
			try {
				(new HexOutputStream(os)).write(controlValue);
			} catch (java.io.IOException ex) {
				os.print("( unprintable OCTET STRING value )");
			}
		}

		os.println();
		for(int ii = 0; ii < indent; ii++) os.print(' ');
		os.print('}');
	}
}
