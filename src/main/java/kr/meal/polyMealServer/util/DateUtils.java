package kr.meal.polyMealServer.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    /**
     * 일요일이 마지막으로 가정
     * @return 이번주의 마지막 날짜
     */
    public static LocalDate getThisWeekLastDay(String date) {
        LocalDate today = toLocalDate(date);
        LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return lastDayOfWeek;
    }

    public static LocalDate getThisWeekFirstDay(String date) {
        LocalDate today = toLocalDate(date);
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return firstDayOfWeek;
    }

    public static LocalDate getLastWeekLastDay(String date) {
        LocalDate getThisWeekLastDay = getThisWeekLastDay(date);
        return getThisWeekLastDay.minusWeeks(1);
    }

    public static LocalDate getLastWeekFirstDay(String date) {
        LocalDate thisWeekFirstDay = getThisWeekFirstDay(date);
        return thisWeekFirstDay.minusWeeks(1);
    }
}
