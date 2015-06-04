/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.driver;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.exception.HadoopException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 *
 * @author ruben
 */
public class DriverFactory {
    
    private DriverFactory() {
        
    }
    
    public static Driver getDriver(Resource r) throws HadoopException {
        try {
            Constructor cons = r.getDriverType().getConstructor();
            Driver d = (Driver) cons.newInstance();
            return d;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new HadoopException("Error getting driver for resource: " + r.getName() + "Reason: " + ex.getMessage());
        }            
    }
}
