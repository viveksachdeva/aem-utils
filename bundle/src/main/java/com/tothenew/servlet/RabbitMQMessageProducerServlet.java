package com.tothenew.servlet;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tothenew.util.QueueConstant;
import com.tothenew.util.RabbitMQUtil;
import org.apache.felix.scr.ScrService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
@SlingServlet(paths = "/bin/generateMessage", generateComponent = false)
@Component(immediate = true, enabled = true)
public class RabbitMQMessageProducerServlet extends SlingAllMethodsServlet {
    @Reference
    ScrService scrService;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        RabbitMQUtil.addMessageToQueue(scrService);
    }
}
