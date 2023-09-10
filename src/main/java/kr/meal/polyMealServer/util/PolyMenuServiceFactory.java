package kr.meal.polyMealServer.util;

import kr.meal.polyMealServer.dto.SchoolCode;
import kr.meal.polyMealServer.service.AbstractMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Component
@RequiredArgsConstructor
public class PolyMenuServiceFactory {

    private final ApplicationContext ac;

    public AbstractMenuService polyMenuServiceFactory(SchoolCode schoolCode) {
        switch (schoolCode) {
            case POLY_CHANGWON : return (AbstractMenuService) ac.getBean("polyChangwonMenuService");
            default: return (AbstractMenuService) ac.getBean("polyMenuService");
        }
    }
}
