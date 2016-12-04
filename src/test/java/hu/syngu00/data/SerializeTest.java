package hu.syngu00.data;

import hu.syngu00.data.exceptions.SerializeTypeMissMatchException;
import hu.syngu00.data.models.serialize.SerializeInvalidType;
import hu.syngu00.data.models.serialize.SerializeTestEntity;
import hu.syngu00.data.writers.CsvWriter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by syngu on 2016-12-04.
 */
public class SerializeTest {

    @Test
    public void timeSerializeTest() throws Exception {
        Collection<SerializeTestEntity> data = new ArrayList<>();
        data.add(new SerializeTestEntity(new Date(748569600000L)));
        String out = "date\n1993-09-21 02:00";

        CsvWriter<SerializeTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SerializeTestEntity.class)
                .setWithHeader(true)
                .build();
        Assert.assertEquals(out, writer.writeString(data));
    }

    @Test
    public void timeSerializeWithNullTest() throws Exception {
        Collection<SerializeTestEntity> data = new ArrayList<>();
        data.add(new SerializeTestEntity(null));

        CsvWriter<SerializeTestEntity> writer = new CsvWriterBuilder.BeanBuilder<>(SerializeTestEntity.class)
                .setWithHeader(true)
                .build();
        Assert.assertEquals("date\n", writer.writeString(data));
    }

    @Test(expected = SerializeTypeMissMatchException.class)
    public void invalidTypeSerializeTest() throws Exception {
        Collection<SerializeInvalidType> data = new ArrayList<>();
        data.add(new SerializeInvalidType(""));
        CsvWriter<SerializeInvalidType> writer = new CsvWriterBuilder.BeanBuilder<>(SerializeInvalidType.class)
                .setWithHeader(true)
                .build();
        writer.writeString(data);
    }


}
