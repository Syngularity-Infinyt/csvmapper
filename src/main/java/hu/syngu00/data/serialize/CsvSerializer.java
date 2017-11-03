package hu.syngu00.data.serialize;

@FunctionalInterface
public interface CsvSerializer<T> {
    String serialize(T t);
}
