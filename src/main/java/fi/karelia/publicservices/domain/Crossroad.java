/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.domain;

import fi.karelia.publicservices.domain.util.Coordinate;
import java.util.List;

/**
 *
 * @author hadoop
 */
public class Crossroad {
    
    private int id;
    private Coordinate location;
    private List<Crossing> crossings;
    private List<TrafficLight> trafficLights;
}
