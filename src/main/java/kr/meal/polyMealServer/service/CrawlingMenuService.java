package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CrawlingMenuService {

    public Elements getMenuElements(SchoolCode schoolCode, String date) {
        Document document = crawlingMenuPagePost(schoolCode, date);
        return extractMenuElements(document);
    }

    public Elements getMenuElements(SchoolCode schoolCode) {
        Document document = crawlingMenuPagePost(schoolCode);
        return extractMenuElements(document);
    }

    private Document crawlingMenuPagePost(SchoolCode schoolCode, String date) {
        String formattedDate = formattingDate(date);

        try {
            return Jsoup.connect(schoolCode.getUrl()).data("day", formattedDate).post();
        } catch (Exception e) {
            log.error("crawlingException, POST schoolCode={}, date={}", schoolCode, date);
            log.error("", e);
            return null;
        }
    }

    private String formattingDate(String date) {
        return date.replaceAll("[^0-9]", "");
    }

    private Document  crawlingMenuPagePost(SchoolCode schoolCode) {
        return crawlingMenuPageGet(schoolCode);
    }

    private Document crawlingMenuPageGet(SchoolCode schoolCode) {
        try {
            return Jsoup.connect(schoolCode.getUrl()).get();
        } catch (Exception e) {
            log.error("crawlingException, GET, schoolCode={}", schoolCode);
            log.error("", e);
            return null;
        }
    }

    private Elements extractMenuElements(Document menuPageDocument) {
        Elements menuTags = menuPageDocument.select(".menu tbody tr");
        Elements menuElements = menuTags.select("td");
        return menuElements;
    }
}
