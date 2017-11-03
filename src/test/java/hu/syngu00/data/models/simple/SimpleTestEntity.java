package hu.syngu00.data.models.simple;

import hu.syngu00.data.annotations.CsvColumn;


public class SimpleTestEntity {

    @CsvColumn(encaper = "'", encapsulate = true)
    private int num;

    @CsvColumn
    private boolean value;

    @CsvColumn("name")
    private String text;

    public SimpleTestEntity(int num, boolean value, String text) {
        this.num = num;
        this.value = value;
        this.text = text;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
