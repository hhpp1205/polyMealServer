package kr.meal.polyMealServer.util;

import jakarta.annotation.PostConstruct;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.service.AbstractMenuService;
import kr.meal.polyMealServer.service.CrawlingMenuService;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Component
@RequiredArgsConstructor
@Profile("product")
public class InitMenuMap {

    private final PolyMenuServiceFactory polyMenuServiceFactory;
    private final CrawlingMenuService crawlingMenuService;
    private final MenuMap menuMap = MenuMap.getMenuMap();

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
            menuMap.putSchoolEmptyMap(schoolCode);

            LocalDate now = LocalDate.now();

            AbstractMenuService polyMenuService = polyMenuServiceFactory.polyMenuServiceFactory(schoolCode);

            Elements menuElements = crawlingMenuService.getMenuElements(schoolCode);

            polyMenuService.makeMenuAndPutMenuMap(menuElements, schoolCode, now.toString());

            countDownLatch.countDown();
        };
    }
}
