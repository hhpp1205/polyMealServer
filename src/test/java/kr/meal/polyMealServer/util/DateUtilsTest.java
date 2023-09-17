package kr.meal.polyMealServer.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {


    @DisplayName("")
    @Test
    void test() {
        LocalDate thisWeekFirstDay = DateUtils.getThisWeekFirstDay();
        LocalDate thisWeekLastDay = DateUtils.getThisWeekLastDay();
        LocalDate lastWeekFirstDay = DateUtils.getLastWeekFirstDay();
        LocalDate lastWeekLastDay = DateUtils.getLastWeekLastDay();

        System.out.println("thisWeekFirstDay = " + thisWeekFirstDay);
        System.out.println("thisWeekLastDay = " + thisWeekLastDay);
        System.out.println("lastWeekFirstDay = " + lastWeekFirstDay);
        System.out.println("lastWeekLastDay = " + lastWeekLastDay);
    }
}