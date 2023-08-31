package kr.meal.polyMealServer.util;

import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.service.AbstractMenuService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class PolyMenuServiceFactory {

    WebApplicationContext context =
            ContextLoader.getCurrentWebApplicationContext();

    public AbstractMenuService polyMenuServiceFactory(SchoolCode schoolCode) {
        switch (schoolCode) {
            case POLY_CHANGWON : return (AbstractMenuService) context.getBean("polyChangwonMenuService");
            default: return (AbstractMenuService) context.getBean("polyMenuService");
        }
    }
}
