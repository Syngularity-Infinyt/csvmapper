package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;

/**
 * Created by syngu on 2016-12-04.
 */
@CsvOrder({"num", "value", "name"})
public abstract class SimpleAbstractMixin {
    @CsvColumn("num")
    public abstract int getNum();

    @CsvColumn("value")
    public abstract boolean isValue();

    @CsvColumn("name")
    public abstract String getText();

}
