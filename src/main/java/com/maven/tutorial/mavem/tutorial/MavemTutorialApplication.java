package com.maven.tutorial.mavem.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MavemTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MavemTutorialApplication.class, args);
	}

//	@Bean
//	public OpenAPI springShopOpenAPI() {
//		return new OpenAPI()
//				.info(new Info().title("SpringShop API")
//						.description("Spring shop sample application")
//						.version("v0.0.1")
//						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
//				.externalDocs(new ExternalDocumentation()
//						.description("SpringShop Wiki Documentation")
//						.url("https://springshop.wiki.github.org/docs"));
//	}
}
