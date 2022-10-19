package com.example.tesseract.domain.exceptions;

public class ImageErrorException extends RuntimeException {

    public ImageErrorException(String message) {
        super(message);
    }

}