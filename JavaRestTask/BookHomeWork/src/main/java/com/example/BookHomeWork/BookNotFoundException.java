package com.example.BookHomeWork;

public class BookNotFoundException extends RuntimeException {
    BookNotFoundException(String barcode) {
        super("Could not find book with the given barcode: " + barcode);
    }
}
