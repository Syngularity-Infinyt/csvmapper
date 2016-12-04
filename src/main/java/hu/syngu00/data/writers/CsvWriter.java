package hu.syngu00.data.writers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Created by syngu on 2016-10-16.
 */
public interface CsvWriter<T> {

    byte[] writeBytes(Collection<T> ts);

    String writeString(Collection<T> ts);

    void writeToFile(Collection<T> ts, File file) throws IOException;

    void writeToFile(Collection<T> ts, Path path) throws IOException;
}
