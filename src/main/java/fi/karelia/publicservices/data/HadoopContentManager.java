/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.exception.HadoopException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.http.HttpEntity;

/**
 *
 * @author ruben
 */
public class HadoopContentManager {
    
    public static void writeToHDFS(Resource r, HttpEntity he) throws HadoopException {
     
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000/"), conf);
            
            Path outFile = new Path(r.getName());
            
            while (he.isStreaming()) {
                he.writeTo(fs.create(outFile, true));  
            }           
            
        } catch (IOException ex) {
            throw new HadoopException("Error writing to HDFS: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            throw new HadoopException("Error in FileSystem URI");
        } 
    }
    
    public static void deleteFromHDFS(String path) throws HadoopException {
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            
            Path p = new Path(path);
            if (fs.exists(p)) {
                fs.delete(p, true);
            }
        } catch (IOException ex) {
            throw new HadoopException("Error deleting from HDFS: " + ex.getMessage());
        }
    } 
}
