package kea.project.exam.courseregistrationsystem.controllers;

import kea.project.exam.courseregistrationsystem.model.Student;
import kea.project.exam.courseregistrationsystem.model.StudyProgramme;
import kea.project.exam.courseregistrationsystem.persistence.CourseRepository;
import kea.project.exam.courseregistrationsystem.persistence.StudentRepository;
import kea.project.exam.courseregistrationsystem.persistence.StudyProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
public class StudentController {


    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudyProgrammeRepository studyProgrammeRepository;
    @Autowired
    private CourseRepository courseRepository;




 /*
 *  this is the method that handles the initial GET request from the student.
 **/

    @GetMapping("/student/{studentId}/courses")
    public ModelAndView showCoursePageForStudent(@PathVariable int studentId){
        Student thisStudent = studentRepository.findOne(studentId);

        if(thisStudent.getCourses().isEmpty()){

            ModelAndView mv = new ModelAndView("coursesForStudent");
            mv.getModel().put("courses", courseRepository.findAll());
            mv.getModel().put("studentId", thisStudent.getId());

            return mv;

        }else{

            ModelAndView mv = new ModelAndView("selectedCoursesForStudent");
            mv.getModel().put("courses", thisStudent.getCourses());
            mv.getModel().put("studentId", thisStudent.getId());
            return mv;
        }


    }

    /*
    * This method handles the registration of the courses to a student
    * */
    @PostMapping(value = "/student/{studentId}/courses")
    public ModelAndView showRegisteredCoursesForStudent(@PathVariable int studentId,
                                                        @RequestParam Map<String, String> queryMap){
        Student thisStudent = studentRepository.findOne(studentId);

        Set<String> selectedCourses = queryMap.keySet();
        System.out.println(selectedCourses);


        for(String s: selectedCourses){
            thisStudent.getCourses().add(courseRepository.findOne(Integer.parseInt(s)));
        }

        studentRepository.save(thisStudent);

        ModelAndView mv = new ModelAndView("selectedCoursesForStudent");
        mv.getModel().put("courses", thisStudent.getCourses());
        mv.getModel().put("studentId", thisStudent.getId());

        return mv;
    }


    /*
    * This method erases the previous selection of courses for a student
    */
    @RequestMapping(value = "/student/{studentId}/courses", params = "cancel")
    public ModelAndView cancelRegistrationAndSendSelectionPage(@PathVariable int studentId){

        System.out.println("Cancel registration");
        Student thisStudent = studentRepository.findOne(studentId);

        thisStudent.getCourses().removeAll(thisStudent.getCourses());
        studentRepository.save(thisStudent);

        ModelAndView mv = new ModelAndView("coursesForStudent");
        mv.getModel().put("courses", courseRepository.findAll());
        mv.getModel().put("studentId", thisStudent.getId());

        return mv;

    }


    @GetMapping("/")
    public ModelAndView homepageload (){

        Iterable<StudyProgramme> studyProgrammeIterable = studyProgrammeRepository.findAll();
        ModelAndView mv = new ModelAndView("index");
        mv.getModel().put("programmes", studyProgrammeIterable);
        return mv;
    }



    @GetMapping("/registerStudents")
    public ModelAndView registerStudents(

            @RequestParam(name = "studentFirstName", defaultValue = "NO_NAME")
                    String studentName,
            @RequestParam(name = "studentProgramme", defaultValue = "NO_PROGRAMME")
                    int studentProgramme,
            @RequestParam(name = "studentUsername", defaultValue = "NO_USERNAME")
                    String studentUserName,
            @RequestParam(name = "studentPassword", defaultValue = "NO_PASSWORD")
                    String studentPassword) {

        StudyProgramme studprog = studyProgrammeRepository.findById(studentProgramme);


        Student student = new Student();
        student.setStudentName(studentName);
        student.setStudyProgramme(studprog);
        student.setStudentUserName(studentUserName);
        student.setStudentPassword(studentPassword);

        studentRepository.save(student);

        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.getModel().put("studentslist", studentRepository.findAll());
        modelAndView.getModel().put("student", student);


        return modelAndView;

    }





}
