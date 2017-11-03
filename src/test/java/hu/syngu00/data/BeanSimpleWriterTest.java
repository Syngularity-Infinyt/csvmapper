package hu.syngu00.data;

import hu.syngu00.data.exceptions.NotAnnotatedException;
import hu.syngu00.data.models.simple.*;
import hu.syngu00.data.writers.CsvWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class BeanSimpleWriterTest {
    private static final String outputNormalOrder = "num,value,name\n'1',true,random\n'2',false,";
    private static final String outputReverseOrder = "name,value,num\nrandom,true,1\n,false,2";


    private Collection<SimpleTestEntity> data = new ArrayList<>();

    @Before
    public void setUp() {
        data.add(new SimpleTestEntity(1, true, "random"));
        data.add(new SimpleTestEntity(2, false, null));
    }

    @Test
    public void simpleAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
            .setWithHeader(true)
            .build();
        Assert.assertEquals(outputNormalOrder, writer.writeString(data));
    }

    @Test
    public void simpleMixInAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
            .setMixin(SimpleMixInEntity.class)
            .setWithHeader(true)
            .build();
        Assert.assertEquals(outputNormalOrder, writer.writeString(data));
    }


    @Test
    public void simpleOrderedAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
            .setMixin(SimpleMixInOrderedEntity.class)
            .setWithHeader(true)
            .build();
        Assert.assertEquals(outputReverseOrder, writer.writeString(data));
    }

    @Test
    public void simpleAbstractMixIndAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
            .setMixin(SimpleAbstractMixin.class)
            .setWithHeader(true)
            .build();
        Assert.assertEquals(outputNormalOrder, writer.writeString(data));
    }

    @Test
    public void simpleInterfaceMixIndAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
            .setMixin(SimpleInterfaceMixin.class)
            .setWithHeader(true)
            .build();
        Assert.assertEquals(outputNormalOrder, writer.writeString(data));
    }

    @Test(expected = NotAnnotatedException.class)
    public void emptyEntityTest() {
        CsvWriter<EmptyTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(EmptyTestEntity.class).build();
    }

    @Test(expected = NotAnnotatedException.class)
    public void notAnnotatedEntityTest() {
        CsvWriter<SimpleNotAnnotatedEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleNotAnnotatedEntity.class).build();
    }
}

