package hu.syngu00.data.annotations;

import hu.syngu00.data.serialize.CsvSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvSerialize {
    Class<? extends CsvSerializer> value();
}
