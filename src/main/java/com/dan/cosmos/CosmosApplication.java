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
public class CosmosApplication implements CommandLineRunner {

	final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(CosmosApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		if (userService.usersCount() != 0) {
			return;
		}

		AppUser admin = new AppUser();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@email.com");
		admin.setFirstName("admin");
		admin.setLastName("admin");
		admin.setEnabled(true);
		admin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_ADMIN)));

		userService.signup(admin);

		AppUser client = new AppUser();
		client.setUsername("client");
		client.setPassword("client");
		client.setEmail("client@email.com");
		client.setFirstName("client");
		client.setLastName("client");
		client.setEnabled(true);
		client.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT)));

		userService.signup(client);
	}
}
