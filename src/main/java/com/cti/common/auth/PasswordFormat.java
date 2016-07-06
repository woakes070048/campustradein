package com.cti.common.auth;

public class PasswordFormat {
	public static final String DELIMITER = ":";
	// These constants may be changed without breaking existing hashes.
	public static final int SALT_BYTE_SIZE = 24;
	public static final int HASH_BYTE_SIZE = 18;
	public static final int ITERATIONS = 64000;
	// These constants define the encoding and may not be changed.
	public static final int HASH_SECTIONS = 5;
	public static final int HASH_ALGORITHM_INDEX = 0;
	public static final int ITERATION_INDEX = 1;
	public static final int HASH_SIZE_INDEX = 2;
	public static final int SALT_INDEX = 3;
	public static final int HASH_INDEX = 4;
}
