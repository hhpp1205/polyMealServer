package kr.meal.polyMealServer.util;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Slf4j
class MenuMapTest {
    private MenuMap menuMap = MenuMap.getMenuMap();

    @BeforeEach
    void setUp() {
        menuMap.clearMepTestOnlyCode();
    }

    @DisplayName("메뉴맵에서 메뉴를 가져올 수 있다.")
    @Test
    void getMenu() {
        //given
        String 날짜 = "2023-09-23";
        SchoolCode 학교코드 = SchoolCode.POLY_DEAJEON;
        List<String> 식사메뉴 = List.of("아침", "점심", "저녁");
        Menu 메뉴 = new Menu(학교코드, 날짜, 식사메뉴);
        menuMap.putSchoolEmptyMap(학교코드);

        //when
        menuMap.putMenu(학교코드, 날짜, 메뉴);

        //then
        assertThat(menuMap.get(학교코드, 날짜)).isEqualTo(메뉴);
    }

    @DisplayName("메뉴맵에 학교코드가 없다면 빈 문자열을 반환한다.")
    @Test
    void getMenuWithoutSchoolCode() {
        //given
        String 날짜 = "2023-09-23";
        SchoolCode 학교코드 = SchoolCode.POLY_DEAJEON;

        //when
        Menu menu = menuMap.get(학교코드, 날짜);

        //then
        assertThat(menu).isEqualTo(Menu.ofEmptyMenu(학교코드, 날짜));
    }

    @DisplayName("메뉴맵에 메뉴가 없다면 빈메뉴를 반환한다.")
    @Test
    void getMenuWithoutDate() {
        //given
        String 날짜 = "2023-09-23";
        SchoolCode 학교코드 = SchoolCode.POLY_DEAJEON;

        //when
        menuMap.putSchoolEmptyMap(학교코드);
        Menu menu = menuMap.get(학교코드, 날짜);

        //then
        assertThat(menu).isEqualTo(Menu.ofEmptyMenu(학교코드, 날짜));
    }



}