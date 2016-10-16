package hu.syngu00.data.writers;

import hu.syngu00.data.exceptions.CouldNotWriteRowException;
import hu.syngu00.data.models.Column;
import hu.syngu00.data.models.Scheme;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by syngu on 2016-10-16.
 */
public class CsvBeanWriter<T> implements CsvWriter<T> {
    private final Scheme scheme;

    public CsvBeanWriter(Scheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public byte[] writeBytes(Collection<T> ts) {

        StringBuilder builder = new StringBuilder();
        if(scheme.isWithHeader()){
            builder.append(writeHeader());
        }

        for(T t: ts){
            builder.append(writeRow(t));
        }

        builder.deleteCharAt(builder.length()-1);
        return builder.toString().getBytes();
    }

    @Override
    public void writeToFile(Collection<T> ts, File file) throws IOException {
        Files.write( file.toPath(), writeBytes(ts), StandardOpenOption.CREATE);
    }

    private String writeHeader() {
        StringBuilder builder = new StringBuilder();

        for (Column column : scheme.getColumns()){
            builder.append(column.getName()).append(scheme.getSeparator());
        }

        builder.deleteCharAt(builder.length()-1);
        builder.append('\n');
        return builder.toString();
    }

    private String writeRow(T t) {
        StringBuilder builder = new StringBuilder();

        for (Column column : scheme.getColumns()){
            if(column.isSerialized()){
                builder.append(column.getSerializer().serialize(invokeMethod(column.getMethod(), t))).append(scheme.getSeparator());
            }{
                builder.append(invokeMethod(column.getMethod(), t)).append(scheme.getSeparator());
            }
        }

        builder.deleteCharAt(builder.length()-1);
        builder.append('\n');
        return builder.toString();
    }

    private Object invokeMethod(Method method, Object object) throws CouldNotWriteRowException {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new CouldNotWriteRowException("Could not write row caused by", e);
        } catch (InvocationTargetException e) {
            throw new CouldNotWriteRowException("Could not write row caused by", e);
        }
    }



}
