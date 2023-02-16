//package com.javatechie.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class EMSSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("Basant")
//                .password(passwordEncoder().encode("Pwd1"))
//                .roles("USER");
//
//        auth.inMemoryAuthentication()
//                .withUser("Amit")
//                .password(passwordEncoder().encode("Pwd2"))
//                .roles("ADMIN");
//
//        auth.inMemoryAuthentication()
//                .withUser("Parmesh")
//                .password(passwordEncoder().encode("Pwd3"))
//                .roles("USER", "ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/nonsecureapp").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/welcome", "/text")
//                .authenticated().and().httpBasic();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
