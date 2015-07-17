package com.tothenew.service;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * Created by intelligrape on 17/7/15.
 */
public interface EmailSendingService {
    public void sendMail(String email, String message);
}
