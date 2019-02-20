package com.app.consumerservice;

import com.app.consumerservice.event.EmployeeProcessSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
@EnableBinding(EmployeeProcessSource.class)
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    @StreamListener(target = EmployeeProcessSource.INPUT)
    public void processRegisterEmployees(String employee){
        System.out.println("Employees Registered by Client Consumer 1 " + employee);
    }
}
