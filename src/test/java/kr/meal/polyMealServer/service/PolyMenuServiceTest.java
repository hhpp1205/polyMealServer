package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PolyMenuServiceTest {

    @Autowired
    private PolyMenuService polyMenuService;

    @Test
    void getMenu() throws IOException {
        Menu menu = polyMenuService.getMenu(SchoolCode.POLY_JUNGSU, "2023-07-31");
        System.out.println("result = " + menu);
    }

    @DisplayName("")
    @Test
    void test() {
        LocalDate requestDate = LocalDate.of(2023, 8, 13);
        LocalDate thisWeekLastDay = DateUtils.getThisWeekLastDay();

        System.out.println(requestDate.isAfter(thisWeekLastDay));
        System.out.println("requestDate = " + requestDate);
        System.out.println("thisWeekLastDay = " + thisWeekLastDay);
    }

    @DisplayName("")
    @Test
    void test2() {
        LocalDate requestDate = LocalDate.of(2023, 8, 6);
        LocalDate thisWeekFirstDay = DateUtils.getThisWeekFirstDay();

        System.out.println(requestDate.isBefore(thisWeekFirstDay));
        System.out.println("requestDate = " + requestDate);
        System.out.println("thisWeekFirstDay = " + thisWeekFirstDay);
    }

    @DisplayName("")
    @Test
    void test3() {
        System.out.println(LocalDate.now());
    }

    @DisplayName("")
    @Test
    void 폴리텍창원크롤링() throws IOException {
        final String[] DAY_OF_THE_WEEK =  {"월","화","수","목","금","토","일"};

        Document doc = Jsoup.connect(SchoolCode.POLY_CHANGWON.getUrl()).get();

        Elements menuTags = doc.select(".menu tr");

        Map<String, Menu> dateManuMap = new HashMap<>();

        Elements td = menuTags.select("td");

        List<String> thisWeekDateData = menuTags.select("script").stream()
                .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

        int dateIdx = 0;

        System.out.println("td.size() =" + td.size());
        for (int i = 1; i < td.size() / 2; i += 4) {
            Menu menu = Menu.builder()
                    .schoolCode(SchoolCode.POLY_CHANGWON)
                    .date(thisWeekDateData.get(dateIdx))
                    .meal(
                            List.of(
                                    td.get(i).text(),
                                    td.get(i + 1).text(),
                                    td.get(i + 2).text()
                            )
                    )
                    .build();
            System.out.println("menu = " + menu);
        }
    }

    @DisplayName("")
    @Test
    void test7() {
        polyMenuService.getMenu(SchoolCode.POLY_ROBOT, "2023-08-29");
    }


}