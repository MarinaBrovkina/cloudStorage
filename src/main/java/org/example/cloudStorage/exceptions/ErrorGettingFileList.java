package org.example.cloudStorage.exceptions;

public class ErrorGettingFileList extends RuntimeException{
    public ErrorGettingFileList() {
        super("Error getting file list");
    }
}
