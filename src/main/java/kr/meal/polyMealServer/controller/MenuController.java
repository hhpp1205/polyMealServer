package kr.meal.polyMealServer.controller;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.MenuSearchParam;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.service.PolyMenuService;
import kr.meal.polyMealServer.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController {

    private final PolyMenuService polyMenuService;

    /**
     * 학교코드와 날짜를 통해 메뉴 조회
     * @param schoolCode
     * @param date ex) 2023-07-24
     */
    @GetMapping("/menus")
    public Menu getMenu(MenuSearchParam request) {
        return polyMenuService.getMenu(request);
    }

}
