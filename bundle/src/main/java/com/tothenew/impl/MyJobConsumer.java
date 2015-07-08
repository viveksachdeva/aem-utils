package com.tothenew.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;

@Component
@Service(value={JobConsumer.class})
@Property(name=JobConsumer.PROPERTY_TOPICS, value="my/sling/job")
public class MyJobConsumer implements JobConsumer {

    public JobResult process(final Job job) {
        System.out.println("==========================================");
        System.out.println(job.getProperty("count"));
        System.out.println("==========================================");
        // process the job and return the result
        int count = (Integer)job.getProperty("count");
        if(count > 3){
           return JobResult.FAILED;
        }
        return JobResult.OK;
    }
}