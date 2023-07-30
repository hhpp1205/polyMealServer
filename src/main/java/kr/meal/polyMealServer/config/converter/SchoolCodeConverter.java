package kr.meal.polyMealServer.config.converter;

import kr.meal.polyMealServer.dto.SchoolCode;
import org.springframework.core.convert.converter.Converter;


public class SchoolCodeConverter implements Converter<String, SchoolCode> {
    @Override
    public SchoolCode convert(String schoolCode) {
        return SchoolCode.of(schoolCode);
    }
}
