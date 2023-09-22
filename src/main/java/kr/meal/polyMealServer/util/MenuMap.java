package kr.meal.polyMealServer.util;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class MenuMap {

    private static final MenuMap instance = new MenuMap();
    public static MenuMap getMenuMap() {
        return instance;
    }

    private static final Map<SchoolCode, Map<String, Menu>> menuMap = new ConcurrentHashMap<>();

    public Menu get(SchoolCode schoolCode, String date) {
        if (isExistSchoolCode(schoolCode) && isExistMenu(schoolCode, date)) {
            return menuMap.get(schoolCode).get(date);
        }
        return Menu.ofEmptyMenu(schoolCode, date);
    }

    public void putMenu(SchoolCode schoolCode, String date, Menu menu) {
        menuMap.get(schoolCode).put(date, menu);
    }

    public void putSchoolEmptyMap(SchoolCode schoolCode) {
        menuMap.put(schoolCode, new HashMap<>());
    }

    public boolean isExistSchoolCode(SchoolCode schoolCode) {
        return menuMap.get(schoolCode) != null;
    }

    public boolean isExistMenu(SchoolCode schoolCode, String date) {
        return isExistSchoolCode(schoolCode) && menuMap.get(schoolCode).get(date) != null;
    }

    /**
     * 테스트용 코드 절대 사용하지 말 것
     */
    public void clearMepTestOnlyCode() {
        menuMap.clear();
    }


}
