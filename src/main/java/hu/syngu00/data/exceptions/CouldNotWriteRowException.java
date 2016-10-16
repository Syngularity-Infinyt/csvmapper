package hu.syngu00.data.exceptions;

/**
 * Created by syngu on 2016-10-16.
 */
public class CouldNotWriteRowException extends RuntimeException {

    public CouldNotWriteRowException(String message, Throwable cause) {
        super(message, cause);
    }
}
