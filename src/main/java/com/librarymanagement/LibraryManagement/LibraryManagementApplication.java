package com.librarymanagement.LibraryManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.librarymanagement.LibraryManagement")
@ComponentScan(basePackages = "com.library_management")
@EnableJpaRepositories(basePackages = "com.library_management.repository")
@EntityScan(basePackages = "com.library_management.entity")
public class LibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);

		System.out.println("Application Started!");
	}

}
