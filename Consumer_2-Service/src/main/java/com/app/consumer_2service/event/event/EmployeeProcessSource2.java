package com.app.consumer_2service.event.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeeProcessSource2 {

        String INPUT="employeeProcessSource2";

        @Input(INPUT)
        SubscribableChannel processEmpReg();
}
