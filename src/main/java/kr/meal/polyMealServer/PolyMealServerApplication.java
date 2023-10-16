package kr.meal.polyMealServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PolyMealServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolyMealServerApplication.class, args);
	}

}
