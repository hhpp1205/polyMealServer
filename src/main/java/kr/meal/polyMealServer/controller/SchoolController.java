package kr.meal.polyMealServer.controller;

import kr.meal.polyMealServer.dto.SchoolCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SchoolController {

    @GetMapping("/schools")
    public Map<String, String> getAllSchools() {
        Map<String, String> schoolCodeMap = new TreeMap<>();

        for(SchoolCode schoolCode : SchoolCode.values()) {
            schoolCodeMap.put(schoolCode.getSchoolName(), schoolCode.getSchoolCode());
        }

        return schoolCodeMap;
    }
}
