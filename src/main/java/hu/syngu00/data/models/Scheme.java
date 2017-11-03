package hu.syngu00.data.models;

import hu.syngu00.data.annotations.CsvOrder;
import hu.syngu00.data.exceptions.ColumnCreateException;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class Scheme {
    private Collection<Column> columns = new ArrayList<>();
    private String separator = ",";
    private boolean withHeader = false;
    private Charset charset = Charset.forName("UTF-8");

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void addColumn(Collection<Column> columns) {
        this.columns.addAll(columns);
    }

    public void createOrder(CsvOrder order) {
        Collection<Column> newColumns = new ArrayList<>();
        for (String name : order.value()) {
            Column column = getColumnByName(name);
            if (column != null) {
                this.columns.remove(column);
                newColumns.add(column);
            } else {
                throw new ColumnCreateException("you are fucked");
            }
        }
        newColumns.addAll(this.columns);
        this.columns = newColumns;
    }

    private Column getColumnByName(String name) {
        Column columnTemp = null;
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                columnTemp = column;
            }
        }
        return columnTemp;
    }
}
