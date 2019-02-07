package com.cvt.springbootunittesting;

import com.cvt.springbootunittesting.model.Students;
import com.cvt.springbootunittesting.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootUnitTestingApplication implements CommandLineRunner{

	@Autowired
	StudentService studentService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUnitTestingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		studentService.save(new Students(1,"Aman", (double) 12000));
		studentService.save(new Students(2,"Raman", (double) 12000));
		studentService.save(new Students(3,"Ram", (double) 12000));
		studentService.save(new Students(4,"Naveen", (double) 12000));
	}
}

