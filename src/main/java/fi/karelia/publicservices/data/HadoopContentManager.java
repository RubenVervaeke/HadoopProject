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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 * @author ruben
 */
public class HadoopContentManager {
    
    public static void writeToHDFS(Resource r, InputStream in) throws HadoopException {
     
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            
            Path outFile = new Path(r.getName());
            
            OutputStreamWriter osw = new OutputStreamWriter(fs.create(outFile, true));
            BufferedWriter br = new BufferedWriter(osw);
            
            int c;
            while ((c = in.read()) != -1) {
                br.write((char) c);
            }
            br.close();
        } catch (IOException ex) {
            throw new HadoopException("Error writing to HDFS: " + ex.getMessage());
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
