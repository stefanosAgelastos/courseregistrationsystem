package kea.project.exam.courseregistrationsystem.persistence;

import kea.project.exam.courseregistrationsystem.model.StudyProgramme;
import org.springframework.data.repository.CrudRepository;

public interface StudyProgrammeRepository extends CrudRepository<StudyProgramme,Integer> {
    public StudyProgramme findById(int id);
}
