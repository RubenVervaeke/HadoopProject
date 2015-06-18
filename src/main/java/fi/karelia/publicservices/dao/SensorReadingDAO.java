package fi.karelia.publicservices.dao;

import fi.karelia.publicservices.domain.SensorReading;
import fi.karelia.publicservices.exception.DBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * This is the DAO class used to get sensor reading objects out of the HBSE.
 * This Layer is Bo
 *
 * @author Jonas
 */
public class SensorReadingDAO {

    // The name of the table to which this class needs connection.
    private final static String tableName = "test";
    private final static String hdfsProperty = "fs.defaultFS";
    private final static String hdfsName = "hdfs://localhost:9000";
    private final static String mapReduceProperty = "mapreduce.framework.name";
    private final static String mapReduceName = "yarn";

    /**
     * This method makes connection with the table to perform operations on.
     *
     * @return Table Return the requested table.
     * @throws IOException Exception thrown when no connection with the database
     * is possible.
     */
    private Connection getConnection() throws DBException {
        Connection conn = null;
        try {
            // Make a new connection and table to create access to the database.
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "localhost");
            config.set(hdfsProperty, hdfsName);
            config.set(mapReduceProperty, mapReduceName);
            conn = ConnectionFactory.createConnection(config);
        } catch (IOException e) {
            throw new DBException("Error in SensorReadingDAO at getConnection(): " + e.getMessage());
        }
        return conn;
    }

    /**
     * This method is used to return all of the sensor readings in the table.
     *
     * @return List<SensorReading> This returns all of the sensor readings.
     */
    public List<SensorReading> getAllSensorReadings() throws DBException {
        // Make a list to store all of the readings in.
        List<SensorReading> retrievedSensorReadings = new ArrayList();

        try {
            Table lSensorTable = getConnection().getTable(TableName.valueOf(tableName));
            // Create a scan object to perform a 'scan' on the table.
            Scan s = new Scan();
            ResultScanner scanner = lSensorTable.getScanner(s);
            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                // Create a new reading object for each row in the table.
                SensorReading lReading = new SensorReading();
                lReading.setValue(Bytes.toString(rr.getValue(Bytes.toBytes("cf"), Bytes.toBytes("value"))));
                String toConvert = Bytes.toString(rr.getRow());
                lReading.setTimestamp(convertTimestamp(toConvert));
                lReading.setId(toConvert);
                // Add the reading to the list that will be returned.
                retrievedSensorReadings.add(lReading);
            }
        } catch (IOException e) {
            throw new DBException("Error in SensorReadingDAO at getAllSensorReadings(): " + e.getMessage());
        }
        return retrievedSensorReadings;
    }

    /**
     * This method returns a sensor reading with a requested id and Timestamp.
     *
     * @param id The requested id.
     * @param Timestamp The requested Timestamp.
     * @return SensorReading This returns the requested SensorReading.
     */
    public SensorReading findReadingById(String id) throws DBException {

        // A SensorReading object to save the requested reading in.
        SensorReading lReading = new SensorReading();
        try {
            Table lSensorTable = getConnection().getTable(TableName.valueOf(tableName));
            Get g = new Get(Bytes.toBytes(id));
            Result r = lSensorTable.get(g);

            // If there is a row with the requested row key (id + timestamp).
            if (r.getRow() != null) {
                lReading.setValue(Bytes.toString(r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("value"))));
                String toConvert = Bytes.toString(r.getRow());
                lReading.setTimestamp(convertTimestamp(toConvert));
                lReading.setId(Bytes.toString(r.getRow()));
            } // If there is no row with the row key, return null.
            else {
                return null;
            }
        } catch (IOException e) {
            throw new DBException("Error in SensorReadingDAO at findReadingByIdAndTimeStamp(): " + e.getMessage());
        }

        return lReading;
    }

    /**
     * This method returns all readings from a sensor with the requested id.
     *
     * @param id The id to get all the readings for.
     * @return List<SensorReading> Returns all of the requested readings.
     * @throws fi.karelia.publicservices.exception.DBException
     */
    public List<SensorReading> findReadingsBySensorId(String id) throws DBException {
        // Make a list to store all of the requested readings in.
        List<SensorReading> retrievedSensorReadings = new ArrayList();

        try {
            Table lSensorTable = getConnection().getTable(TableName.valueOf(tableName));
            // Create a scan object to perform a 'scan' on the table.
            Scan s = new Scan();
            ResultScanner scanner = lSensorTable.getScanner(s);
            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                String lIdString = (Bytes.toString(rr.getRow())).substring(0, 7);

                if (lIdString.equals(id)) {
                    // Create a new reading object for each row in the table.
                    SensorReading lReading = new SensorReading();
                    lReading.setValue(Bytes.toString(rr.getValue(Bytes.toBytes("cf"), Bytes.toBytes("value"))));
                    String toConvert = Bytes.toString(rr.getRow());
                    lReading.setTimestamp(convertTimestamp(toConvert));
                    lReading.setId(toConvert);
                    // Add the reading to the list that will be returned.
                    retrievedSensorReadings.add(lReading);

                }
            }
        } catch (IOException e) {
            throw new DBException("Error in SensorReadingDAO at findReadingsById(): " + e.getMessage());
        }
        return retrievedSensorReadings;
    }

    /**
     * This method returns all readings from a sensor with the requested
     * Timestamp.
     *
     * @param Timestamp The Timestamp to get all the readings for.
     * @return List<SensorReading> Returns all of the requested readings.
     */
    public List<SensorReading> findReadingsByTimestamp(Long Timestamp) throws DBException {

        // Make a list to store all of the requested readings in.
        List<SensorReading> retrievedSensorReadings = new ArrayList();

        try {
            Table lSensorTable = getConnection().getTable(TableName.valueOf(tableName));
            // Create a scan object to perform a 'scan' on the table.
            Scan s = new Scan();
            ResultScanner scanner = lSensorTable.getScanner(s);
            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                String lTimestampString = (Bytes.toString(rr.getRow())).substring(7);

                if (lTimestampString.equals(Timestamp.toString())) {
                    // Create a new reading object for each row in the table.
                    SensorReading lReading = new SensorReading();
                    lReading.setValue(Bytes.toString(rr.getValue(Bytes.toBytes("cf"), Bytes.toBytes("value"))));
                    String toConvert = Bytes.toString(rr.getRow());
                    lReading.setTimestamp(convertTimestamp(toConvert));
                    lReading.setId(toConvert);

                    // Add the reading to the list that will be returned.
                    retrievedSensorReadings.add(lReading);
                }
            }
        } catch (IOException e) {
            throw new DBException("Error in SensorReadingDAO at findReadingsByTimestamp(): " + e.getMessage());
        }
        return retrievedSensorReadings;
    }

    /**
     * This method converts a String timestamp to the appropriate Long
     * timestamp.
     *
     * @param toConvert
     * @return
     */
    private Long convertTimestamp(String pToConvert) {
        String lCorrectTimeStamp = pToConvert.substring(7, 17);
        Long lTimeStamp = Long.parseLong(lCorrectTimeStamp);
        return lTimeStamp;
    }
}
