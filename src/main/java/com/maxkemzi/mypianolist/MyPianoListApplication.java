package com.maxkemzi.mypianolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = {
		MyPianoListApplication.class,
		// Handle LocalDate properly in tests
		Jsr310JpaConverters.class
})
@SpringBootApplication
public class MyPianoListApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyPianoListApplication.class, args);
	}
}
