package com.sort;

public class SortException extends Exception {
    public SortException(String message) {
        super(message);
    }

    public void show() {
        System.err.println("Exception:\n\t" + getMessage());
    }
}
