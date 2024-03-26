package edu.java.scrapper.exceptions;
//CHECKSTYLE:OFF: checkstyle:AvoidNoArgumentSuperConstructorCall
public class ScrapperException extends RuntimeException {
    public ScrapperException() {
        super();
    }

    public ScrapperException(String message) {
        super(message);
    }

    public ScrapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScrapperException(Throwable cause) {
        super(cause);
    }
}
