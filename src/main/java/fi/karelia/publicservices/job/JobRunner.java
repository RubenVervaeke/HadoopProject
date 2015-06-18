/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.job;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.exception.HadoopException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.util.RunJar;

/**
 *
 * @author ruben
 */
public class JobRunner {
    
    public static final String hdfsLocation = "hdfs://localhost/user/ruben/";
    
    private final Resource resource;
    // private NativeMapReduceOper mapRedOperation;
    
    public JobRunner(Resource r) {
        this.resource = r;
    }
    
    public void run() throws HadoopException {  
        String[] parameters = { resource.getDriverJarName(), hdfsLocation + resource.getName() };
        try {
            RunJar.main(parameters);
//        try {
//            String[] parameters = { hdfsLocation + resource.getName() };
//            mapRedOperation = new NativeMapReduceOper(new OperatorKey(), resource.getDriverJarName(), parameters);
//            mapRedOperation.runJob();
//        } catch (JobCreationException ex) {
//            throw new HadoopException("Error in JobRunner: " + ex.getMessage());
//        }
        } catch (Throwable ex) {
            throw new HadoopException("Error in JobRunner: " + ex);
        }
    }
}
