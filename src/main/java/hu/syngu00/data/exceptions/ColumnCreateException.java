package hu.syngu00.data.exceptions;

/**
 * Created by syngu on 2016-10-16.
 */
public class ColumnCreateException extends RuntimeException {

    public ColumnCreateException(String message) {
        super(message);
    }

    public ColumnCreateException(String message, Throwable cause) {
        super(message, cause);
    }


}
