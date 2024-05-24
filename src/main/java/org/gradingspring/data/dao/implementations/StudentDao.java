package org.gradingspring.data.dao.implementations;

import org.gradingspring.data.DbConnection;
import org.gradingspring.data.dao.interfaces.IStudentDao;
import org.gradingspring.model.Student;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentDao implements IStudentDao {

    @Override
    public Student getStudentByEmail(String email) {
        try {

            String query =
                    "SELECT * " +
                    "FROM students " +
                    "WHERE email = ?";


            PreparedStatement stmt = DbConnection.createPreparedStatement(query);

            stmt.setString(1, email);

            ResultSet result = stmt.executeQuery();

            Student student = result.next() ? new Student(result) : null;

            DbConnection.closeConnection();

            return student;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}