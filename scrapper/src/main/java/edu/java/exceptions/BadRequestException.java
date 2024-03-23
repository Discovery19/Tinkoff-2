package edu.java.exceptions;
//CHECKSTYLE:OFF: checkstyle:AvoidNoArgumentSuperConstructorCall
public class BadRequestException extends ScrapperException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
