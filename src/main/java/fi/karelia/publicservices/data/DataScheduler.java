/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author hadoop
 */
public class DataScheduler {

    private static volatile DataScheduler dataScheduler = null;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20);
    private final Runnable mainTask;
    private ScheduledFuture<?> mainTaskHandle;

    private Map<String, Long> modificationTimestamps;

    private DataScheduler() {
        mainTask = new Runnable() {
            @Override
            public void run() {
                // Fetch modified xml file data
            }
        };
    }

    public static DataScheduler getInstance() {
        if (dataScheduler == null) {
            synchronized (DataScheduler.class) {
                if (dataScheduler == null) {
                    dataScheduler = new DataScheduler();
                }
            }
        }
        return dataScheduler;
    }

    public void initialize() {
        // Start the main task to pull all metadata from xml config files
        mainTaskHandle = scheduler.scheduleAtFixedRate(mainTask, 30000, 86400000, TimeUnit.MILLISECONDS);
    }
}
