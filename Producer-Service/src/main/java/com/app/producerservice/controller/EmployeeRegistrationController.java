package com.app.producerservice.controller;

import com.app.producerservice.event.EmployeeRegSource;
import com.app.producerservice.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@EnableBinding(EmployeeRegSource.class)
public class EmployeeRegistrationController {

    @Autowired
    private EmployeeRegSource employeeRegSource;

    @PostMapping
    @ResponseBody
    public String EmpReg(@RequestBody Employee employee) throws Exception {
        employeeRegSource.employeeRegistration().send(MessageBuilder.withPayload(employee).build());
        System.out.println(employee.toString());
        return "Employee Registered";
    }
}
