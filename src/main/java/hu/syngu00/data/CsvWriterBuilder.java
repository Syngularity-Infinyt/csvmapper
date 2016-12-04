package hu.syngu00.data;

import hu.syngu00.data.mappers.CsvBeanMapper;
import hu.syngu00.data.mappers.CsvMapper;
import hu.syngu00.data.mappers.CsvSimpleMapper;
import hu.syngu00.data.models.Scheme;
import hu.syngu00.data.writers.CsvSchemaWriter;
import hu.syngu00.data.writers.CsvWriter;

/**
 * Created by syngu on 2016-12-04.
 */
public class CsvWriterBuilder {

    public static class SimpleBuilder<SOURCE> {

        private final Class<SOURCE> sourceClazz;
        private String separator = ",";
        private boolean withHeader = false;

        public SimpleBuilder(Class<SOURCE> sourceClazz) {
            this.sourceClazz = sourceClazz;
        }

        public SimpleBuilder setSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        public SimpleBuilder setWithHeader(boolean withHeader) {
            this.withHeader = withHeader;
            return this;
        }

        public CsvWriter<SOURCE> build() {
            CsvMapper<SOURCE> mapper = new CsvSimpleMapper<>(this.sourceClazz);
            Scheme scheme = mapper.getSchema();
            scheme.setSeparator(this.separator);
            scheme.setWithHeader(this.withHeader);
            return new CsvSchemaWriter<SOURCE>(scheme);
        }
    }


    public static class BeanBuilder<SOURCE> {
        private final Class<SOURCE> sourceClazz;
        private Class mixinClazz = null;

        private String separator = ",";
        private boolean withHeader = false;

        public BeanBuilder(Class<SOURCE> sourceClazz) {
            this.sourceClazz = sourceClazz;
        }

        public BeanBuilder addMixin(Class mixinClazz) {
            this.mixinClazz = mixinClazz;
            return this;
        }

        public BeanBuilder setSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        public BeanBuilder setWithHeader(boolean withHeader) {
            this.withHeader = withHeader;
            return this;
        }

        @SuppressWarnings("unchecked")
        public CsvWriter<SOURCE> build() {
            CsvBeanMapper<SOURCE> mapper = new CsvBeanMapper<>(this.sourceClazz);
            mapper.addMixIn(mixinClazz);
            Scheme scheme = mapper.getSchema();
            scheme.setSeparator(this.separator);
            scheme.setWithHeader(this.withHeader);
            return new CsvSchemaWriter<>(scheme);
        }
    }
}
