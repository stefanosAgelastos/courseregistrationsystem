package kea.project.exam.courseregistrationsystem.persistence;

import kea.project.exam.courseregistrationsystem.model.Course;
import kea.project.exam.courseregistrationsystem.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Integer> {
    public List<Course> findCoursesByTeachersIsContaining(Teacher teacher);
}
