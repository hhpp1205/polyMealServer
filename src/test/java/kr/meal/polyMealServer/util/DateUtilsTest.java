package kr.meal.polyMealServer.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


class DateUtilsTest {

    @DisplayName("이번주의 마지막 날짜를 반환한다.")
    @Test
    void getThisWeekLastDay() {
        //given
        String todayString = "2023-09-23";
        LocalDate thisWeekLastDay = LocalDate.of(2023, 9, 24);

        //when
        LocalDate result = DateUtils.getThisWeekLastDay(todayString);

        //then
        assertThat(result).isEqualTo(thisWeekLastDay);
    }

    @DisplayName("저번주의 마지막 날짜를 반환한다.")
    @Test
    void getLastWeekLastDay() {
        //given
        String todayString = "2023-09-23";
        LocalDate lastWeekLastDay = LocalDate.of(2023, 9, 17);

        //when
        LocalDate result = DateUtils.getLastWeekLastDay(todayString);

        //then
        assertThat(result).isEqualTo(lastWeekLastDay);
    }

    @DisplayName("이번주의 첫번째 날짜를 반환한다.")
    @Test
    void getThisWeekFirstDay() {
        //given
        String todayString = "2023-09-23";
        LocalDate thisWeekFirstDay = LocalDate.of(2023, 9, 18);

        //when
        LocalDate result = DateUtils.getThisWeekFirstDay(todayString);

        //then
        assertThat(result).isEqualTo(thisWeekFirstDay);
    }

    @DisplayName("저번주의 첫번째 날짜를 반환한다.")
    @Test
    void getLastWeekFirstDay() {
        //given
        String todayString = "2023-09-23";
        LocalDate lastWeekFirstDay = LocalDate.of(2023, 9, 11);

        //when
        LocalDate result = DateUtils.getLastWeekFirstDay(todayString);

        //then
        assertThat(result).isEqualTo(lastWeekFirstDay);
    }

}