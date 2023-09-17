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
    public static LocalDate getThisWeekLastDay() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return lastDayOfWeek;
    }

    public static LocalDate getThisWeekFirstDay() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return firstDayOfWeek;
    }

    public static LocalDate getLastWeekLastDay() {
        LocalDate getThisWeekLastDay = getThisWeekLastDay();
        return getThisWeekLastDay.minusWeeks(1);
    }

    public static LocalDate getLastWeekFirstDay() {
        LocalDate thisWeekFirstDay = getThisWeekFirstDay();
        return thisWeekFirstDay.minusWeeks(1);
    }
}
