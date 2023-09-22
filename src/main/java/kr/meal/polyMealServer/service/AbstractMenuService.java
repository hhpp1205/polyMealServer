package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.DateUtils;
import kr.meal.polyMealServer.util.MenuMap;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
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

    public Menu getMenu(SchoolCode schoolCode, String date) {
        if (menuMap.isExistMenu(schoolCode, date)) {
            log.info("manuMap get(schoolCode={}, data={})", schoolCode, date);
            return menuMap.get(schoolCode, date);
        }


        // 저번주 보다 과거 날짜의 메뉴 요청이라면 return EmptyMenu
        if(isPastDateFromLastWeek(date)) {
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        Document menuPageDocument = crawlingMenuService.crawlingMenuPagePost(schoolCode, date);
        Elements elementsOfMenu = extractMenuElements(menuPageDocument);
        if(elementsOfMenu == null || elementsOfMenu.size() == 0) {
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        makeMenuAndPutMenuMap(elementsOfMenu, schoolCode, date);

        Menu menu = menuMap.get(schoolCode, date);

        if(menu == null) {
            log.warn("be null after crawling, schoolCode={}, data={}", schoolCode, date);
            return Menu.ofEmptyMenu(schoolCode, date);
        }

        return menu;
    }

    private boolean isPastDateFromLastWeek(String date) {
        LocalDate requestDate = DateUtils.toLocalDate(date);
        LocalDate lastWeekFirstDay = DateUtils.getLastWeekFirstDay(date);

        return requestDate.isBefore(lastWeekFirstDay);
    }

    public Elements extractMenuElements(Document menuPageDocument) {
        Elements menuTags = menuPageDocument.select(".menu tbody tr");
        Elements menuElements = menuTags.select("td");
        return menuElements;
    }
}
