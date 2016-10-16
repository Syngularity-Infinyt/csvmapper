package hu.syngu00.data.serialize;

/**
 * Created by syngu on 2016-10-15.
 */
public interface CsvSerializer<T> {
    String serialize(T t);
}
