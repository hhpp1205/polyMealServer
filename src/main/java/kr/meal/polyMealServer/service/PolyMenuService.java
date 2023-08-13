package kr.meal.polyMealServer.service;

import jakarta.annotation.PostConstruct;
import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service(value = "PolyMenuService")
public class PolyMenuService extends AbstractMenuService {

    @Autowired
    PolyChangwonMenuService polyChangwonMenuService;

    @PostConstruct
    public void init() {
        for(SchoolCode schoolCode : SchoolCode.values()) {
            menuMap.put(schoolCode, new HashMap<>());
            LocalDate now = LocalDate.now();

            if(schoolCode == SchoolCode.POLY_CHANGWON) {
                polyChangwonMenuService.crawlingMenu(schoolCode, now.toString());
                continue;
            }

            crawlingMenu(schoolCode, now.toString());
        }
    }


    @Override
    public Menu getMenu(SchoolCode schoolCode, String date) {
        if(menuMap != null && menuMap.get(schoolCode).get(date) != null) {
            //맵에 데이터가 있다면 찾아서 return
            log.info("manuMap get({})", date);
            return menuMap.get(schoolCode).get(date);
        }

        //요청 날짜가 이번주가 아니라면 EmptyMenu return
        if(!isThisWeekOrTodaySunday(date)) {
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        crawlingMenu(schoolCode, date);

        Menu menu = menuMap.get(schoolCode).get(date);

        // TODO: 2023/08/10 크롤링 할 페이지에 메뉴 테이블이 존재하지 않는다면 계속 계속 크롤링 하는 현상 수정
        // 크롤링 해도 null이라면 기본 menu 객체 반환
        if(menu == null) {
            log.warn("be null after crawling, schoolCode={}, data={}", schoolCode, date);
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        return menu;
    }

    private boolean isThisWeekOrTodaySunday(String date) {
        LocalDate requestDate = DateUtils.toLocalDate(date);

        //오늘이 일요일인가?
        DayOfWeek dayOfWeek = requestDate.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }

        LocalDate thisWeekLastDay = DateUtils.getThisWeekLastDay();
        LocalDate thisWeekFirstDay = DateUtils.getThisWeekFirstDay();

        // 다음주인가?
        if(requestDate.isAfter(thisWeekLastDay)) {
            return false;
        }
        //저번주인가?
        if(requestDate.isBefore(thisWeekFirstDay)) {
            return false;
        }

        return true;
    }

    @Override
    protected void crawlingMenu(SchoolCode schoolCode, String date) {
        try {
            Document doc = Jsoup.connect(schoolCode.getUrl()).get();

            Elements menuTags = doc.select(".menu tr");

            Map<String, Menu> dateManuMap = new HashMap<>();

            Elements td = menuTags.select("td");

            List<String> thisWeekDateData = menuTags.select("script").stream()
                    .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

            int dateIdx = 0;

            for (int i = 1; i < td.size(); i += 4) {
                Menu menu = Menu.builder()
                        .schoolName(schoolCode.getSchoolName())
                        .date(thisWeekDateData.get(dateIdx))
                        .dayOfTheWeek(DAY_OF_THE_WEEK[dateIdx])
                        .meal(
                                List.of(
                                        td.get(i).text(),
                                        td.get(i + 1).text(),
                                        td.get(i + 2).text()
                                )
                        )
                        .build();

                dateManuMap.put(thisWeekDateData.get(dateIdx++), menu);
                menuMap.put(schoolCode, dateManuMap);
            }
                log.warn("call crawlingMenu(), schoolCode={}, date={}", schoolCode, date);
        } catch (Exception e) {
            log.error("crawlingExeption, schoolCode={}, date={}", schoolCode, date);
            log.error("", e);
        }
    }

}
