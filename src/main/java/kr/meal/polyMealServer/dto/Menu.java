package kr.meal.polyMealServer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Menu {
    private String schoolName;
    private String date;
    private String dayOfTheWeek;
    private String breakfast;
    private String lunch;
    private String dinner;

    @Builder
    public Menu(String schoolName, String date, String dayOfTheWeek, String breakfast, String lunch, String dinner) {
        this.schoolName = schoolName;
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "schoolCode='" + schoolName + '\'' +
                ", date='" + date + '\'' +
                ", dayOfTheWeek='" + dayOfTheWeek + '\'' +
                ", breakfast='" + breakfast + '\'' +
                ", lunch='" + lunch + '\'' +
                ", dinner='" + dinner + '\'' +
                '}';
    }
}
