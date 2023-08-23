package com.gallery.manage.common.util;

import com.light.core.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class LocalDateUtils {

    public static Date validateBeginTime(Date date) {
        if (DateUtils.get(date, Calendar.HOUR_OF_DAY) > 11) {
            return getBeginOfDayUSA(date);
        } else {
            return getBeginOfDayUSA(DateUtils.getBeforeDay(date));
        }
    }

    public static Date validateEndTime(Date date) {
        if (DateUtils.get(date, Calendar.HOUR_OF_DAY) < 12) {
            return getEndOfDayUSA(date);
        } else {
            return getEndOfDayUSA(DateUtils.getAfterDay(date));
        }
    }

    public static Date getBeginOfDayUSA(Date date) {
        return DateUtils.parse(DateUtils.DATE_TIME_FORMAT, DateUtils.format(date, DateUtils.DATE_FORMAT) + " 12:00:00");
    }

    public static Date getEndOfDayUSA(Date date) {
        return DateUtils.parse(DateUtils.DATE_TIME_FORMAT, DateUtils.format(date, DateUtils.DATE_FORMAT) + " 11:59:59");
    }


}
