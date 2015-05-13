/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.data.domain.City;
import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.data.domain.Service;
import fi.karelia.publicservices.exception.HadoopException;
import fi.karelia.publicservices.util.XMLReader;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadoop
 */
public class DataScheduler {

    private static volatile DataScheduler dataScheduler = null;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ScheduledThreadPoolExecutor dataExecutor = new ScheduledThreadPoolExecutor(1000);
    private final Runnable mainTask;
    private ScheduledFuture<?> mainTaskHandle;

    private Map<String, Long> modificationTimestamps;

    private DataScheduler() {
        mainTask = new Runnable() {
            @Override
            public void run() {
                // Fetch modified xml file data
                XMLReader r = XMLReader.getInstance();
                for (String city :r.getCityNames()) {
                    try {
                        updateCitySchedule(r.getCity(city));
                    } catch (HadoopException ex) {
                        Logger.getLogger(DataScheduler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
    
    public void updateCitySchedule(City c) throws HadoopException {
        for (Service s: c.getServices()) {
            for (Resource r: s.getResources()) {
                boolean present = false;
                for (Runnable task: dataExecutor.getQueue()) {
                    DataPullTask dpt = (DataPullTask) task;
                    if (dpt == null) {
                        throw new HadoopException("Wrong task type assigned");
                    }
                    if (dpt.getResource().getId() == r.getId()) {
                        present = true;
                    }
                }
                
                if (!present) {
                    // Add new task to the pool
                    DataPullTask dpt = new DataPullTask(r);
                    dataExecutor.scheduleAtFixedRate(dpt, 0, r.getSchedulingInterval(), TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
