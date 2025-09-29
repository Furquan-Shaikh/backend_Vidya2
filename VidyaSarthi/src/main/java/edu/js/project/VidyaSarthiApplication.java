package edu.js.project;

import edu.js.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class VidyaSarthiApplication  {

    @Autowired
    private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(VidyaSarthiApplication.class, args);
	}



}
