package com.dan.cosmos;

import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.AppUserRole;
import com.dan.cosmos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class CosmosApplication {

	final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(CosmosApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
