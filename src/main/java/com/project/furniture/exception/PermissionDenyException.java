package com.project.furniture.exception;

public class PermissionDenyException extends Exception{
    public PermissionDenyException(String message){
        super(message);
    }
}