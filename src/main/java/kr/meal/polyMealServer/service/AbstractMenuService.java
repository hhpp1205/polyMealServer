package kr.meal.polyMealServer.service;

import kr.meal.polyMealServer.dto.Menu;
import kr.meal.polyMealServer.dto.SchoolCode;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMenuService {

    protected final String[] DAY_OF_THE_WEEK =  {"월","화","수","목","금","토","일"};

    /**
     * SchoolCode 학교
     * String 날짜  ex) 2023-08-02
     */
    protected static Map<SchoolCode, Map<String, Menu>> menuMap = new HashMap<>();

    abstract void crawlingMenu(SchoolCode schoolCode, String date);

    abstract Menu getMenu(SchoolCode schoolCode, String date);
}
