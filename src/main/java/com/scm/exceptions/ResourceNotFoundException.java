package com.scm.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    String msg;
    public ResourceNotFoundException(String msg){
       super(msg);
    }

    public ResourceNotFoundException(){
       super("Resource Not Found");
    }

}
