package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service(value = "polyMenuService")
@RequiredArgsConstructor
public class PolyMenuService extends AbstractMenuService {

    private final CrawlingMenuService crawlingMenuService;

    @Override
    public void crawlingMenuAndPutMenuMap(SchoolCode schoolCode, String date) {
        Elements elementsOfMenu = getElementsOfMenu(schoolCode);
        if(elementsOfMenu == null || elementsOfMenu.size() == 0) {
            return;
        }

        Map<String, Menu> dateManuMap = new HashMap<>();

        List<String> thisWeekDateData = elementsOfMenu.select("script").stream()
                .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

        int dateIdx = 0;

        for (int i = 1; i < elementsOfMenu.size(); i += 4) {
            Menu menu = Menu.builder()
                    .schoolCode(schoolCode)
                    .date(thisWeekDateData.get(dateIdx))
                    .meal(
                            List.of(
                                    elementsOfMenu.get(i).text(),
                                    elementsOfMenu.get(i + 1).text(),
                                    elementsOfMenu.get(i + 2).text()
                            )
                    )
                    .build();

            dateManuMap.put(thisWeekDateData.get(dateIdx++), menu);
            menuMap.put(schoolCode, dateManuMap);
        }
        log.warn("call crawlingMenu(), schoolCode={}, date={}", schoolCode, date);
    }

    private Elements getElementsOfMenu(SchoolCode schoolCode) {
        Document document = crawlingMenuService.crawlingMenuPageGet(schoolCode);
        Elements menuTags = document.select(".menu tbody tr");
        Elements tdOfMenu = menuTags.select("td");
        return tdOfMenu;
    }

}
