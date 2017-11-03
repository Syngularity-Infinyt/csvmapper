package hu.syngu00.data.exceptions;

public class SerializeTypeMissMatchException extends RuntimeException {
    public SerializeTypeMissMatchException(String message) {
        super(message);
    }

    public SerializeTypeMissMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializeTypeMissMatchException(Throwable cause) {
        super(cause);
    }

    public SerializeTypeMissMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
