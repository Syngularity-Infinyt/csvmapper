package hu.syngu00.data.writers;

import hu.syngu00.data.exceptions.CouldNotWriteRowException;
import hu.syngu00.data.exceptions.SerializeTypeMissMatchException;
import hu.syngu00.data.models.Column;
import hu.syngu00.data.models.Scheme;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;


public class CsvSchemaWriter<T> implements CsvWriter<T> {
    private final Scheme scheme;

    public CsvSchemaWriter(Scheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public byte[] writeBytes(Collection<T> ts) {
        StringBuilder builder = new StringBuilder();

        if (scheme.isWithHeader()) {
            builder.append(writeHeader());
        }

        if (ts != null && !ts.isEmpty()) {
            for (T t : ts) {
                builder.append(writeRow(t));
            }
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString().getBytes(scheme.getCharset());
    }

    @Override
    public void writeToFile(Collection<T> ts, File file) throws IOException {
        Files.write(file.toPath(), writeBytes(ts), StandardOpenOption.CREATE);
    }

    @Override
    public String writeString(Collection<T> ts) {
        return new String(writeBytes(ts));
    }

    @Override
    public void writeToFile(Collection<T> ts, Path path) throws IOException {
        Files.write(path, writeBytes(ts), StandardOpenOption.CREATE);
    }

    private StringBuilder writeHeader() {
        StringBuilder builder = new StringBuilder();

        for (Column column : scheme.getColumns()) {
            builder.append(column.getName()).append(scheme.getSeparator());
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append('\n');
        return builder;
    }

    private StringBuilder writeRow(T t) {
        StringBuilder builder = new StringBuilder();
        if (t == null) {
            return builder;
        }

        for (Column column : scheme.getColumns()) {
            builder.append(writeColumn(column, t));
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append('\n');
        return builder;
    }

    private StringBuilder writeColumn(Column column, T t) {
        StringBuilder builder = new StringBuilder();

        if (column.isSerialized()) {
            builder.append(invokeSerializer(column, t));
        } else {
            Object o = invokeMethod(column.getMethod(), t);
            if (o == null) {
                builder.append("");
            } else {
                builder.append(invokeMethod(column.getMethod(), t));
            }
        }

        if (column.isEncapsulate()) {
            builder.insert(0, column.getEncaper()).append(column.getEncaper());
        }

        builder.append(scheme.getSeparator());
        return builder;
    }

    private Object invokeMethod(Method method, Object object) {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CouldNotWriteRowException("Could not write row caused by", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Object invokeSerializer(Column column, T t) {
        try {
            return column.getSerializer().serialize(invokeMethod(column.getMethod(), t));
        } catch (ClassCastException e) {
            throw new SerializeTypeMissMatchException(column.getSerializer().getClass().getName() + "serialize(T t) don't accept type " + invokeMethod(column.getMethod(), t).getClass().getName(), e);
        }
    }

}
