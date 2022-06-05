package com.drops.banking.exceptions;

public class BalanceNotSufficientException extends Exception{
    public BalanceNotSufficientException(String msg){
        super(msg);
    }
}
