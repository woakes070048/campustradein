package com.cti.util;

import javax.xml.bind.DatatypeConverter;

public class Base64 {
	public static byte[] fromBase64(String hex) throws IllegalArgumentException {
		return DatatypeConverter.parseBase64Binary(hex);
	}

	public static String toBase64(byte[] array) {
		return DatatypeConverter.printBase64Binary(array);
	}
}
