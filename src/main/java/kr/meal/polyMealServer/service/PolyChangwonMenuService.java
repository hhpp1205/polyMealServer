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
@Service(value = "PolyChangwonMenuService")
public class PolyChangwonMenuService extends AbstractMenuService {

    @Override
    Menu getMenu(SchoolCode schoolCode, String date) {
        return null;
    }

    @Override
    protected void crawlingMenu(SchoolCode schoolCode, String date) {
        // TODO: 2023/08/09 창원 식당은 제1식당과 제2식당 메뉴가 다름 
        try {
            Document doc = Jsoup.connect(schoolCode.getUrl()).get();

            Elements menuTags = doc.select(".menu tr");

            Map<String, Menu> dateManuMap = new HashMap<>();

            Elements td = menuTags.select("td");

            List<String> thisWeekDateData = menuTags.select("script").stream()
                    .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

            int dateIdx = 0;

            for (int i = 1; i < td.size() / 2; i += 4) {
                Menu menu = Menu.builder()
                        .schoolCode(schoolCode)
                        .date(thisWeekDateData.get(dateIdx))
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
            log.error("{}", e);
        }
    }
}
