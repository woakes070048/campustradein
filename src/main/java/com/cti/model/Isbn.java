package com.cti.model;

import com.cti.exception.InvalidISBNException;

import java.text.MessageFormat;
import java.util.IllegalFormatException;

/**
 * @author ifeify
 */
public final class Isbn {
    private final String isbn13;
    private final String isbn10;

    public Isbn(String isbn) throws InvalidISBNException {
        // strip out spaces and dashes
        isbn = isbn.replaceAll("(\\s|-)", "");
        if(isbn.length() != 10 && isbn.length() != 13) {
            throw new InvalidISBNException(MessageFormat.format(
                                    "ISBN has length {} but should be either 10 or 13",
                                    isbn));
        }
        checkValid(isbn);
        if(isbn.length() == 13) {
            isbn13 = isbn;
            isbn10 = convertTo10Digit(isbn);
        } else {
            isbn10 = isbn;
            isbn13 = convertTo13Digit(isbn);
        }

    }

    private String convertTo10Digit(String isbn) {
        return null;
    }

    private String convertTo13Digit(String isbn) {
        StringBuilder isbnBuilder = new StringBuilder();
        isbnBuilder.append("978");
        isbnBuilder.append(isbn.substring(0, 9));
        // calculate checksum
        int checksum = 0;
        for(int i = 0; i < isbnBuilder.length(); i++) {
            int digit = ((i % 2 == 0) ? 1 : 3);
            checksum += (((int)isbnBuilder.charAt(i)) - 48) * digit;
        }
        checksum = 10 - (checksum % 10);
        isbnBuilder.append(checksum);
        return isbnBuilder.toString();
    }

    private void checkValid(String number) throws InvalidISBNException {
        if(number.length() == 10) { // isbn 10 validation
            int sum = 0;
            for(int i = 0; i < 9; i++) {
                int digit = Integer.parseInt(number.substring(i, i+1));
                sum += ((10 - i) * digit);
            }
            String checksum = Integer.toString((11 - (sum % 11)) % 11);
            if("10".equals(checksum)) {
                checksum = "X";
            }
            if(!checksum.equals(number.substring(9))) {
                String error = MessageFormat.format(
                            "Checksum for ISBN {} is {} but should be {}",
                            number, number.substring(9), checksum);
                throw new InvalidISBNException(error);
            }
        } else { // isbn 13 validation
            int sum = 0;
            for(int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(number.substring(i, i+1));
                sum += (i % 2 == 0) ? digit : digit * 3;
            }
            int checksum = 10 - (sum % 10);
            if(checksum == 10) {
                checksum = 0;
            }
            if(checksum != Integer.parseInt(number.substring(12))) {
                String error = MessageFormat.format(
                        "Checksum for ISBN {} is {} but should be {}",
                        number, number.substring(12), checksum);
                throw new InvalidISBNException(error);
            }
        }
    }

    public String toIsbn13() {
        return isbn13;
    }

    public String toIsbn10() {
        return isbn10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Isbn isbn = (Isbn)o;

        return isbn13.equals(isbn.isbn13) && isbn10.equals(isbn.isbn10);
    }

    @Override
    public int hashCode() {
        int result = isbn13.hashCode();
        result = 31 * result + isbn10.hashCode();
        return result;
    }
}
