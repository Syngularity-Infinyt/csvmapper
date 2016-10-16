package hu.syngu00.data;

import hu.syngu00.data.exceptions.ColumnCreateException;
import hu.syngu00.data.models.Column;
import hu.syngu00.data.models.Scheme;

import java.lang.reflect.Field;

/**
 * Created by syngu on 2016-10-16.
 */
public class CsvSimpleMapper<SOURCE> implements CsvMapper<SOURCE> {

    Class<SOURCE> sourceClazz = getSourceClass();

    @Override
    public Scheme getSchemaFor() {
        Scheme scheme = new Scheme();
        Field[] fields = sourceClazz.getFields();
        for (int i = 0; i < fields.length; i++) {
            Column column = new Column();
            Field current = fields[i];
            String name = current.getName();
            column.setName(name);
            try {
                column.setMethod(this.sourceClazz.getMethod(current.getType().equals(boolean.class) ? "is" + name : "get" + name));
            } catch (NoSuchMethodException e) {
                throw new ColumnCreateException("class " + this.sourceClazz.getName() + " has no method named " + (current.getType().equals(boolean.class) ? "is" + name : "get" + name));
            }
            scheme.addColumn(column);
        }
        return scheme;
    }

}
