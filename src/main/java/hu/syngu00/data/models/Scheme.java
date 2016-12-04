package hu.syngu00.data.models;

import hu.syngu00.data.annotations.CsvOrder;
import hu.syngu00.data.exceptions.ColumnCreateException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by syngu on 2016-10-15.
 */
public class Scheme {
    private Collection<Column> columns = new ArrayList<>();
    private String separator = ",";
    private boolean withHeader = false;

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public boolean isWithHeader() {
        return withHeader;
    }

    public void setWithHeader(boolean withHeader) {
        this.withHeader = withHeader;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

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
        Column column_ = null;
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                column_ = column;
            }
        }
        return column_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scheme scheme = (Scheme) o;

        if (withHeader != scheme.withHeader) return false;
        if (!columns.equals(scheme.columns)) return false;
        return separator.equals(scheme.separator);
    }

    @Override
    public int hashCode() {
        int result = columns.hashCode();
        result = 31 * result + separator.hashCode();
        result = 31 * result + (withHeader ? 1 : 0);
        return result;
    }
}