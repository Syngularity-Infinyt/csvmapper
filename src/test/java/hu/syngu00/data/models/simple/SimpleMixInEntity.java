package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;


public class SimpleMixInEntity {
    @CsvColumn(encaper = "'", encapsulate = true)
    private int num;

    @CsvColumn
    private boolean value;

    @CsvColumn("name")
    private String text;

}
