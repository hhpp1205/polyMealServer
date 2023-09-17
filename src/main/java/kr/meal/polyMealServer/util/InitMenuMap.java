package kr.meal.polyMealServer.util;

import jakarta.annotation.PostConstruct;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.service.AbstractMenuService;
import kr.meal.polyMealServer.service.CrawlingMenuService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static kr.meal.polyMealServer.service.AbstractMenuService.menuMap;

@Component
public class InitMenuMap {

    @Autowired
    private PolyMenuServiceFactory polyMenuServiceFactory;
    @Autowired
    private CrawlingMenuService crawlingMenuService;

    @PostConstruct
    public void menuMapInitialValueSettings() throws InterruptedException {
        int schoolCodeLength = SchoolCode.values().length;
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

            AbstractMenuService polyMenuService = polyMenuServiceFactory.polyMenuServiceFactory(schoolCode);

            Document menuPageDocument = crawlingMenuService.crawlingMenuPageGet(schoolCode);
            Elements menuElements = polyMenuService.extractMenuElements(menuPageDocument);

            polyMenuService.makeMenuAndPutMenuMap(menuElements, schoolCode, now.toString());

            countDownLatch.countDown();
        };
    }
}
