/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.bll;

import fi.karelia.publicservices.dao.SensorDAO;
import fi.karelia.publicservices.domain.Sensor;
import fi.karelia.publicservices.exception.ApplicationException;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class SensorBLL {
    private SensorDAO sDAO;
    
    /**
     * This method gets all sensors from the database.
     * @return List<Sensor> A list of all of the sensors.
     */
    public List<Sensor> getAll() {
        sDAO = new SensorDAO();
        // Get all traffic lights
        return sDAO.getAll();
    }

    /**
     * This method finds a sensor by id.
     * @param id The id to identify the specific sensor.
     * @return Sensor Return the desired sensor.
     * @throws ApplicationException 
     */
    public Sensor findById(int id) throws ApplicationException {
        sDAO = new SensorDAO();
        // Find trafficlight
        Sensor s = sDAO.findById(id);
        if (s == null) {
            throw new ApplicationException("Sensor doesn't exist for id: " + id);
        }
        return s;
    }
}
