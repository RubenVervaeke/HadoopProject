/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.domain;

import java.util.List;

/**
 *
 * @author hadoop
 */
public abstract class Sensor {
    
    private int id;
    protected List<SensorReading> readings;
    
    public abstract List<SensorReading> getReadings();
    public abstract void setReadings(List<SensorReading> readings);
    public abstract void addReading(SensorReading reading);
}
