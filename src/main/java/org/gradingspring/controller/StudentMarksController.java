package org.gradingspring.controller;

import org.gradingspring.model.Student;
import org.gradingspring.model.StudentCourse;
import org.gradingspring.services.AppUserDetailsService;
import org.gradingspring.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping(path = "/student-marks")
public class StudentMarksController {

    private final StudentService studentService;

    @Autowired
    public StudentMarksController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
   public void getStudentMarks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Student student = studentService.getStudentByEmail( AppUserDetailsService.getAuthenticatedStudentEmail() );

        List<StudentCourse> studentCourses = studentService.getStudentCourses( student.getId() );

        request.setAttribute("student", student);

        request.setAttribute("studentCourses", studentCourses);

        request.getRequestDispatcher("/WEB-INF/views/student-marks.jsp").forward(request, response);

    }

}
