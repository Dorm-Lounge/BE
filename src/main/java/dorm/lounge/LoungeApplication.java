package dorm.lounge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoungeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoungeApplication.class, args);
	}

}
