/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.mapper;

import com.aspose.hadoop.core.SpreadSheetParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author hadoop
 */
public class VehicleDetectionJoensuuMapper extends Mapper<NullWritable, BytesWritable, NullWritable, NullWritable> {

    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        InputStream is = new ByteArrayInputStream(value.getBytes());
        try {
            SpreadSheetParser parser = new SpreadSheetParser(is);
            
        } catch (IOException ex) {
            
        }
    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context); //To change body of generated methods, choose Tools | Templates.
    }
    
}
