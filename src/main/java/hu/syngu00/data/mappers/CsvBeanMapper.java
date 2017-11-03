package hu.syngu00.data.mappers;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;
import hu.syngu00.data.annotations.CsvSerialize;
import hu.syngu00.data.exceptions.ColumnCreateException;
import hu.syngu00.data.exceptions.NotAnnotatedException;
import hu.syngu00.data.models.Column;
import hu.syngu00.data.models.Scheme;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

public class CsvBeanMapper<S> implements CsvMapper {

    private Class<S> sourceClazz;
    private Class<?> mixInClazz;

    public CsvBeanMapper(Class<S> sourceClazz) {
        this.sourceClazz = sourceClazz;
    }

    public <M> void addMixIn(Class<M> mixInClazz) {
        this.mixInClazz = mixInClazz;
    }

    @Override
    public Scheme getSchema() {
        Scheme scheme = new Scheme();

        if (hasMixIn()) {
            scheme.addColumn(getColumnsFor(mixInClazz));
        } else {
            scheme.addColumn(getColumnsFor(sourceClazz));
        }

        CsvOrder[] order;
        if (hasMixIn()) {
            order = mixInClazz.getAnnotationsByType(CsvOrder.class);
        } else {
            order = sourceClazz.getAnnotationsByType(CsvOrder.class);
        }

        if (order.length > 0) {
            scheme.createOrder(order[0]);
        }

        return scheme;
    }

    private Set<Column> getColumnsFor(@NonNull Class<?> clazz) {

        Set<Column> columns = new LinkedHashSet<>();
        Class clazzTemp = clazz;

        while (clazzTemp != null) {
            Field[] fields = clazzTemp.getDeclaredFields();
            for (Field current : fields) {
                if (current.getAnnotationsByType(CsvColumn.class).length > 0) {
                    columns.add(fieldToColumn(current));
                }
            }
            clazzTemp = clazzTemp.getSuperclass();
        }

        @SuppressWarnings("squid:S2259")
        Method[] methods = clazz.getMethods();
        for (Method current : methods) {
            if (current.getAnnotationsByType(CsvColumn.class).length > 0) {
                columns.add(methodToColumn(current));
            }
        }

        if (columns.isEmpty()) {
            throw new NotAnnotatedException(clazz.getName() + " has no @CsvColumn annotation");
        }

        return columns;
    }


    private Column fieldToColumn(Field field) {
        CsvColumn csvColumn = field.getAnnotationsByType(CsvColumn.class)[0];
        Column column = buildColumn(csvColumn);

        String name = field.getName();
        String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

        try {
            column.setMethod(this.sourceClazz.getMethod(field.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName));
        } catch (NoSuchMethodException e) {
            throw new ColumnCreateException("class " + this.sourceClazz.getName() + " has no method named " + (field.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName), e);
        }

        if (!"".equals(csvColumn.value())) {
            column.setName(csvColumn.value());
        } else {
            column.setName(name);
        }

        if (field.isAnnotationPresent(CsvSerialize.class)) {
            column.setSerializer(field.getAnnotationsByType(CsvSerialize.class)[0]);
        }
        return column;
    }

    private Column methodToColumn(Method method) {
        CsvColumn csvColumn = method.getAnnotationsByType(CsvColumn.class)[0];
        Column column = buildColumn(csvColumn);

        String name = method.getName();
        try {
            column.setMethod(this.sourceClazz.getMethod(name));
        } catch (NoSuchMethodException e) {
            throw new ColumnCreateException("class " + this.sourceClazz.getName() + " has no method named " + name, e);
        }

        if (!"".equals(csvColumn.value())) {
            column.setName(csvColumn.value());
        } else {
            column.setName(name);
        }

        if (method.isAnnotationPresent(CsvSerialize.class)) {
            column.setSerializer(method.getAnnotationsByType(CsvSerialize.class)[0]);
        }

        return column;
    }

    private Column buildColumn(CsvColumn annotation) {
        Column column = new Column();
        column.setEncapsulate(annotation.encapsulate());
        column.setEncaper(annotation.encaper());

        return column;
    }

    private boolean hasMixIn() {
        return this.mixInClazz != null;
    }

}

