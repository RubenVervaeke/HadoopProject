/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.domain;

/**
 *
 * @author hadoop
 */
public class VehicleDetectionReading extends SensorReading {

    private boolean detected;
    
    @Override
    public Object getValue() {
        return detected;
    }

    @Override
    public void setValue(Object value) {
        detected = (boolean) value;
    }
}
