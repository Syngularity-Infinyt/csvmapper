package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;
import hu.syngu00.data.annotations.CsvOrder;


@CsvOrder({"num", "value", "name"})
public interface SimpleInterfaceMixin {


    @CsvColumn(value = "num", encaper = "'", encapsulate = true)
    int getNum();

    @CsvColumn("value")
    boolean isValue();

    @CsvColumn("name")
    String getText();

}
