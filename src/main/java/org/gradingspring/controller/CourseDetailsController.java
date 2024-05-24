package org.gradingspring.controller;


import org.gradingspring.model.CourseDetails;
import org.gradingspring.model.Student;
import org.gradingspring.services.AppUserDetailsService;
import org.gradingspring.services.CourseService;
import org.gradingspring.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(path = "/course-details")
public class CourseDetailsController {

    private final CourseService courseService;
    private final StudentService studentService;

    @Autowired
    public CourseDetailsController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }


    @GetMapping
    protected void getCourseDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String courseIdParam = request.getParameter("courseId");
        if (!isValidCourseIdParam(courseIdParam, response))
            return;

        int courseId = Integer.parseInt(courseIdParam);
        Student authenticatedStudent = studentService.getStudentByEmail( AppUserDetailsService.getAuthenticatedStudentEmail() );

        if (!isCourseAccessible(courseId, authenticatedStudent, response))
            return;

        prepareAndDispatchCourseDetailsView(courseId, request, response);
    }

    private void prepareAndDispatchCourseDetailsView(int courseId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseDetails courseDetails = courseService.getCourseDetails(courseId);
        request.setAttribute("courseDetails", courseDetails);
        request.getRequestDispatcher("/WEB-INF/views/course-details.jsp").forward(request, response);
    }


    // Validation methods

    private boolean isValidCourseIdParam(String courseIdParam, HttpServletResponse response) throws IOException {
        try {
            return Integer.parseInt(courseIdParam) > 0;
        } catch (Exception e) {
            response.sendError(400, "Can't resolve [" + courseIdParam + "], expect positive number.");
            return false;
        }
    }
    private boolean isCourseAccessible(int courseId, Student student, HttpServletResponse response) throws IOException {
        if (!courseService.isCourseExists(courseId)) {
            response.sendError(404, "Course not found");
            return false;
        }

        if (!courseService.isStudentEnrolledInCourse(student.getId(), courseId)) {
            response.sendError(403, "Access Denied: You must be enrolled in the course to view its details.");
            return false;
        }

        return true;
    }

}