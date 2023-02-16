package com.sbms.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Student callJitu(){
        return new Student("jeetendra...");
    }
    @Bean
    public Student CallNewPerson(){
        return new Student("jeetendra...");
    }
}
class Test{

    public static void main(String[] args) {
       // System.out.println(con);
    }
}
