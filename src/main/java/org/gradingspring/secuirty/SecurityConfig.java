package org.gradingspring.secuirty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/student-marks", "/course-details").authenticated()
            .and()
            .formLogin( form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/student-marks")
                .loginProcessingUrl("/login")
                .failureUrl("/login?loginStatus=Invalid Credentials!!")
                .permitAll()
            );
        return http.build();
    }

}
