package com.redpill;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ComponentScan
public class MicroserviceMuleApp {

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(MicroserviceMuleApp.class);
    }

}
