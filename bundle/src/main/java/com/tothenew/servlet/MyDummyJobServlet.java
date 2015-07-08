package com.tothenew.servlet;

import com.tothenew.JobGeneratorService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;

@Service
@SlingServlet(paths = "/bin/startJob")
public class MyDummyJobServlet extends SlingAllMethodsServlet {
    @Reference
    JobGeneratorService jobGeneratorService;


    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        /*This code just starts a dummy job*/
        jobGeneratorService.startJob();
        response.getWriter().print("Done");
    }
}
