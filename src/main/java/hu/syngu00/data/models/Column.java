package hu.syngu00.data.models;

import hu.syngu00.data.annotations.CsvSerialize;
import hu.syngu00.data.exceptions.ColumnCreateException;
import hu.syngu00.data.serialize.CsvSerializer;

import java.lang.reflect.Method;

/**
 * Created by syngu on 2016-10-15.
 */
public class Column {
    private String name;
    private Method method;
    private CsvSerializer serializer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public CsvSerializer getSerializer() {
        return serializer;
    }

    public void setSerializer(CsvSerializer serializer) {
        this.serializer = serializer;
    }

    public void setSerializer(CsvSerialize serializer) throws ColumnCreateException {
        try {
            this.serializer = serializer.value().newInstance();
        } catch (InstantiationException e) {
            throw new ColumnCreateException(serializer + " has no default constructor");
        } catch (IllegalAccessException e) {
            throw new ColumnCreateException(serializer + " default constructor is not public");
        }
    }

    public boolean isSerialized() {
        return serializer != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (!name.equals(column.name)) return false;
        if (!method.equals(column.method)) return false;
        return serializer != null ? serializer.equals(column.serializer) : column.serializer == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + (serializer != null ? serializer.hashCode() : 0);
        return result;
    }
}
