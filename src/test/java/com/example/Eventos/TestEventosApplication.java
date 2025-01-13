package com.example.Eventos;

import org.springframework.boot.SpringApplication;

public class TestEventosApplication {

	public static void main(String[] args) {
		SpringApplication.from(EventosApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
