package hu.syngu00.data.mappers;

import hu.syngu00.data.models.Scheme;

@FunctionalInterface
public interface CsvMapper {
    Scheme getSchema();
}
