package kr.meal.polyMealServer.util;

import java.util.Comparator;

public class SchoolNameComparator implements Comparator<String> {

    @Override
    public int compare(String sc1, String sc2) {
        return sc1.compareTo(sc2);
    }
}
