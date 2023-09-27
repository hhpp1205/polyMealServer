package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.MenuSearchParam;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.DateUtils;
import kr.meal.polyMealServer.util.MenuMap;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Slf4j
@Service
public abstract class AbstractMenuService {

    /**
     * SchoolCode 학교
     * String 날짜  ex) 2023-08-02
     */
    protected final MenuMap menuMap = MenuMap.getMenuMap();
    protected CrawlingMenuService crawlingMenuService = new CrawlingMenuService();

    public abstract void makeMenuAndPutMenuMap(Elements elementsOfMenu, SchoolCode schoolCode, String date);

    public Menu getMenu(MenuSearchParam request) {
        if (menuMap.isExistMenu(request.getSchoolCode(), request.getDate())) {
            log.info("manuMap get(schoolCode={}, data={})", request.getSchoolCode(), request.getDate());
            return menuMap.get(request.getSchoolCode(), request.getDate());
        }


        // 저번주 보다 과거 날짜의 메뉴 요청이라면 return EmptyMenu
        if(isPastDateFromLastWeek(request.getDate(), request.getNowDate())) {
            return Menu.ofEmptyMenu(request.getSchoolCode(), request.getDate());
        }

        Elements elementsOfMenu = crawlingMenuService.getMenuElements(request.getSchoolCode(), request.getDate());
        if(elementsOfMenu == null || elementsOfMenu.size() == 0) {
            return Menu.ofEmptyMenu(request.getSchoolCode(), request.getDate());
        }

        makeMenuAndPutMenuMap(elementsOfMenu, request.getSchoolCode(), request.getDate());

        Menu menu = menuMap.get(request.getSchoolCode(), request.getDate());

        if(menu == null) {
            log.warn("be null after crawling, schoolCode={}, data={}", request.getSchoolCode(), request.getDate());
            return Menu.ofEmptyMenu(request.getSchoolCode(), request.getDate());
        }

        return menu;
    }

    private boolean isPastDateFromLastWeek(String date, LocalDate nowDate) {
        LocalDate requestDate = DateUtils.toLocalDate(date);
        LocalDate lastWeekFirstDay = DateUtils.getLastWeekFirstDay(nowDate.toString());

        return requestDate.isBefore(lastWeekFirstDay);
    }

}
