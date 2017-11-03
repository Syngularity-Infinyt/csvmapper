package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;

@CsvOrder({"num", "value", "name"})
public abstract class SimpleAbstractMixin {

    @CsvColumn(value = "num", encaper = "'", encapsulate = true)
    public abstract int getNum();

    @CsvColumn("value")
    public abstract boolean isValue();

    @CsvColumn("name")
    public abstract String getText();

}
