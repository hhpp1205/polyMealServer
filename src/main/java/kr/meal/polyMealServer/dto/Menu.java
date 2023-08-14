package kr.meal.polyMealServer.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Menu {
    private String schoolCode;
    private String schoolName;
    private String date;
    private List<String> meal;

    @Builder
    public Menu(SchoolCode schoolCode, String date, List<String> meal) {
        this(schoolCode.getSchoolCode(), schoolCode.getSchoolName(), date, meal);
    }

    public Menu(String schoolCode, String schoolName, String date, List<String> meal) {
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
        this.date = date;
        this.meal = meal;
    }

    public static Menu ofEmptyMenu(SchoolCode schoolCode, String date) {
        return Menu.builder()
                .date(date)
                .schoolCode(schoolCode)
                .meal(List.of("", "", ""))
                .build();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "schoolName='" + schoolName + '\'' +
                ", date='" + date + '\'' +
                ", meal=" + meal +
                '}';
    }
}
