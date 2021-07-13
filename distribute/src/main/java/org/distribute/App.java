package org.distribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//(scanBasePackages = { "org.distribute" })
public class App {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);

	}

}
