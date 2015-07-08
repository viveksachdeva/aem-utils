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
    public void startJob() {
        final Map<String, Object> props = new HashMap<String, Object>();
        props.put("item1", "/something");
        props.put("count", 5);
        jobManager.addJob("my/special/jobtopic", props);
    }
}
