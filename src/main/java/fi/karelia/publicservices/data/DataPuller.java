/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.mapper.TrafficLightMapper;
import java.io.IOException;
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
    
    public void pull() {
        try {
            Job job = new Job();
            job.setJobName("Trafficlightmapping");
            
            FileInputFormat.addInputPath(job, new Path("Example.csv"));
            FileOutputFormat.setOutputPath(job, new Path("Output"));
            
            job.setMapperClass(TrafficLightMapper.class);
            
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            
            job.submit();
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            System.out.println("DataPuller error: " + ex.getMessage());
            Logger.getLogger(DataPuller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
