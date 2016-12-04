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

/**
 * Created by syngu on 2016-10-16.
 */
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

        return builder.toString().getBytes();
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

    private String writeHeader() {
        StringBuilder builder = new StringBuilder();

        for (Column column : scheme.getColumns()) {
            builder.append(column.getName()).append(scheme.getSeparator());
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append('\n');
        return builder.toString();
    }

    private String writeRow(T t) {
        StringBuilder builder = new StringBuilder();

        for (Column column : scheme.getColumns()) {
            if (column.isSerialized()) {
                builder.append(invokeSerializer(column, t)).append(scheme.getSeparator());
            } else {
                if (t == null) {
                    builder.append("").append(scheme.getSeparator());
                }
                builder.append(invokeMethod(column.getMethod(), t)).append(scheme.getSeparator());
            }
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append('\n');
        return builder.toString();
    }

    private Object invokeMethod(Method method, Object object) throws CouldNotWriteRowException {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CouldNotWriteRowException("Could not write row caused by", e);
        }
    }

    private Object invokeSerializer(Column column, T t) throws SerializeTypeMissMatchException {
        try {
            return column.getSerializer().serialize(invokeMethod(column.getMethod(), t));
        } catch (ClassCastException e) {
            throw new SerializeTypeMissMatchException(column.getSerializer().getClass().getName() + "serialize(T t) don't accept type " + invokeMethod(column.getMethod(), t).getClass().getName());
        }
    }

}
