package org.jwt.oauth.AuthenticationServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AuthenticationServerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthenticationServerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServerApplication.class, args);
	}

}
