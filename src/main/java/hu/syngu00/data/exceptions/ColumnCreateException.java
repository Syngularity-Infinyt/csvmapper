package hu.syngu00.data.exceptions;


public class ColumnCreateException extends RuntimeException {

    public ColumnCreateException(String message) {
        super(message);
    }

    public ColumnCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumnCreateException(Throwable cause) {
        super(cause);
    }

    public ColumnCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
