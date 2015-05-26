/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.dao;

import fi.karelia.publicservices.domain.Sensor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class SensorDAO {
    private static class SensorDAOCollection {
        public static List<Sensor> sensorCollection = null;    
        public static List<Sensor> getInstance() {
            if (sensorCollection == null) {
                sensorCollection = new ArrayList<>();
                // Add existing sensors
            }
            return sensorCollection;
        }      
    }
    
     private static List<Sensor> getSensorCollection() {
        return SensorDAOCollection.getInstance();
    }

    public List<Sensor> getAll() {
        return getSensorCollection();
    }

    public Sensor findById(int id) {
        Sensor resS = null;
        for (Sensor s : getSensorCollection()) {
            if (s.getId() == id) {
                resS = s;
                break;
            }
        }     
        return resS;
    }
}
