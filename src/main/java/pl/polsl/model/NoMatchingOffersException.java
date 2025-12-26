package pl.polsl.model;
/**
 * Exception thrown when the provided parameters do not match any of the system's offers
 * @author Przemysław Korzec
 * @version 0.4
 */
public class NoMatchingOffersException extends Exception{
    /**
     * Exception constructor
     * @param message Message to be printed
     */
    public NoMatchingOffersException(String message){
        super(message);
    }
    /**
     * Exception constructor with a cause provided
     * @param message Message to be printed
     * @param cause Cause of the exception
     */
    public NoMatchingOffersException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
