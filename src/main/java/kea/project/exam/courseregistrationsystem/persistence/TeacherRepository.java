package kea.project.exam.courseregistrationsystem.persistence;

import kea.project.exam.courseregistrationsystem.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher,Integer> {
    public Teacher findById(int id);
}
