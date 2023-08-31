package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public abstract class AbstractMenuService {

    protected final String[] DAY_OF_THE_WEEK =  {"월","화","수","목","금","토","일"};

    /**
     * SchoolCode 학교
     * String 날짜  ex) 2023-08-02
     */
    public static Map<SchoolCode, Map<String, Menu>> menuMap = new ConcurrentHashMap<>();

    public abstract void crawlingMenuAndPutMenuMap(SchoolCode schoolCode, String date);

    public Menu getMenu(SchoolCode schoolCode, String date) {
        if(isExistMenu(schoolCode, date)) {
            log.info("manuMap get(schoolCode={}, data={})", schoolCode, date);
            return menuMap.get(schoolCode).get(date);
        }

        //요청 날짜가 이번주가 아니라면 EmptyMenu return
        if(!isThisWeekOrTodaySunday(date)) {
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        crawlingMenuAndPutMenuMap(schoolCode, date);

        Menu menu = menuMap.get(schoolCode).get(date);

        if(menu == null) {
            log.warn("be null after crawling, schoolCode={}, data={}", schoolCode, date);
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        return menu;
    };

    private boolean isExistMenu(SchoolCode schoolCode, String date) {
        return menuMap != null && menuMap.get(schoolCode).get(date) != null;
    }

    private boolean isThisWeekOrTodaySunday(String date) {
        LocalDate requestDate = DateUtils.toLocalDate(date);

        LocalDate thisWeekLastDay = DateUtils.getThisWeekLastDay();
        LocalDate thisWeekFirstDay = DateUtils.getThisWeekFirstDay();

        boolean isNotThisWeek = requestDate.isAfter(thisWeekLastDay) || requestDate.isBefore(thisWeekFirstDay);

        // 이번주 일요일인가?
        if(thisWeekLastDay.toString() == date) {
            return true;
        }
        // 이번주가 아닌가?
        if(isNotThisWeek) {
            return false;
        }

        return true;
    }
}
