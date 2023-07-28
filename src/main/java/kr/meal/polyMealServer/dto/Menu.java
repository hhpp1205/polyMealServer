package kr.meal.polyMealServer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Menu {
    private String schoolCode;
    private String date;
    private String dayOfTheWeek;
    private String breakfast;
    private String lunch;
    private String dinner;

    @Builder
    public Menu(String schoolCode, String date, String dayOfTheWeek, String breakfast, String lunch, String dinner) {
        this.schoolCode = schoolCode;
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "schoolCode='" + schoolCode + '\'' +
                ", date='" + date + '\'' +
                ", dayOfTheWeek='" + dayOfTheWeek + '\'' +
                ", breakfast='" + breakfast + '\'' +
                ", lunch='" + lunch + '\'' +
                ", dinner='" + dinner + '\'' +
                '}';
    }
}
