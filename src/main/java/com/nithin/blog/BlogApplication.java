package com.nithin.blog;

// import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nithin.blog.config.AppConstant;
import com.nithin.blog.model.Role;
// import com.nithin.blog.model.User;
import com.nithin.blog.repository.RoleRepo;
// import com.nithin.blog.repository.UserRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	// @Autowired
	// private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));

		try {
			Role role = new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ADMIN_USER");

			Role role1 = new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("NORMAL_USER");

			List<Role> roles = List.of(role, role1);
			List<Role> savedRoles = this.roleRepo.saveAll(roles);

			savedRoles.forEach(r -> {
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
