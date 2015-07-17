package com.tothenew.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.felix.scr.ScrService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {
    public static final String EMAIL_TEMPLATE_PATH = "/etc/rabbitmq/email.txt";

    /*Activate the message consumer component when a message is added to queue first time*/
    public static void activateConsumer(ScrService scrService) {
        org.apache.felix.scr.Component[] consumers = scrService.getComponents("RabbitMessageConsumer");
        if (consumers[0].getState() != org.apache.felix.scr.Component.STATE_ACTIVE) {
            consumers[0].enable();
        }
    }

    public static void addMessageToQueue(ScrService scrService) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QueueConstant.DEFAULT_QUEUE_NAME, false, false, false, null);
            String message = "vivek.sachdeva@intelligrape.com";
            /*Can send any data in bytes. Can also send serialized object*/
            channel.basicPublish("", QueueConstant.DEFAULT_QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            activateConsumer(scrService);
            channel.close();
            connection.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
