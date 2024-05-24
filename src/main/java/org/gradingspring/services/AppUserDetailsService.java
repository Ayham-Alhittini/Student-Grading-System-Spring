package org.gradingspring.services;

import org.gradingspring.data.dao.interfaces.IStudentDao;
import org.gradingspring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private  final IStudentDao studentDao;

    @Autowired
    public AppUserDetailsService(IStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentDao.getStudentByEmail(email);
        if (student == null)
            throw new UsernameNotFoundException("User not found");

        return User.withUsername(email)
                .password(student.getPassword())
                .authorities("STUDENT")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    public static String getAuthenticatedStudentEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }

}
