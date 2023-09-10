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

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service(value = "PolyMenuService")
public class PolyMenuService extends AbstractMenuService {

    @Autowired
    PolyChangwonMenuService polyChangwonMenuService;

    @PostConstruct
    public void menuMapInitialValueSettings() throws InterruptedException {
        int schoolCodeLength = SchoolCode.values().length - 1;
        ExecutorService executorService = Executors.newFixedThreadPool(schoolCodeLength);
        CountDownLatch countDownLatch = new CountDownLatch(schoolCodeLength);

        for(SchoolCode schoolCode : SchoolCode.values()) {
            executorService.submit(createRunnableOfCrawlingMenuAndCountDown(countDownLatch, schoolCode));
        }

        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }

        countDownLatch.await();
    }

    private Runnable createRunnableOfCrawlingMenuAndCountDown(CountDownLatch countDownLatch, SchoolCode schoolCode) {
        return () -> {
            menuMap.put(schoolCode, new HashMap<>());

            LocalDate now = LocalDate.now();

            if (schoolCode == SchoolCode.POLY_CHANGWON) {
                polyChangwonMenuService.crawlingMenuAndPutMenuMap(schoolCode, now.toString());
                return;
            }

            crawlingMenuAndPutMenuMap(schoolCode, now.toString());
            countDownLatch.countDown();

        };
    }

    @Override
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
    }

    private boolean isExistMenu(SchoolCode schoolCode, String date) {
        return menuMap != null && menuMap.get(schoolCode).get(date) != null;
    }

    private boolean isThisWeekOrTodaySunday(String date) {
        LocalDate requestDate = DateUtils.toLocalDate(date);

        LocalDate thisWeekLastDay = DateUtils.getThisWeekLastDay();
        LocalDate thisWeekFirstDay = DateUtils.getThisWeekFirstDay();

        boolean isNotThisWeek = requestDate.isAfter(thisWeekLastDay) || requestDate.isBefore(thisWeekFirstDay);

        // 이번주 일요일인가?
        if(thisWeekLastDay.toString().equals(date)) {
            return true;
        }
        // 이번주가 아닌가?
        if(isNotThisWeek) {
            return false;
        }

        return true;
    }

    @Override
    protected void crawlingMenuAndPutMenuMap(SchoolCode schoolCode, String date) {
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
