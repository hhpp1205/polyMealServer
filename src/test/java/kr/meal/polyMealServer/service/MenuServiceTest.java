package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.SchoolCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void getMenu() throws IOException {
        menuService.getMenu(SchoolCode.POLY_DEAJEON, "2023-07-27");
    }

}