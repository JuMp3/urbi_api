package it.jump3.urbi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UrbiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbiDemoApplication.class, args);
	}

}
