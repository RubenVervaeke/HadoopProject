/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.bll;

import fi.karelia.publicservices.dao.TrafficLightDAO;
import fi.karelia.publicservices.domain.TrafficLight;
import fi.karelia.publicservices.exception.ApplicationException;
import fi.karelia.publicservices.exception.DBException;
import java.util.List;

/**
 *
 * @author Ruben
 */
public class TrafficLightBLL {

    private TrafficLightDAO tlDAO;

    public TrafficLight add(TrafficLight tl) throws ApplicationException, DBException {
        tlDAO = new TrafficLightDAO();
        // Check if trafficlight already exists
        if (tlDAO.findById(tl.getId()) != null) {
            throw new ApplicationException("Id already exists for id: " + tl.getId());
        }
        return tlDAO.add(tl);
    }

    public List<TrafficLight> getAll() {
        tlDAO = new TrafficLightDAO();
        // Get all traffic lights
        return tlDAO.getAll();
    }

    public void remove(int id) throws ApplicationException, DBException {
        tlDAO = new TrafficLightDAO();
        // Check if trafficlight with id exists
        if (tlDAO.findById(id) == null) {
            throw new ApplicationException("Trafficlight not present for id: " + id);
        }
        // Remove trafficlight
        tlDAO.remove(id);
    }

    public TrafficLight update(TrafficLight tl) throws ApplicationException, DBException {
        tlDAO = new TrafficLightDAO();
        // Check if trafficlight with id exists
        if (tlDAO.findById(tl.getId()) == null) {
            throw new ApplicationException("Trafficlight not present for id: " + tl.getId());
        }
        // Update trafficlight
        return tlDAO.update(tl);
    }

    public TrafficLight findById(int id) throws ApplicationException {
        tlDAO = new TrafficLightDAO();
        // Find trafficlight
        TrafficLight tl = tlDAO.findById(id);
        if (tl == null) {
            throw new ApplicationException("Trafficlight doesn't exist for id: " + id);
        }
        return tl;
    }
}
