package com.app.producerservice.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeRegSource {

    String OUTPUT="employeeRegistrationSource";

    @Output(OUTPUT)
    MessageChannel employeeRegistration();
}
