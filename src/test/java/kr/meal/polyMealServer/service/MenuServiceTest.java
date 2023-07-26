package kr.meal.polyMealServer.service;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void getMenu() throws IOException {
        menuService.getMenu();
//        for(Element x : menuService.getMenu()) {
//                System.out.println("x = " + x);
//        }
    }

    @Test
    void getDate() throws IOException {
        for(Element x : menuService.getDate()) {
            System.out.println("x = " + x.toString().substring(31, 39));
        }
    }
}