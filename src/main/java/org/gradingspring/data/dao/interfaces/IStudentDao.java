package org.gradingspring.data.dao.interfaces;

import org.gradingspring.model.Student;

public interface IStudentDao {
    Student getStudentByEmail(String email);
}
