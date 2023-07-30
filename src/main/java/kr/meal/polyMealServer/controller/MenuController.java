package kr.meal.polyMealServer.controller;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuService menuService;

    /**
     * @param schoolCode
     * @param date ex) 2023-07-24
     */
    @GetMapping("/menus")
    public Map<String, Menu> getMenu(@RequestParam SchoolCode schoolCode, @RequestParam String date ) throws IOException {
        return menuService.getMenu();
    }


}
