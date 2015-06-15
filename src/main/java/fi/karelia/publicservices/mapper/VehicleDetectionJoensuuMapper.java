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
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author hadoop
 */
public class VehicleDetectionJoensuuMapper extends Mapper<Text, BytesWritable, ImmutableBytesWritable, Put> {

    @Override
    protected void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {
        InputStream is = new ByteArrayInputStream(value.getBytes());
        SpreadSheetParser parser = new SpreadSheetParser(is);
        String content = parser.getParsedSpreadSheet();
        
        System.out.println("VehicleDetectionJoensuuMapper: Creating PUT");
        Put put = new Put(Bytes.toBytes("TestId"));
        put.add("cf".getBytes(), "TestAtt".getBytes(), content.getBytes());
//        t.put(put);
        
//        t.close();
        context.write(null, put);
    }

//    @Override
//    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
//        InputStream is = new ByteArrayInputStream(value.getBytes());
//        SpreadSheetParser parser = new SpreadSheetParser(is);
//        context.write(key, new Text(parser.getParsedSpreadSheet()));
//    }
}
