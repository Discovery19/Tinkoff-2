package edu.java.api.exceptions;
//CHECKSTYLE:OFF: checkstyle:AvoidNoArgumentSuperConstructorCall
public class ResourceNotFoundException extends ScrapperException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
