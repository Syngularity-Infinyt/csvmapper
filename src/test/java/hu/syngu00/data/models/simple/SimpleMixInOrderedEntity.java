package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;


@CsvOrder({"name", "value", "num"})
public class SimpleMixInOrderedEntity {
    private int num;

    @CsvColumn
    private boolean value;

    @CsvColumn("name")
    private String text;

    @CsvColumn("num")
    public int getNum() {
        return num;
    }
}
