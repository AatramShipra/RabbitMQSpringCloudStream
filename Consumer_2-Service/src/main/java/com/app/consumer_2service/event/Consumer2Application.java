package com.app.consumer_2service.event;

import com.app.consumer_2service.event.event.EmployeeProcessSource2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
@EnableBinding(EmployeeProcessSource2.class)
public class Consumer2Application {

    public static void main(String[] args) {

        SpringApplication.run(Consumer2Application.class);
    }

    @StreamListener(target = EmployeeProcessSource2.INPUT)
    public void processRegisterEmployees(String employee) throws Exception {
        if (employee.contains("student")) {
            throw new Exception("Student is not Employee and move to DLQ..");
        }
        System.out.println("Employees Registered by Client Consumer 2" + employee);
    }


}
