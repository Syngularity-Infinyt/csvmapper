package hu.syngu00.data;

import hu.syngu00.data.mappers.CsvBeanMapper;
import hu.syngu00.data.mappers.CsvSimpleMapper;
import hu.syngu00.data.models.Scheme;
import hu.syngu00.data.writers.CsvSchemaWriter;
import hu.syngu00.data.writers.CsvWriter;

import java.nio.charset.Charset;

public class CsvWriterBuilder {

    private CsvWriterBuilder() {
    }

    public static class SimpleBuilder<S> {

        protected final Class<S> sourceClazz;
        protected String separator = ",";
        protected boolean withHeader = false;
        protected Charset charset = Charset.forName("UTF-8");

        public SimpleBuilder(Class<S> sourceClazz) {
            this.sourceClazz = sourceClazz;
        }

        public SimpleBuilder<S> setCharset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public SimpleBuilder<S> setCharset(String charset) {
            this.charset = Charset.forName(charset);
            return this;
        }

        public SimpleBuilder<S> setSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        public SimpleBuilder<S> setWithHeader(boolean withHeader) {
            this.withHeader = withHeader;
            return this;
        }

        public CsvWriter<S> build() {
            CsvSimpleMapper<S> mapper = new CsvSimpleMapper<>(this.sourceClazz);
            Scheme scheme = mapper.getSchema();
            scheme.setSeparator(this.separator);
            scheme.setWithHeader(this.withHeader);
            scheme.setCharset(this.charset);
            return new CsvSchemaWriter<>(scheme);
        }
    }


    public static class BeanBuilder<S> {

        protected final Class<S> sourceClazz;
        protected String separator = ",";
        protected boolean withHeader = false;
        protected Charset charset = Charset.forName("UTF-8");
        private Class<?> mixinClazz = null;

        public BeanBuilder(Class<S> sourceClazz) {
            this.sourceClazz = sourceClazz;
        }

        public BeanBuilder<S> setMixin(Class mixinClazz) {
            this.mixinClazz = mixinClazz;
            return this;
        }

        public BeanBuilder<S> setCharset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public BeanBuilder<S> setCharset(String charset) {
            this.charset = Charset.forName(charset);
            return this;
        }

        public BeanBuilder<S> setSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        public BeanBuilder<S> setWithHeader(boolean withHeader) {
            this.withHeader = withHeader;
            return this;
        }

        public CsvWriter<S> build() {
            CsvBeanMapper<S> mapper = new CsvBeanMapper<>(this.sourceClazz);
            mapper.addMixIn(mixinClazz);
            Scheme scheme = mapper.getSchema();
            scheme.setSeparator(this.separator);
            scheme.setWithHeader(this.withHeader);
            scheme.setCharset(this.charset);
            return new CsvSchemaWriter<>(scheme);
        }

    }
}
