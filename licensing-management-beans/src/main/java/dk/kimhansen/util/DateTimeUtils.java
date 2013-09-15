package dk.kimhansen.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateTimeUtils {

    public static Date getTodayAtMidnight() {
        return DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
    }

    public static Date getTodayOneMillisecondBeforeMidnight() {
        Date date = getTodayAtMidnight();
        date = DateUtils.addDays(date, 1);
        return DateUtils.addMilliseconds(date, -1);
    }
}
