package hu.syngu00.data.models;

import hu.syngu00.data.annotations.CsvSerialize;
import hu.syngu00.data.exceptions.ColumnCreateException;
import hu.syngu00.data.serialize.CsvSerializer;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class Column {
    private String name;
    private Method method;
    private CsvSerializer serializer = null;
    boolean encapsulate;
    String encaper;

    public void setSerializer(CsvSerialize serializer) {
        try {
            this.serializer = serializer.value().newInstance();
        } catch (InstantiationException e) {
            throw new ColumnCreateException(serializer + " has no default constructor", e);
        } catch (IllegalAccessException e) {
            throw new ColumnCreateException(serializer + " default constructor is not public", e);
        }
    }

    public boolean isSerialized() {
        return serializer != null;
    }


}
