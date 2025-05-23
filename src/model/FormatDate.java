package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

    public static String getToday(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
