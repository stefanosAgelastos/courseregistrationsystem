package kea.project.exam.courseregistrationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CourseregistrationsystemApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CourseregistrationsystemApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CourseregistrationsystemApplication.class, args);
	}

}