package com.hzf.study.utils;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author changzhu.wu
 * @date 2019/8/22 10:42
 */
public class DateHelper {

    public static Long getStartOfDay(Long ts) {
        return new DateTime(ts).withTimeAtStartOfDay().getMillis();
    }

    public static Long getEndOfDay(Long ts) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date(ts));
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);

        return calendarEnd.getTimeInMillis();
    }

    public static Long getNextDay(Long ts) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date(ts));
        calendarEnd.add(Calendar.DATE, 1);

        return calendarEnd.getTimeInMillis();
    }

    public static Long getAmountDay(Long ts, Integer amount) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date(ts));
        calendarEnd.add(Calendar.DATE, amount);

        return calendarEnd.getTimeInMillis();
    }

    public static Long getLastDay(Long ts) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date(ts));
        calendarEnd.add(Calendar.DATE, -1);

        return calendarEnd.getTimeInMillis();
    }

    public static Long getStartOfMonth(Long ts) {
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.setTime(new Date(ts));
        firstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstDayOfMonth.set(Calendar.MINUTE, 0);
        firstDayOfMonth.set(Calendar.SECOND, 0);
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

        firstDayOfMonth.set(Calendar.MILLISECOND, 0);
        return firstDayOfMonth.getTime().getTime();
    }

    public static Long getEndOfMonth(Long ts) {
        Calendar endOfMonth = Calendar.getInstance();
        endOfMonth.setTime(new Date(ts));

        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND,59);
        endOfMonth.set(Calendar.MILLISECOND, 999);

        return endOfMonth.getTimeInMillis();
    }

    public static Long getNextMonth(Long ts) {
        Long firstDay = getStartOfMonth(ts);

        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.setTime(new Date(firstDay));
        firstDayOfMonth.add(Calendar.MONTH, 1);

        return firstDayOfMonth.getTime().getTime();
    }

    public static Long getAmountMonth(Long ts, Integer amount) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date(ts));
        calendarEnd.add(Calendar.MONTH, amount);

        return calendarEnd.getTimeInMillis();
    }

    public static Long getLastMonth(Long ts) {
        Long firstDay = getStartOfMonth(ts);

        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.setTime(new Date(firstDay));
        firstDayOfMonth.add(Calendar.MONTH, -1);

        return firstDayOfMonth.getTime().getTime();
    }

    public static Long getNextWeek(Long ts) {

        Calendar mondayOfWeek = Calendar.getInstance();
        mondayOfWeek.setTime(new Date(ts));

        if(mondayOfWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            mondayOfWeek.add(Calendar.DATE, -1);
        }
        mondayOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mondayOfWeek.add(Calendar.DATE, 7);

        return mondayOfWeek.getTimeInMillis();
    }

    public static Long getLastWeek(Long ts) {

        Calendar mondayOfWeek = Calendar.getInstance();
        mondayOfWeek.setTime(new Date(ts));

        if(mondayOfWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            mondayOfWeek.add(Calendar.DATE, -1);
        }
        mondayOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mondayOfWeek.add(Calendar.DATE, -7);

        return mondayOfWeek.getTimeInMillis();
    }

    public static Long getStartOfWeek(Long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(ts));

        int d = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? -6 : 2 - cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis();
    }

    public static Long getEndOfWeek(Long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(ts));

        int d = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 0 : 8 - cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTimeInMillis();
    }

    public static List<Long> splitTimeSpan(Long beginTs, Long endTs, Integer mode) {

        if (mode == 0) {
            return splitTimeSpanInDayMode(beginTs, endTs);
        } else if (mode == 1) {
            return splitTimeSpanInWeekMode(beginTs, endTs);
        } else if (mode == 2){
            return splitTimeSpanInMonthMode(beginTs, endTs);
        }

        return new LinkedList<>();
    }

    private static List<Long> splitTimeSpanInDayMode(Long beginTs, Long endTs) {
        List<Long> periods = new LinkedList<>();

        Long startOfDay = getStartOfDay(beginTs);
        Long endOfDay = getEndOfDay(endTs);

        periods.add(startOfDay);

        Long nextDay = getNextDay(startOfDay);
        while (nextDay < endOfDay) {

            periods.add(nextDay);
            nextDay = getNextDay(nextDay);
        }
        periods.add(endOfDay);

        return periods;
    }

    private static List<Long> splitTimeSpanInWeekMode(Long beginTs, Long endTs) {
        List<Long> periods = new LinkedList<>();

        Long startOfDay = getStartOfDay(beginTs);
        Long endOfDay = getEndOfDay(endTs);

        periods.add(startOfDay);

        Long nextWeek = getNextWeek(startOfDay);
        while (nextWeek < endOfDay) {

            periods.add(nextWeek);
            nextWeek = getNextWeek(nextWeek);
        }
        periods.add(endOfDay);

        return periods;
    }

    private static List<Long> splitTimeSpanInMonthMode(Long beginTs, Long endTs) {
        List<Long> periods = new LinkedList<>();

        Long startOfDay = getStartOfDay(beginTs);
        Long endOfDay = getEndOfDay(endTs);

        periods.add(startOfDay);

        Long nextMonth = getNextMonth(startOfDay);
        while (nextMonth < endOfDay) {

            periods.add(nextMonth);
            nextMonth = getNextMonth(nextMonth);
        }
        periods.add(endOfDay);

        return periods;
    }
}
