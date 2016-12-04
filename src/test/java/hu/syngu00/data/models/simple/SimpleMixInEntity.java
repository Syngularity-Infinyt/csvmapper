package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;

/**
 * Created by syngu on 2016-12-04.
 */
public class SimpleMixInEntity {
    @CsvColumn
    private int num;

    @CsvColumn
    private boolean value;

    @CsvColumn("name")
    private String text;

}
