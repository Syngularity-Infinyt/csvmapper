package hu.syngu00.data.mappers;

import hu.syngu00.data.models.Scheme;

/**
 * Created by syngu on 2016-10-16.
 */
public interface CsvMapper<SOURCE> {
    Scheme getSchema();
}
