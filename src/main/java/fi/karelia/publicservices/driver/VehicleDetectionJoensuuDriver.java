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
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 *
 * @author ruben
 */
public class VehicleDetectionJoensuuDriver implements Driver {

    @Override
    public void execute(Resource r) throws HadoopException {
        try {
            Configuration config = HBaseConfiguration.create();
            Job job = new Job(config, "Vehicle detection sensor");
            job.setJarByClass(VehicleDetectionJoensuuDriver.class);
            
            FileInputFormat.addInputPath(job, new Path(r.getName()));
            
            
//            Scan scan = new Scan();
//            scan.setCaching(500);
//            scan.setCacheBlocks(false);
//            
//            TableMapReduceUtil.initTableMapperJob("VehicleDetectionReadings", 
//                    scan, 
//                    VehicleDetectionJoensuuMapper.class, 
//                    null, 
//                    null, 
//                    job);
//
//            job.setNumReduceTasks(0);
            
            boolean b = job.waitForCompletion(true);
            if (!b) {
                throw new HadoopException("Error executing job for driver: " + this.toString());
            }
            
        } catch (IOException ex) {
            throw new HadoopException("Error executing driver: " + this.toString());
        } catch (InterruptedException | ClassNotFoundException ex) {
            throw new HadoopException("Error executing job for driver: " + this.toString());
        }
    }
}
