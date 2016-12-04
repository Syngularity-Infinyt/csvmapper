package hu.syngu00.data.serialize;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by syngu on 2016-10-15.
 */
public class CsvDateTimeSerializer implements CsvSerializer<Date> {
    @Override
    public String serialize(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
        return format.format(date);
    }
}
