package kr.meal.polyMealServer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Menu {

    private String date;
    private String breakfast;
    private String lunch;
    private String dinner;

    @Builder
    public Menu(String date, String breakfast, String lunch, String dinner) {
        this.date = date;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date='" + date + '\'' +
                ", breakfast='" + breakfast + '\'' +
                ", lunch='" + lunch + '\'' +
                ", dinner='" + dinner + '\'' +
                '}';
    }
}
