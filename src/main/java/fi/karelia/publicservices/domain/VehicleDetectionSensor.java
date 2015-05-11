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
public class VehicleDetectionSensor extends Sensor {
    
    @Override
    public List<SensorReading> getReadings() {
        return readings;
    }

    @Override
    public void setReadings(List<SensorReading> readings) {
        this.readings = readings;
    }

    @Override
    public void addReading(SensorReading reading) {
        readings.add(reading);
    }
}
