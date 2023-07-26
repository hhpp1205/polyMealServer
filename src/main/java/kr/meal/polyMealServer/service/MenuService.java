package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    private final String POLY_DEAJEON_URL = "https://www.kopo.ac.kr/daejeon/content.do?menu=5417";

    private final String[] DAY_OF_THE_WEEK =  {"월","화","수","목","금","토","일"};



    public Map<String, Menu> getMenu() throws IOException {
        Document doc = Jsoup.connect(POLY_DEAJEON_URL).get();
        Elements menuTags = doc.select(".menu tr");

        Map<String, Menu> dateManuMap = new HashMap<>();

        Elements td = menuTags.select("td");

        int dateIdx = 0;

        for (int i = 1; i < td.size(); i += 4) {
            Menu menu = Menu.builder()
                    .date(DAY_OF_THE_WEEK[dateIdx])
                    .breakfast(td.get(i).text())
                    .lunch(td.get(i + 1).text())
                    .dinner(td.get(i + 2).text())
                    .build();

            dateManuMap.put(DAY_OF_THE_WEEK[dateIdx++], menu);
        }


        List<String> thisWeekDateData = menuTags.select("script").stream()
                .map(dateTag -> dateTag.toString().substring(32, 42)).toList();

        System.out.println("dateManuMap = " + dateManuMap);
        System.out.println("thisWeekDateData = " + thisWeekDateData);

        return dateManuMap;
    }

    public Elements getDate() throws IOException {
        Document doc = Jsoup.connect(POLY_DEAJEON_URL).get();

        Elements thisWeekDateTagData = doc.select(".week_move .week script");

        return thisWeekDateTagData;
    }
}
