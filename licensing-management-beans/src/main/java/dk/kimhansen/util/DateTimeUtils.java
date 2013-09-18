package dk.kimhansen.util;

import java.text.SimpleDateFormat;
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

	public static Date parseIsoTimestamp(String timestamp) {
		try {
			SimpleDateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			String date = timestamp.replaceAll("\\+0([0-9]){1}\\:00", "+0$100");
			return iso8601DateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String toIsoTimestamp(Date timestamp) {
		try {
			SimpleDateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			return iso8601DateFormat.format(timestamp);
		} catch (Exception e) {
			return null;
		}
	}
}
