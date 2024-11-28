package org.example.cloudStorage.exceptions;

public class ErrorDeleteFile extends RuntimeException{
    public ErrorDeleteFile() {
        super("Error delete file");
    }
}
