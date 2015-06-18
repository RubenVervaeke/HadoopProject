/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.bll;

import java.util.List;
import fi.karelia.publicservices.dao.SensorReadingDAO;
import fi.karelia.publicservices.domain.SensorReading;
import fi.karelia.publicservices.exception.ApplicationException;
import fi.karelia.publicservices.exception.DBException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas
 */
public class SensorReadingBLL {

    private SensorReadingDAO sDAO;

    /**
     * This method gets all sensorreadings from the database.
     *
     * @return List<SensorReading> A list of all of the sensors.
     */
    public List<SensorReading> getAllSensorReadings() throws ApplicationException{
        sDAO = new SensorReadingDAO();
        List<SensorReading> allReadings = new ArrayList();
        try {
            // Get all traffic readings.
            allReadings = sDAO.getAllSensorReadings();
        } catch (DBException ex) {
            throw new ApplicationException("Error in SensorReadingBLL: " + ex.getMessage());
        }
        return allReadings;
    }

    /**
     * This method returns a reading on a specified moment for a specified
     * sensor.
     *
     * @param id The id of the desired sensor.
     * @param Timestamp The timestamp for the requested moment.
     * @return SensorReading Return the reading that was found.
     * @throws ApplicationException Exception thrown when no reading was found
     * for this combination.
     */
    public SensorReading findReadingByIdAndTimeStamp(String id, Long Timestamp) throws ApplicationException, DBException {
        sDAO = new SensorReadingDAO();

        SensorReading lFoundReading = new SensorReading();

        try {
            // Find sensorreading for specified date and id.
            lFoundReading = sDAO.findReadingByIdAndTimeStamp(id, Timestamp);
            if (lFoundReading == null) {
                throw new ApplicationException("No readings found for requested id on requested moment: " + id + Timestamp);
            }
        } catch(DBException ex) {
            Logger.getLogger(SensorReadingBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lFoundReading;
    }

    /**
     * This method finds all sensorreadings from a sensor with a given id.
     *
     * @param id The id to identify the specific sensor.
     * @return List<SensorReadings> Return the readings from the desired sensor.
     * @throws ApplicationException
     */
    public List<SensorReading> findReadingsById(String id) throws ApplicationException {
        sDAO = new SensorReadingDAO();

        List<SensorReading> lFoundReadings = new ArrayList();

        try {
            // Find readings from specified id.
            lFoundReadings = sDAO.findReadingsById(id);

            if (lFoundReadings.isEmpty()) {
                throw new ApplicationException("No readings found for id: " + id);
            }
        } catch (DBException ex) {
            Logger.getLogger(SensorReadingBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lFoundReadings;
    }

    /**
     * This method returns all readings of a specific Timestamp, for every
     * sensor.
     *
     * @param Timestamp The requested Timestamp to get the readings for.
     * @return List<SensorReading> Return the readings from the specified
     * Timestamp.
     * @throws ApplicationException Exception thrown when no row is found for
     * the specified Timestamp.
     */
    public List<SensorReading> findReadingsByTimestamp(Long Timestamp) throws ApplicationException {
        sDAO = new SensorReadingDAO();

        // Find readings from specified timestamp.
        List<SensorReading> lFoundReadings = new ArrayList();
        try {
            lFoundReadings = sDAO.findReadingsByTimestamp(Timestamp);

            if (lFoundReadings.isEmpty()) {
                throw new ApplicationException("No readings found for Timestamp: " + Timestamp);
            }
        } catch (DBException ex) {
            Logger.getLogger(SensorReadingBLL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lFoundReadings;
    }
}
