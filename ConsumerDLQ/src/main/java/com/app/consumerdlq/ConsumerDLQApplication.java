package com.app.consumerdlq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerDLQApplication {

    private static final String ORIGINAL_QUEUE="employeeReg.employeeRegistrationQueue";

    private static final String DLQ=ORIGINAL_QUEUE + ".dlq";

    private static final String X_RETRIES_HEADER = "x-retries";

    public static void main(String[] args) {
        SpringApplication.run(ConsumerDLQApplication.class);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = DLQ)
    public void rePublishMsg(Message failedMessage){

        failedMessage=attemptToRepair(failedMessage);

        Integer retriesHeader = (Integer) failedMessage.getMessageProperties().getHeaders().get(X_RETRIES_HEADER);
        if (retriesHeader == null) {
            retriesHeader = Integer.valueOf(0);
        }
        if (retriesHeader < 3) {
            failedMessage.getMessageProperties().getHeaders().put(X_RETRIES_HEADER, retriesHeader + 1);
            this.rabbitTemplate.send(ORIGINAL_QUEUE, failedMessage);
        }
        else {
            System.out.println("Writing to database: "+failedMessage.toString());
            //we can write to a database or move to a parking lot queue
        }
    }

    private Message attemptToRepair(Message failedMessage) {
        String msgBody=new String(failedMessage.getBody());
        if (msgBody.contains("student")){
            msgBody=msgBody.replace("student","new Employee");
            System.out.println("Repairing Message :"+msgBody);
            return MessageBuilder.withBody(msgBody.getBytes()).
                             copyHeaders(failedMessage.getMessageProperties()
                            .getHeaders()).build();
        }
        return failedMessage;
    }
}
