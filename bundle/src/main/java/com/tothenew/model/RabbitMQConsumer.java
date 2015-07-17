package com.tothenew.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.tothenew.service.EmailSendingService;
import com.tothenew.util.QueueConstant;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*Delayed activation : Needed because it runs in a loop forever : Polls RabbitMQ Queue for Messages*/
@Component(enabled = false, name = "RabbitMessageConsumer")
public class RabbitMQConsumer {
    @Reference
    EmailSendingService emailSendingService;

    @Activate
    public void activate() {
        new Thread() {
            public void run() {
                System.out.println("Activating my component");
                ConnectionFactory factory = new ConnectionFactory();
                /*This is default RabbitMQ Configuration. Can be configured to point to customized server location and port. Default port is 15672*/
                factory.setHost("localhost");
                Channel channel = null;
                try {
                    Connection connection = factory.newConnection();
                    channel = connection.createChannel();
                    channel.queueDeclare(QueueConstant.DEFAULT_QUEUE_NAME, false, false, false, null);
                    System.out.println(" [*] Waiting for messages.");
                    QueueingConsumer consumer = new QueueingConsumer(channel);
                    channel.basicConsume(QueueConstant.DEFAULT_QUEUE_NAME, true, consumer);
                    while (true) {
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        String recipient = new String(delivery.getBody());
                        System.out.println("  '" + recipient + "'");
                        emailSendingService.sendMail(recipient, "Dummy Message");
                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
