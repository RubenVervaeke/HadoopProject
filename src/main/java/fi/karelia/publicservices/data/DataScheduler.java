/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * @author hadoop
 */
public class DataScheduler {
    
    private static volatile DataScheduler dataScheduler = null;
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20);
    // private final Runnable mainTask;
    
    private Map<String, Long> modificationTimestamps;
    
    private DataScheduler () {}
    
    public static DataScheduler getInstance() {
        if (dataScheduler == null) {
            synchronized(DataScheduler.class) {
                if (dataScheduler == null) {
                    dataScheduler = new DataScheduler();
                }
            }
        }
        return dataScheduler;
    }
    
    public void initialize() {
        
        // Start the main task to pull all metadata from xml config files
        
    }   
}
