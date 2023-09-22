package kr.meal.polyMealServer.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private void isValidDateFormat(String date) {
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        Pattern regex = Pattern.compile(pattern);

        Matcher matcher = regex.matcher(date);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식 입니다.");
        }
    }

    public Menu(String schoolCode, String schoolName, String date, List<String> meal) {
        isValidDateFormat(date);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (!Objects.equals(schoolCode, menu.schoolCode)) return false;
        if (!Objects.equals(schoolName, menu.schoolName)) return false;
        if (!Objects.equals(date, menu.date)) return false;
        return Objects.equals(meal, menu.meal);
    }

    @Override
    public int hashCode() {
        int result = schoolCode != null ? schoolCode.hashCode() : 0;
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        return result;
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
