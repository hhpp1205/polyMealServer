package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service(value = "polyChangwonMenuService")
public class PolyChangwonMenuService extends AbstractMenuService {

    @Override
    public void makeMenuAndPutMenuMap(Elements elementsOfMenu, SchoolCode schoolCode, String date) {
        // TODO: 2023/08/09 창원 식당은 제1식당과 제2식당 메뉴가 다름
        if(elementsOfMenu == null || elementsOfMenu.size() == 0) {
            return;
        }

        List<String> weekDateString = elementsOfMenu.select("script").stream()
                .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

        int dateIdx = 0;

        for (int i = 1; i < elementsOfMenu.size() / 2; i += 4) {
            Menu menu = Menu.builder()
                    .schoolCode(schoolCode)
                    .date(weekDateString.get(dateIdx))
                    .meal(
                            List.of(
                                    elementsOfMenu.get(i).text(),
                                    elementsOfMenu.get(i + 1).text(),
                                    elementsOfMenu.get(i + 2).text()
                            )
                    )
                    .build();

            menuMap.putMenu(schoolCode, weekDateString.get(dateIdx++), menu);
        }
        log.warn("call crawlingMenu(), schoolCode={}, date={}", schoolCode, date);
    }
}
