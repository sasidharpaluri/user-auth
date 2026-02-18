package dev.sasidhar.userauth.Exceptions;


public class InsufficientDetails extends RuntimeException{
    public InsufficientDetails(String exp){
        super(exp);
    }
}
