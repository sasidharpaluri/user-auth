package dev.sasidhar.userauth.Exceptions;


public class InsufficientDetails extends Exception{
    public InsufficientDetails(String exp){
        super(exp);
    }
}
