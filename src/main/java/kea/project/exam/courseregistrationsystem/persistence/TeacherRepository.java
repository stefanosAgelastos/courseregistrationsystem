package kea.project.exam.courseregistrationsystem.persistence;

import kea.project.exam.courseregistrationsystem.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher,Integer> {
    public Teacher findById(int id);
    public Teacher findByName(String name);
    public Iterable<Teacher> findByIdIsNot(int id);
}
