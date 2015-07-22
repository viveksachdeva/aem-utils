package com.tothenew.impl.machinetranslation;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * Created by intelligrape on 8/7/15.
 */
@Service
@Component(enabled = true, immediate = true)
@Property(name=org.osgi.service.event.EventConstants.EVENT_TOPIC,
        value=org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_ADDED)
public class MyOsgiEventHandler implements EventHandler {

    @Override
    public void handleEvent(Event event) {
        System.out.print("My OSGi Event Handler");
    }
}
