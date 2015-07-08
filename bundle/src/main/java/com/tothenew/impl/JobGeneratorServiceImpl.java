package com.tothenew.impl;

import com.tothenew.JobGeneratorService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.event.jobs.JobManager;

import java.util.HashMap;
import java.util.Map;


@Component
@Service
public class JobGeneratorServiceImpl implements JobGeneratorService {
    @Reference
    private JobManager jobManager;

    @Override
    public void publishJob() {
        final Map<String, Object> jobProperties = new HashMap<String, Object>();
        jobProperties.put("jobName", "some dummy job");
        jobProperties.put("count", 3);
        jobProperties.put("job location", "yet another city");
        jobManager.addJob("my/sling/job", jobProperties);
    }
}
