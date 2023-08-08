package kr.meal.polyMealServer.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Menu {
    private String schoolName;
    private String date;
    private String dayOfTheWeek;
    private List<String> meal;

    @Builder
    public Menu(String schoolName, String date, String dayOfTheWeek, List<String> meal) {
        this.schoolName = schoolName;
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.meal = meal;
    }

    public static Menu ofEmptyMenu(SchoolCode schoolCode, String date) {
        return Menu.builder()
                .date(date)
                .schoolName(schoolCode.getSchoolName())
                .build();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "schoolName='" + schoolName + '\'' +
                ", date='" + date + '\'' +
                ", dayOfTheWeek='" + dayOfTheWeek + '\'' +
                ", meal=" + meal +
                '}';
    }
}
