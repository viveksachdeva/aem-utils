package com.tothenew.impl;

import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.tothenew.service.EmailSendingService;
import com.tothenew.util.RabbitMQUtil;
import org.apache.commons.mail.HtmlEmail;
import org.apache.felix.scr.ScrService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.Session;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vivek Sachdeva on 17/7/15.
 */
@Service
@Component(immediate = true, enabled = true)
public class EmailSendingServiceImpl implements EmailSendingService {
    @Reference
    MessageGatewayService messageGatewayService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    ScrService scrService;

    MessageGateway messageGateway;

    @Override
    public void sendMail(String emailRecipient, String message) {
        try {
            /*Should use subservice to get administrative resource resolver instead of this code*/
            ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
            Resource templateResource = resourceResolver.getResource(RabbitMQUtil.EMAIL_TEMPLATE_PATH);
            if (templateResource.getChild("file") != null) {
                templateResource = templateResource.getChild("file");
            }
            ArrayList<InternetAddress> emailRecipients = new ArrayList<InternetAddress>();
            final MailTemplate mailTemplate = MailTemplate.create(templateResource.getPath(),
                    templateResource.getResourceResolver().adaptTo(Session.class));
            HtmlEmail email = new HtmlEmail();
            Map<String, String> mailTokens = new HashMap<String, String>();
            mailTokens.put("message", message);
            mailTokens.put("subject", "Dummy Subject");
            mailTokens.put("email", emailRecipient);
            if (mailTemplate != null) {

                emailRecipients.add(new InternetAddress(emailRecipient));
                email.setTo(emailRecipients);
                email.setSubject("Dummy Mail");
                email.setTextMsg(message);
                messageGateway = messageGatewayService.getGateway(HtmlEmail.class);
                messageGateway.send(email);
            }
        } catch (Exception e) {
            /*Put message in queue again in case of exception.. Based on type of exception, it can be decided whether it has to be put in queue again or not*/
            RabbitMQUtil.addMessageToQueue(scrService);
            e.printStackTrace(System.out);
        }

    }
}
