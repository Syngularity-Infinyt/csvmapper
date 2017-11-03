package hu.syngu00.data.exceptions;

public class CouldNotWriteRowException extends RuntimeException {

    public CouldNotWriteRowException(String message) {
        super(message);
    }

    public CouldNotWriteRowException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotWriteRowException(Throwable cause) {
        super(cause);
    }

    public CouldNotWriteRowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
