/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

/**
 * Exception thrown when provided with invalid Parameters
 * @author Przemysław Korzec
 * @version 0.4
 */
public class InvalidLoanParametersException extends Exception{
    /**
     * Exception constructor
     * @param message Exception message
     */
    public InvalidLoanParametersException(String message) {
        super(message);
    }
    
    /**
     * Exception constructor with a cause argument
     * @param message Exception message
     * @param cause Cause
     */
    public InvalidLoanParametersException(String message, Throwable cause){
        super(message,cause);
    }
}
