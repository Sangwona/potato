package com.potatostudios.ecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcardApplication {

	public static void main(String[] args) {
		// .env 파일에 값이 있을 경우에만 System property를 설정합니다.

		System.out.println("🔍 DB_URL (System.getenv): " + System.getenv("DB_URL"));
		System.out.println("🔍 DB_USERNAME (System.getenv): " + System.getenv("DB_USERNAME"));

		SpringApplication.run(EcardApplication.class, args);
	}

}
