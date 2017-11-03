package hu.syngu00.data.exceptions;

public class NoFieldDeclaredException extends RuntimeException {
    public NoFieldDeclaredException(String message) {
        super(message);
    }

    public NoFieldDeclaredException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFieldDeclaredException(Throwable cause) {
        super(cause);
    }

    public NoFieldDeclaredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
