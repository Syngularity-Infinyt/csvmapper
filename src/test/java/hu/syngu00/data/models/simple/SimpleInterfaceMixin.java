package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;

/**
 * Created by syngu on 2016-12-04.
 */
@CsvOrder({"num", "value", "name"})
public interface SimpleInterfaceMixin {
    @CsvColumn("num")
    int getNum();

    @CsvColumn("value")
    boolean isValue();

    @CsvColumn("name")
    String getText();

}
