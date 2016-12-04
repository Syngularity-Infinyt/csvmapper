package hu.syngu00.data.models.serialize;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvSerialize;
import hu.syngu00.data.serialize.CsvDateTimeSerializer;

/**
 * Created by syngu on 2016-12-04.
 */
public class SerializeInvalidType {

    @CsvColumn
    @CsvSerialize(CsvDateTimeSerializer.class)
    private String string;

    public SerializeInvalidType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
