package org.gradingspring.services;

import org.gradingspring.data.dao.interfaces.IStudentCourseDao;
import org.gradingspring.data.dao.interfaces.IStudentDao;
import org.gradingspring.model.Student;
import org.gradingspring.model.StudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final IStudentCourseDao studentCourseDao;
    private final IStudentDao studentDao;
    public StudentService(IStudentDao studentDao, IStudentCourseDao studentCourseDao) {
        this.studentDao = studentDao;
        this.studentCourseDao = studentCourseDao;
    }

    public List<StudentCourse> getStudentCourses(int studentId) {
        return studentCourseDao.getStudentCourses(studentId);
    }

    public Student getStudentByEmail(String email) {
        return studentDao.getStudentByEmail(email);
    }

}
