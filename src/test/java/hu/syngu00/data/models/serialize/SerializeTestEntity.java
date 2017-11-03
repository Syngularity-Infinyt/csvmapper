package hu.syngu00.data.models.serialize;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvSerialize;
import hu.syngu00.data.serialize.CsvDateTimeSerializer;

import java.util.Date;

public class SerializeTestEntity {
    @CsvColumn
    @CsvSerialize(CsvDateTimeSerializer.class)
    private Date date;

    public SerializeTestEntity(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
