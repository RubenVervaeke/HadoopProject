/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.driver;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.exception.HadoopException;
import fi.karelia.publicservices.mapper.VehicleDetectionJoensuuMapper;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.RunJar;

/**
 *
 * @author ruben
 */
public class VehicleDetectionJoensuuDriver extends Driver {

    @Override
    public void execute(Resource r) throws HadoopException {
            
        try {
            
            System.out.println("Creating Configuration");
            Configuration config = HBaseConfiguration.create();
            config.set("fs.defaultFS", "hdfs://localhost:9000");
            config.set("mapreduce.framework.name", "yarn");
            // config.set("yarn.resourcemanager.address", "localhost:8032");
            
            System.out.println("Creating Job");
            Job job = new Job(config, "Vehicle detection sensor");
            job.setJarByClass(VehicleDetectionJoensuuDriver.class);
            job.setMapperClass(VehicleDetectionJoensuuMapper.class);
            FileInputFormat.addInputPath(job, new Path(r.getName()));
            FileOutputFormat.setOutputPath(job, new Path("output"));
            
            System.out.println("Executing job");
            boolean b = job.waitForCompletion(true);
            if (!b) {
                throw new HadoopException("Error executing job for driver: " + this.toString());
            }
            
        } catch (InterruptedException | ClassNotFoundException | IOException ex) {
            throw new HadoopException("Error executing driver: " + ex.getMessage());
        } 
    }
}
