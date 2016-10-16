package hu.syngu00.data;

import hu.syngu00.data.models.Scheme;

import java.lang.reflect.ParameterizedType;

/**
 * Created by syngu on 2016-10-16.
 */
public interface CsvMapper<SOURCE> {

    Scheme getSchemaFor();

    @SuppressWarnings("unchecked")
    default Class<SOURCE> getSourceClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<SOURCE>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

}
