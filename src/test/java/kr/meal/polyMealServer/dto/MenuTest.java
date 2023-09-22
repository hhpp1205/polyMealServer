package kr.meal.polyMealServer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MenuTest {

    @DisplayName("올바르지 않은 형식의 날짜 포멧을 넘기면 오류가 발생한다.")
    @Test
    void test() {
        //given
        String 날짜1 = "2013.01.20";
        String 날짜2 = "2013:01:20";

        //when //then
        assertThatThrownBy(() -> Menu.ofEmptyMenu(SchoolCode.POLY_DEAJEON, 날짜1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Menu.ofEmptyMenu(SchoolCode.POLY_DEAJEON, 날짜2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 메뉴를 생성한다.")
    @Test
    void emptyMenu() {
        //given
        SchoolCode 학교코드 = SchoolCode.POLY_DEAJEON;
        String 날짜 = "2013-12-02";

        Menu 빈메뉴 = Menu.builder()
                .date(날짜)
                .schoolCode(학교코드)
                .meal(List.of("", "", ""))
                .build();
        //when
        Menu 메뉴 = Menu.ofEmptyMenu(학교코드, 날짜);

        //then
        assertThat(메뉴).isEqualTo(빈메뉴);
    }

}