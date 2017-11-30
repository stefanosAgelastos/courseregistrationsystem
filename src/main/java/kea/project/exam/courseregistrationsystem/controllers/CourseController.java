package kea.project.exam.courseregistrationsystem.controllers;

import kea.project.exam.courseregistrationsystem.model.Course;
import kea.project.exam.courseregistrationsystem.model.StudyProgramme;
import kea.project.exam.courseregistrationsystem.model.Teacher;
import kea.project.exam.courseregistrationsystem.persistence.CourseRepository;
import kea.project.exam.courseregistrationsystem.persistence.StudyProgrammeRepository;
import kea.project.exam.courseregistrationsystem.persistence.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudyProgrammeRepository studyProgrammeRepository;

/*
    *//*
    * This method shows the page with the form for registering a new course.
    * *//*
    @RequestMapping("/newcoursedetails")
    public ModelAndView showCourseRegistrationForm(){

        Iterable<Teacher> teacherIterable = teacherRepository.findAll();
        Iterable<StudyProgramme> studyProgrammeIterable = studyProgrammeRepository.findAll();

        ModelAndView mv = new ModelAndView("courseForm");
        mv.getModel().put("teachers", teacherIterable);
        mv.getModel().put("programmes", studyProgrammeIterable);

        return mv;
    }
    */

/*

    @RequestMapping("/courses/{courseId}/editcoursedetails")
    public ModelAndView editcourses (@PathVariable int courseId){

        Course course= courseRepository.findOne(courseId);
        Iterable<Course> courseIterable = courseRepository.findAll();
        Iterable<StudyProgramme> studyProgrammeIterable = studyProgrammeRepository.findAll();
        Iterable<Teacher> teacherIterable = teacherRepository.findAll();
        ModelAndView mv = new ModelAndView("editablecourses");
        mv.getModel().put("programmes", studyProgrammeIterable);
        mv.getModel().put("courses", courseIterable);
        mv.getModel().put("course", course);
        mv.getModel().put("teachers", teacherIterable);
        return mv;
}

*/

    /*
    * This method shows the page with a course's details.
    *
    * TODO make the edit button available only to teachers
    * */
   @RequestMapping("/courses/{courseId}/details")
    public ModelAndView showCourseDetails(@PathVariable int courseId,
                                          Authentication authentication){

       String backUrl;
       boolean canEdit;
        if(authentication.getAuthorities().iterator().next().toString().equals("ROLE_TEACHER")){
            backUrl="/teacher";
            canEdit=true;
        }else{
            backUrl="/student";
            canEdit=false;
        }
       Course course= courseRepository.findOne(courseId);
       Iterable<Course> courseIterable = courseRepository.findAll();


          ModelAndView mv = new ModelAndView("coursedetails");
          mv.getModel().put("courses", courseIterable);
          mv.getModel().put("course", course);
          mv.getModel().put("canEdit",canEdit);
       mv.getModel().put("back", backUrl);

       return mv;
      }
/*

    @RequestMapping("/courses")
    public ModelAndView showCoursesList(){
        Iterable<Course> courseIterable = courseRepository.findAll();


        ModelAndView mv = new ModelAndView("courselist");
        mv.getModel().put("courses", courseIterable);
        return mv;
    }
*/

/*    @RequestMapping("/save")
    public ModelAndView saveNewCourse(

            @RequestParam(name = "titleDanish", defaultValue = "NO_NAME", required = true)
                    String titleDanish,
            @RequestParam(name = "titleEnglish", defaultValue = "NO_NAME", required = true)
                    String titleEnglish,
            @RequestParam(name = "semester", defaultValue = "-1", required = true)
                    int semester,
            @RequestParam(name = "classCode", defaultValue = "NO_NAME", required = true)
                    String classCode,
            @RequestParam(name = "studyProgramme", required = true)
                    int studyProgrammeId,
            @RequestParam(name = "mandatory", required = true)
                    boolean mandatory,
            @RequestParam(name = "ects", defaultValue = "-1", required = true)
                    int ects,
            @RequestParam(name = "courseLanguage", required = true)
                    boolean courseLanguage,
            @RequestParam(name = "minimumStudents", defaultValue = "-1", required = true)
                    int minimumStudents,
            @RequestParam(name = "expectedStudents", defaultValue = "-1", required = true)
                    int expectedStudents,
            @RequestParam(name = "maximumStudents", defaultValue = "-1", required = true)
                    int maximumStudents,
            @RequestParam(name = "prerequisites", defaultValue = "NO_NAME", required = true)
                    String prerequisites,
            @RequestParam(name = "learningOutcome", defaultValue = "NO_NAME", required = true)
                    String learningOutcome,
            @RequestParam(name = "content", defaultValue = "NO_NAME", required = true)
                    String content,
            @RequestParam(name = "learningActivities", defaultValue = "NO_NAME", required = true)
                    String learningActivities,
            @RequestParam(name = "examForm", defaultValue = "NO_NAME", required = true)
                    String examForm,
            @RequestParam(name = "teachers", defaultValue = "NO_NAME", required = true)
                    List<Integer> teachers
    ){
        System.out.println("saving new course here. english is: "+courseLanguage+" mandatory is: "+mandatory);

        Course newCourse = new Course();
        newCourse.setTitleEnglish(titleEnglish);
        newCourse.setTitleDanish(titleDanish);
        newCourse.setSemester(semester);
        newCourse.setClassCode(classCode);
        StudyProgramme studyProgramme = studyProgrammeRepository.findById(studyProgrammeId);
        newCourse.setStudyProgramme(studyProgramme);
        newCourse.setMandatory(mandatory);
        newCourse.setEcts(ects);
        newCourse.setCourseLanguage(courseLanguage);
        newCourse.setMinimumStudents(minimumStudents);
        newCourse.setExpectedStudents(expectedStudents);
        newCourse.setMaximumStudents(maximumStudents);
        newCourse.setPrerequisites(prerequisites);
        newCourse.setLearningOutcome(learningOutcome);
        newCourse.setContent(content);
        newCourse.setLearningActivities(learningActivities);
        newCourse.setExamForm(examForm);
        List<Teacher> teachersList = new ArrayList<>();
        for(int id: teachers){
            teachersList.add(teacherRepository.findById(id));
        }
        newCourse.setTeachers(teachersList);

        courseRepository.save(newCourse);

        Iterable<Teacher> teacherIterable = teacherRepository.findAll();
        Iterable<StudyProgramme> studyProgrammeIterable = studyProgrammeRepository.findAll();
        Iterable<Course> courseIterable = courseRepository.findAll();

        ModelAndView mv = new ModelAndView("courselist");
        System.out.println();
        mv.getModel().put("teachers", teacherIterable);
        mv.getModel().put("programmes", studyProgrammeIterable);
        mv.getModel().put("courses", courseIterable);

        return mv;

    }*/

}