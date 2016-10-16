package hu.syngu00.data.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by syngu on 2016-10-15.
 */
public class Scheme {
    private Set<Column> columns = new HashSet<>();
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

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void addColumn(Column column, Column... more) {
        this.columns.add(column);
        this.columns.addAll(Arrays.asList(more));
    }

    public void addColumn(Set<Column> columns) {
        this.columns.addAll(columns);
    }

    public Set<Column> getColumns() {
        return columns;
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
