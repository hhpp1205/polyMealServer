package kr.meal.polyMealServer.schedule;

import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.util.MenuMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
@RestController
public class MenuMapClearScheduler {

    private final int DAY_OF_WEEK = 7;
    private MenuMap menuMap = MenuMap.getMenuMap();

    @Scheduled(cron = "0 0 3 * * MON")
    @GetMapping("/clear")
    public void clearMenuMapBeforeTwoWeeks() {
        LocalDate now = LocalDate.now();

        LocalDate twoWeekAgo = now.minusWeeks(2);
        LocalDate mondayOfTwoWeekAgo = twoWeekAgo.with(DayOfWeek.MONDAY);

        for(int i = 0; i < DAY_OF_WEEK; i++) {
            LocalDate date = mondayOfTwoWeekAgo.plusDays(i);
            removeMenuFromAllSchools(date);
        }

    }

    private void removeMenuFromAllSchools(LocalDate date) {
        for (SchoolCode schoolCode : SchoolCode.values()) {
            menuMap.removeByDate(schoolCode, date.toString());
        }
    }

}
