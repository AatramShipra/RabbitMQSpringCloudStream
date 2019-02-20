package com.app.consumerservice.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeProcessSource {

    String INPUT="employeeProcessSource";

    @Input(INPUT)
    SubscribableChannel processEmpReg();
}
