/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.data.domain.City;
import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.mapper.TrafficLightMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author Ruben
 */
public class DataPuller {
    
    private boolean processed;
    
    public DataPuller() {
        processed = true;
    }
    
    /**
     * Retrieve all of the cities which are in the system.
     * @return A list of the cities.
     */
    public List<City> pullAll() {
        return new ArrayList<City>();
    }
    
    /**
    * The pull method which pulls a specific resource.
    * @param resource The specific resource to pull.
    */
    public void pull(Resource resource) {
        processed = false;
//        try {
//            Job job = new Job();
//            job.setJobName("Trafficlightmapping");
//            
//            FileInputFormat.addInputPath(job, new Path("Example.csv"));
//            FileOutputFormat.setOutputPath(job, new Path("Output"));
//                     
//            job.setMapperClass(TrafficLightMapper.class);
//            
//            job.setOutputKeyClass(Text.class);
//            job.setOutputValueClass(Text.class);
//            
//            job.submit();
//        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
//            System.out.println("DataPuller error: " + ex.getMessage());
//            Logger.getLogger(DataPuller.class.getName()).log(Level.SEVERE, null, ex);
//        }
        processed = true;
    }

    /**
     * @return the processed
     */
    public boolean isProcessed() {
        return processed;
    }
}
