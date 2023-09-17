package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.SchoolCode;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CrawlingMenuServiceTest {

    @Autowired
    CrawlingMenuService crawlingMenuService;

    @Test
    void crawlingMenuPageGet() {

    }

    @Test
    void crawlingMenuPagePost() {
        Document document = crawlingMenuService.crawlingMenuPagePost(SchoolCode.POLY_DEAJEON, "2023-09-18");
        System.out.println("document = " + document);
    }
}