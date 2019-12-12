package common;

import java.util.Calendar;

public class DateTimeUtil {

    public static String getDateAndTimeString() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE), second = cal.get(Calendar.SECOND);
        int year = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH) + 1, day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%d%02d%02d_%2d-%02d-%02d", year, month, day, hour, minute, second);
    }

    public static int getDateNumber() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH) + 1, day = cal.get(Calendar.DAY_OF_MONTH);
        return year * 10000 + month * 100 + day;
    }

    public static String getTimeStringHM() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE);
        return String.format("[%02d:%02d]", hour, minute);
    }

    public static String getTimeStringHMS() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE), second = cal.get(Calendar.SECOND);
        return String.format("[%02d:%02d:%02d]", hour, minute, second);
    }

}
