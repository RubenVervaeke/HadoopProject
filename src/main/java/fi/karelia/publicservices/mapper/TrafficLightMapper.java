/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.mapper;

import fi.karelia.publicservices.bll.TrafficLightBLL;
import fi.karelia.publicservices.domain.TrafficLight;
import fi.karelia.publicservices.exception.ApplicationException;
import fi.karelia.publicservices.exception.DBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

/**
 *
 * @author Ruben
 */
public class TrafficLightMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        TrafficLightBLL tlBLL = new TrafficLightBLL();
        try {
            String[] pair = value.toString().split(",");
            tlBLL.add(new TrafficLight(Integer.parseInt(pair[0]), pair[1]));
        } catch (NumberFormatException ex) {
            Logger.getLogger(TrafficLightMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
