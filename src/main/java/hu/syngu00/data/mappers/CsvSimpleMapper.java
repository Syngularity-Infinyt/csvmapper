package hu.syngu00.data.mappers;

import hu.syngu00.data.exceptions.ColumnCreateException;
import hu.syngu00.data.exceptions.NoFieldDeclaredException;
import hu.syngu00.data.models.Column;
import hu.syngu00.data.models.Scheme;

import java.lang.reflect.Field;

public class CsvSimpleMapper<S> implements CsvMapper {

    private Class<S> sourceClazz;

    public CsvSimpleMapper(Class<S> sourceClazz) {
        this.sourceClazz = sourceClazz;
    }

    @Override
    public Scheme getSchema() {
        Scheme scheme = new Scheme();
        Field[] fields = sourceClazz.getDeclaredFields();

        if (fields.length == 0) {
            throw new NoFieldDeclaredException("class " + this.sourceClazz.getName() + " has no declared field");
        }

        for (int i = 0; i < fields.length; i++) {
            Field current = fields[i];
            Column column = new Column();

            String name = current.getName();
            column.setName(name);
            String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

            try {
                column.setMethod(this.sourceClazz.getMethod(current.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName));
            } catch (NoSuchMethodException e) {
                throw new ColumnCreateException("class " + this.sourceClazz.getName() + " has no method named " + (current.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName), e);
            }

            scheme.addColumn(column);
        }
        return scheme;
    }

}
