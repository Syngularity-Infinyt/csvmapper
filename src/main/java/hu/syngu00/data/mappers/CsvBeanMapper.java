package hu.syngu00.data.mappers;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;
import hu.syngu00.data.annotations.CsvSerialize;
import hu.syngu00.data.exceptions.ColumnCreateException;
import hu.syngu00.data.exceptions.NotAnnotatedException;
import hu.syngu00.data.models.Column;
import hu.syngu00.data.models.Scheme;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by syngu on 2016-10-15.
 */
public class CsvBeanMapper<SOURCE> implements CsvMapper<SOURCE> {


    private Class<SOURCE> sourceClazz;
    private Class mixInClazz;

    public CsvBeanMapper(Class<SOURCE> sourceClazz) {
        this.sourceClazz = sourceClazz;
    }

    public <MIXIN> void addMixIn(Class<MIXIN> mixInClazz) {
        this.mixInClazz = mixInClazz;
    }

    public Scheme getSchema() {
        Scheme scheme = new Scheme();

        if (hasMixIn()) {
            scheme.addColumn(getColumnsFor(mixInClazz));
        } else {
            scheme.addColumn(getColumnsFor(sourceClazz));
        }

        CsvOrder[] order;

        if (hasMixIn()) {
            order = (CsvOrder[]) mixInClazz.getAnnotationsByType(CsvOrder.class);
        } else {
            order = sourceClazz.getAnnotationsByType(CsvOrder.class);
        }

        if (order.length > 0) {
            scheme.createOrder(order[0]);
        }

        return scheme;
    }

    private Set<Column> getColumnsFor(final Class clazz) throws NotAnnotatedException {

        Set<Column> columns = new LinkedHashSet<>();
        Class clazz_ = clazz;

        while (clazz_ != null) {
            Field[] fields = clazz_.getDeclaredFields();
            for (Field current : fields) {
                if (current.getAnnotationsByType(CsvColumn.class).length > 0) {
                    columns.add(fieldToColumn(current));
                }
            }
            clazz_ = clazz_.getSuperclass();
        }

        Method[] methods = clazz.getMethods();
        for (Method current : methods) {
            if (current.getAnnotationsByType(CsvColumn.class).length > 0) {
                columns.add(methodToColumn(current));
            }
        }

        if (columns.size() == 0) {
            throw new NotAnnotatedException(clazz.getName() + " has no @CsvColumn annotation");
        }

        return columns;
    }


    private Column fieldToColumn(Field field) {
        Column column = new Column();
        CsvColumn csvColumn = field.getAnnotationsByType(CsvColumn.class)[0];
        CsvSerialize[] serializers = field.getAnnotationsByType(CsvSerialize.class);

        String name = field.getName();
        String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

        try {
            column.setMethod(this.sourceClazz.getMethod(field.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName));
        } catch (NoSuchMethodException e) {
            throw new ColumnCreateException("class " + this.sourceClazz.getName() + " has no method named " + (field.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName));
        }

        if (!csvColumn.value().equals("")) {
            column.setName(csvColumn.value());
        } else {
            column.setName(name);
        }
        if (serializers.length > 0) {
            column.setSerializer(serializers[0]);
        }
        return column;
    }

    private Column methodToColumn(Method method) throws ColumnCreateException {
        Column column = new Column();
        CsvColumn csvColumn = method.getAnnotationsByType(CsvColumn.class)[0];
        CsvSerialize[] serializers = method.getAnnotationsByType(CsvSerialize.class);
        String name = method.getName();
        try {
            column.setMethod(this.sourceClazz.getMethod(name));
        } catch (NoSuchMethodException e) {
            throw new ColumnCreateException("class " + this.sourceClazz.getName() + " has no method named " + name);
        }
        if (!csvColumn.value().equals("")) {
            column.setName(csvColumn.value());
        } else {
            column.setName(name);
        }
        if (serializers.length > 0) {
            column.setSerializer(serializers[0]);
        }
        return column;
    }

    private boolean hasMixIn() {
        return this.mixInClazz != null;
    }


}

