package org.gradingspring.data.dao.interfaces;

import org.gradingspring.model.StudentCourse;

import java.util.List;

public interface IStudentCourseDao {
    List<StudentCourse> getStudentCourses(int studentId);
    List<Integer> getCourseMarks(int courseId);
    boolean verifyStudentCourseEnrollment(int studentId, int courseId);
}
