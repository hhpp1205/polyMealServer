package kr.meal.polyMealServer.dto;

import lombok.Getter;

@Getter
public enum SchoolCode {
    POLY_DEAJEON("001");

    private String schoolCode;

    SchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public static SchoolCode of(String schoolCode) {
        if(schoolCode == null) {
            throw new IllegalArgumentException("일치하는 학교코드가 없습니다.");
        }

        for(SchoolCode sc : SchoolCode.values()) {
            if(sc.schoolCode.equals(schoolCode)) {
                return sc;
            }
        }

        throw new IllegalArgumentException("일치하는 학교코드가 없습니다.");
    }
}
