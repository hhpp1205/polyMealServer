package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service(value = "polyChangwonMenuService")
public class PolyChangwonMenuService extends AbstractMenuService {

    @Override
    public void crawlingMenuAndPutMenuMap(SchoolCode schoolCode, String date) {
        // TODO: 2023/08/09 창원 식당은 제1식당과 제2식당 메뉴가 다름
        Elements elementsOfMenu = getElementsOfMenu(schoolCode);
        if(elementsOfMenu == null || elementsOfMenu.size() == 0) {
            return;
        }

        Map<String, Menu> dateManuMap = new HashMap<>();

        List<String> thisWeekDateData = elementsOfMenu.select("script").stream()
                .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

        int dateIdx = 0;

        for (int i = 1; i < elementsOfMenu.size() / 2; i += 4) {
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
        try {
            Document doc = Jsoup.connect(schoolCode.getUrl()).get();
            Elements menuTags = doc.select(".menu tbody tr");
            Elements tdOfMenu = menuTags.select("td");
            return tdOfMenu;
        } catch (Exception e) {
            log.error("crawlingExeption, schoolCode={}", schoolCode);
            log.error("", e);
            return null;
        }
    }
}
