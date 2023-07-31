package kr.meal.polyMealServer.dto;

import lombok.Getter;

@Getter
public enum SchoolCode {
    POLY_DEAJEON("001", "한국폴리텍대학 대전캠퍼스", "https://www.kopo.ac.kr/daejeon/content.do?menu=5417"),
    PORY_JUNGSU("002", "한국폴리텍대학 서울정수캠퍼스", "https://www.kopo.ac.kr/jungsu/content.do?menu=247");

    private String schoolCode;
    private String schoolName;
    private String url;

    SchoolCode(String schoolCode, String schoolName, String url) {
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
        this.url = url;
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
