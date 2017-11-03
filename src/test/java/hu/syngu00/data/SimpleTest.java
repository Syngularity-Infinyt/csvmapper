package hu.syngu00.data;

import hu.syngu00.data.exceptions.NoFieldDeclaredException;
import hu.syngu00.data.models.simple.EmptyTestEntity;
import hu.syngu00.data.models.simple.SimpleTestEntity;
import hu.syngu00.data.writers.CsvWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleTest {

    private static final String outputWithOutHeader = "1,true,random\n2,false,text";
    private static final String outputWithHeader = "num,value,text\n1,true,random\n2,false,text";

    private Collection<SimpleTestEntity> data = new ArrayList<>();

    @Before
    public void setUp() {
        data.add(new SimpleTestEntity(1, true, "random"));
        data.add(new SimpleTestEntity(2, false, "text"));
    }

    @Test
    public void simpleWriterTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(SimpleTestEntity.class).build();
        Assert.assertEquals(outputWithOutHeader, writer.writeString(data));
    }

    @Test
    public void simpleWriterWithHeaderTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(SimpleTestEntity.class)
            .setWithHeader(true)
            .build();
        Assert.assertEquals(outputWithHeader, writer.writeString(data));
    }

    @Test
    public void simpleWriterEmptyListTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(SimpleTestEntity.class).build();
        Assert.assertEquals("", writer.writeString(new ArrayList<>()));
    }

    @Test
    public void simpleWriterNullListTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(SimpleTestEntity.class).build();
        Assert.assertEquals("", writer.writeString(null));
    }


    @Test
    public void simpleWriterSeparatorTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(SimpleTestEntity.class)
            .setSeparator(";")
            .build();
        Assert.assertEquals(outputWithOutHeader.replace(",", ";"), writer.writeString(data));
    }

    @Test
    public void simpleWriterWithHeaderAndSeparatorTest() throws Exception {
        CsvWriter<SimpleTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(SimpleTestEntity.class)
            .setSeparator(";")
            .setWithHeader(true)
            .build();

        Assert.assertEquals(outputWithHeader.replace(",", ";"), writer.writeString(data));
    }

    @Test(expected = NoFieldDeclaredException.class)
    public void emptyEntityTest() {
        CsvWriter<EmptyTestEntity> writer = new CsvWriterBuilder.SimpleBuilder<>(EmptyTestEntity.class).build();
    }
}
