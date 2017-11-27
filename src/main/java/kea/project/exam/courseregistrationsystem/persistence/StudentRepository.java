package kea.project.exam.courseregistrationsystem.persistence;

import kea.project.exam.courseregistrationsystem.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository <Student, Integer> {
    Student findStudentByStudentName(String studentSurname);
    Student findStudentByStudentUserName(String username);

}
