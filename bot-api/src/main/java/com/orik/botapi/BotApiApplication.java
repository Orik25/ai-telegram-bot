package com.orik.botapi;

import com.orik.botapi.DAO.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotApiApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BotApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(roleRepository.findById(1L).get().getUsers().get(0).getFirstName());
//	}
}
