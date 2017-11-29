package kea.project.exam.courseregistrationsystem;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration

@EnableRdsInstance(databaseName = "keacourses", dbInstanceIdentifier = "examdb", password = "ingastefanos")
public class AwsResourceConfig {

}
