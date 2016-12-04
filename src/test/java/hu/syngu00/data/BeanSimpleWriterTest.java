package hu.syngu00.data;

import hu.syngu00.data.exceptions.NotAnnotatedException;
import hu.syngu00.data.models.simple.*;
import hu.syngu00.data.writers.CsvWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by syngu on 2016-12-04.
 */
public class BeanSimpleWriterTest {
    private static final String outputNormalOrder = "num,value,name\n1,true,random\n2,false,text";
    private static final String outputReverseOrder = "name,value,num\nrandom,true,1\ntext,false,2";


    private Collection<SimpleTestEntity> data = new ArrayList<>();

    @Before
    public void setUp() {
        data.add(new SimpleTestEntity(1, true, "random"));
        data.add(new SimpleTestEntity(2, false, "text"));
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
                .addMixin(SimpleMixInEntity.class)
                .setWithHeader(true)
                .build();
        Assert.assertEquals(outputNormalOrder, writer.writeString(data));
    }


    @Test
    public void simpleOrderedAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
                .addMixin(SimpleMixInOrderedEntity.class)
                .setWithHeader(true)
                .build();
        Assert.assertEquals(outputReverseOrder, writer.writeString(data));
    }

    @Test
    public void simpleAbstractMixIndAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
                .addMixin(SimpleAbstractMixin.class)
                .setWithHeader(true)
                .build();
        Assert.assertEquals(outputNormalOrder, writer.writeString(data));
    }

    @Test
    public void simpleInterfaceMixIndAnnotationTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SimpleTestEntity.class)
                .addMixin(SimpleInterfaceMixin.class)
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

