package kr.meal.polyMealServer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MenuSearchParam {
    private SchoolCode schoolCode;
    private String date;

    public LocalDate getNowDate() {
        return LocalDate.now();
    }
}
