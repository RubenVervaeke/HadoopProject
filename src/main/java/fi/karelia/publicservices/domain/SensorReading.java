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
public abstract class SensorReading {
    
    private Long timestamp;
    
    public abstract Object getValue();
    public abstract void setValue(Object value);
}
