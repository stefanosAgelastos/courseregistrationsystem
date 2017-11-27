package kea.project.exam.courseregistrationsystem.controllers;

import kea.project.exam.courseregistrationsystem.model.Course;
import kea.project.exam.courseregistrationsystem.model.StudyProgramme;
import kea.project.exam.courseregistrationsystem.model.Teacher;
import kea.project.exam.courseregistrationsystem.persistence.CourseRepository;
import kea.project.exam.courseregistrationsystem.persistence.StudyProgrammeRepository;
import kea.project.exam.courseregistrationsystem.persistence.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class TeacherController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudyProgrammeRepository studyProgrammeRepository;



    /*
    * This method sends a form for editing an existing course.
    *
    * */
    @RequestMapping("/courses/{courseId}/editcoursedetails")
    public ModelAndView editCourses(@PathVariable int courseId){

        Course course= courseRepository.findOne(courseId);
        Iterable<StudyProgramme> studyProgrammeIterable = studyProgrammeRepository.findAll();
        Iterable<Teacher> teacherIterable = teacherRepository.findAll();
        ModelAndView mv = new ModelAndView("editablecourses");
        mv.getModel().put("programmes", studyProgrammeIterable);
        mv.getModel().put("course", course);
        mv.getModel().put("teachers", teacherIterable);
        return mv;
    }


    /*
    * This method displays a list of the courses.
    * The method displays only courses that the logged in teacher is teaching.
    * */
    @RequestMapping("/teacher")
    public ModelAndView showCoursesList(Principal principal){

        //find the logged in teacher
        Teacher loggedInTeacher = teacherRepository.findByName(principal.getName());

        Iterable<Course> courseIterable = courseRepository.findCoursesByTeachersIsContaining(loggedInTeacher);

        ModelAndView mv = new ModelAndView("courselist");
        mv.getModel().put("courses", courseIterable);

        return mv;
    }


    /*
    * This method deletes the selected courses.
    * */
    @RequestMapping("/teacher/delete")
    public ModelAndView deleteCourses (@RequestParam Map<String, String> queryMap,
                                       Principal principal){
        System.out.println("deleting courses...");
        Set<String> selectedCourses = queryMap.keySet();
        System.out.println(selectedCourses);
        for (String key: selectedCourses) {
            courseRepository.delete(Integer.parseInt(key));
        }
        //finds the logged in teacher
        Teacher loggedInTeacher = teacherRepository.findByName(principal.getName());
        //finds the courses associated with that teacher
        Iterable<Course> courseIterable = courseRepository.findCoursesByTeachersIsContaining(loggedInTeacher);
        ModelAndView mv = new ModelAndView("courselist");
        mv.getModel().put("courses", courseIterable);
        return mv;
    }

    /*
    This method shows the page with the form for registering a new course.
    */
    @RequestMapping("/teacher/newcoursedetails")
    public ModelAndView showCourseRegistrationForm(Principal principal){

        //find the logged in teacher
        Teacher loggedInTeacher = teacherRepository.findByName(principal.getName());

        //get list of all teachers except the one that is logged in, he has to teach the class in order to create it,
        //so he will be added in the save course method
        Iterable<Teacher> teacherIterable = teacherRepository.findByIdIsNot(loggedInTeacher.getId());



        Iterable<StudyProgramme> studyProgrammeIterable = studyProgrammeRepository.findAll();

        ModelAndView mv = new ModelAndView("courseForm");
        mv.getModel().put("teachers", teacherIterable);
        mv.getModel().put("programmes", studyProgrammeIterable);

        return mv;
    }


/*
        WE CAN DELETE THIS ONE I THINK

    @ResponseBody
    @RequestMapping("/courses/{courseId}/details")
    String showCourseDetails(@PathVariable int courseId){
        System.out.println("ID is "+courseId);
        return "Dynamic URI parameter fetched: " + courseId;
    }*/

    @RequestMapping("/save")
    public ModelAndView saveNewCourse(

            @RequestParam(name = "titleDanish", required = true)
                    String titleDanish,
            @RequestParam(name = "titleEnglish", required = true)
                    String titleEnglish,
            @RequestParam(name = "semester", required = true)
                    int semester,
            @RequestParam(name = "classCode", required = true)
                    String classCode,
            @RequestParam(name = "studyProgramme", required = true)
                    int studyProgrammeId,
            @RequestParam(name = "mandatory", required = true)
                    boolean mandatory,
            @RequestParam(name = "ects", required = true)
                    int ects,
            @RequestParam(name = "courseLanguage", required = true)
                    boolean courseLanguage,
            @RequestParam(name = "minimumStudents",  required = true)
                    int minimumStudents,
            @RequestParam(name = "expectedStudents",  required = true)
                    int expectedStudents,
            @RequestParam(name = "maximumStudents",  required = true)
                    int maximumStudents,
            @RequestParam(name = "prerequisites", required = true)
                    String prerequisites,
            @RequestParam(name = "learningOutcome", required = true)
                    String learningOutcome,
            @RequestParam(name = "content", required = true)
                    String content,
            @RequestParam(name = "learningActivities", required = true)
                    String learningActivities,
            @RequestParam(name = "examForm", required = true)
                    String examForm,
            @RequestParam(name = "teachers", required = false)
                    List<Integer> teachersIds,
            @RequestParam(name="edit", defaultValue = "0")
                    int editCourseWithId,
            Principal principal
    ){
        System.out.println("saving new course here. english is: "+courseLanguage+" mandatory is: "+mandatory);

        //find the current teacher
        Teacher loggedInTeacher = teacherRepository.findByName(principal.getName());
        Course courseToSave = new Course();

        //if editCourseWithId is different than 0, then the request is for editing a course with this ID
        if (editCourseWithId!=0) {
            courseToSave.setId(editCourseWithId);
        }

        courseToSave.setTitleEnglish(titleEnglish);
        courseToSave.setTitleDanish(titleDanish);
        courseToSave.setSemester(semester);
        courseToSave.setClassCode(classCode);
        StudyProgramme studyProgramme = studyProgrammeRepository.findById(studyProgrammeId);
        courseToSave.setStudyProgramme(studyProgramme);
        courseToSave.setMandatory(mandatory);
        courseToSave.setEcts(ects);
        courseToSave.setCourseLanguage(courseLanguage);
        courseToSave.setMinimumStudents(minimumStudents);
        courseToSave.setExpectedStudents(expectedStudents);
        courseToSave.setMaximumStudents(maximumStudents);
        courseToSave.setPrerequisites(prerequisites);
        courseToSave.setLearningOutcome(learningOutcome);
        courseToSave.setContent(content);
        courseToSave.setLearningActivities(learningActivities);
        courseToSave.setExamForm(examForm);
        List<Teacher> teachersList = new ArrayList<>();
        if(teachersIds!=null) {
            for (int id : teachersIds) {
                teachersList.add(teacherRepository.findById(id));
            }
        }
        //if this is a new course then the teacher that creates the course selects
        // only the other teachers (s)he will be teaching with, and (s)he is added by default here
        //otherwise, if the course is being edited, then the teacher gets the option to not chose him/herself
        if(editCourseWithId==0) {
            teachersList.add(loggedInTeacher);
        }
        courseToSave.setTeachers(teachersList);
        courseRepository.save(courseToSave);

        Iterable<Course> courseIterable = courseRepository.findCoursesByTeachersIsContaining(loggedInTeacher);

        ModelAndView mv = new ModelAndView("courselist");
        mv.getModel().put("courses", courseIterable);


        return mv;

    }

}
