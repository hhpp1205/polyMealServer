package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.MenuSearchParam;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.MenuMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PolyMenuServiceTest {

    @Autowired
    private PolyMenuService polyMenuService;
    private MenuMap menuMap = MenuMap.getMenuMap();


    @DisplayName("학교코드와 날짜를 가지고 메뉴를 가져온다.")
    @Test
    void getMenu() {
        //given
        String 날짜 = "2023-09-23";
        SchoolCode 학교코드 = SchoolCode.POLY_DEAJEON;
        List<String> 식사메뉴 = List.of("아침", "점심", "저녁");
        Menu 메뉴 = new Menu(학교코드, 날짜, 식사메뉴);
        menuMap.putMenu(학교코드, 날짜, 메뉴);

        MenuSearchParam menuSearchParam = new MenuSearchParam();
        menuSearchParam.setDate(날짜);
        menuSearchParam.setSchoolCode(학교코드);

        //when
        Menu result = polyMenuService.getMenu(menuSearchParam);

        //then
        assertThat(result).isEqualTo(메뉴);
    }

    @DisplayName("저번주보다 과거의 요청이라면 빈메뉴를 반환한다.")
    @Test
    void test() {
        //given
        String 날짜 = "2021-01-23";
        SchoolCode 학교코드 = SchoolCode.POLY_DEAJEON;

        MenuSearchParam menuSearchParam = new MenuSearchParam();
        menuSearchParam.setDate(날짜);
        menuSearchParam.setSchoolCode(학교코드);

        //when
        Menu menu = polyMenuService.getMenu(menuSearchParam);

        //then
        assertThat(menu).isEqualTo(Menu.ofEmptyMenu(학교코드, 날짜));
    }

}