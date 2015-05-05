/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.dao;

import fi.karelia.publicservices.domain.TrafficLight;
import fi.karelia.publicservices.exception.DBException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ruben
 */
public class TrafficLightDAO {

    private static class TrafficLightDAOCollection {
        public static List<TrafficLight> trafficLightCollection = null;    
        public static List<TrafficLight> getInstance() {
            if (trafficLightCollection == null) {
                trafficLightCollection = new ArrayList<>();
                // Add initial traffic lights
                trafficLightCollection.add(new TrafficLight(1, "test1"));
                trafficLightCollection.add(new TrafficLight(3, "test2"));
            }
            return trafficLightCollection;
        }      
    }
    
     private static List<TrafficLight> getTrafficLightCollection() {
        return TrafficLightDAOCollection.getInstance();
    }
    
    public TrafficLight add(TrafficLight tl) {
        getTrafficLightCollection().add(tl);
        return tl;
    }

    public List<TrafficLight> getAll() {
        return getTrafficLightCollection();
    }

    public void remove(int id) {
        getTrafficLightCollection().remove(findById(id));
    }

    public TrafficLight update(TrafficLight tl) {
        TrafficLight resTL = findById(tl.getId());
        resTL.setValue(tl.getValue());
        return resTL;
    }

    public TrafficLight findById(int id) {
        TrafficLight resTL = null;
        for (TrafficLight tl : getTrafficLightCollection()) {
            if (tl.getId() == id) {
                resTL = tl;
                break;
            }
        }     
        return resTL;
    }
}
