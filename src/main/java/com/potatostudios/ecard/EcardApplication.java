package com.potatostudios.ecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class EcardApplication {

	public static void main(String[] args) {
		// .env 파일에 값이 있을 경우에만 System property를 설정합니다.
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		if (dotenv.get("DB_URL") != null) {
			System.setProperty("DB_URL", dotenv.get("DB_URL"));
		}
		if (dotenv.get("DB_USERNAME") != null) {
			System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		}
		if (dotenv.get("DB_PASSWORD") != null) {
			System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		}

		SpringApplication.run(EcardApplication.class, args);
	}

}
