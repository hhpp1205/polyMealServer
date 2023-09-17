package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CrawlingMenuService {

    public Document crawlingMenuPageGet(SchoolCode schoolCode) {
        try {
            return Jsoup.connect(schoolCode.getUrl()).get();
        } catch (Exception e) {
            log.error("crawlingException, GET, schoolCode={}", schoolCode);
            log.error("", e);
            return null;
        }
    }

    public Document crawlingMenuPagePost(SchoolCode schoolCode, String date) {
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
}
