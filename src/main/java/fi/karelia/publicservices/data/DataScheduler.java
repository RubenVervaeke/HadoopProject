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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.TimeStamp;

/**
 *
 * @author hadoop
 */
public class DataScheduler {

    private static volatile DataScheduler dataScheduler = null;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final ScheduledThreadPoolExecutor dataExecutor = new ScheduledThreadPoolExecutor(1000);

    private final Runnable mainTask;

    private Map<String, Long> modificationTimestamps;

    private DataScheduler() {
        mainTask = new Runnable() {
            @Override
            public void run() {
                // Fetch xml file data
                XMLReader r = XMLReader.getInstance();
                for (City city : r.getAllCities()) {
                    try {
                        // Update modified resources
                        updateModifiedResources(city);
                    } catch (HadoopException ex) {
                        Logger.getLogger(DataScheduler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // Reschedule modified resources
                rescheduleModifiedResources();
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
        executorService.scheduleAtFixedRate(mainTask, 30000, 86400000, TimeUnit.MILLISECONDS);
    }

    public void updateModifiedResources(City c) throws HadoopException {
        // Check if city file has been modified since its last update
        File f = new File(c.getFileName());

        // Check if the file exists
        if (f == null) {
            throw new HadoopException("Error while trying to update city schedule for city '" + c.getName() + "': File not found: " + c.getFileName());
        }

        // Check if our array of lastModified dates contains the city name,
        // if not we know this is a new added city
        if (!modificationTimestamps.containsKey(c.getName())) {
            // Add the new last modified date for this city file
            modificationTimestamps.put(c.getName(), f.lastModified());
        }

        // Check if the lastModified date exceeds the last stored lastModified date
        if (modificationTimestamps.get(c.getName()) > f.lastModified()) {
            // If the file wasn't changed since last time, update nothing
            return;
        }

        // Loop through all resources to check if they are modified or added
        for (Service s : c.getServices()) {
            for (Resource r : s.getResources()) {
                boolean present = false;
                // Loop through our scheduled resources to check if the current one
                // is running
                for (Runnable task : dataExecutor.getQueue()) {
                    DataPullTask dpt = (DataPullTask) task;
                    if (dpt == null) {
                        throw new HadoopException("Wrong task type assigned");
                    }
                    if (dpt.getResource().getId() == r.getId()) {
                        // Update the resource on the DataPullTask
                        dpt.setResource(r);
                        present = true;
                        break;
                    }
                }

                // Check if the resource is a new resource
                if (!present) {
                    // Add new task to the pool
                    DataPullTask dpt = new DataPullTask(r);
                    dataExecutor.scheduleAtFixedRate(dpt, 0, r.getSchedulingInterval(), TimeUnit.MILLISECONDS);
                }
            }
        }

        // Loop through all scheduled resources to check if there are deleted resources
        for (Runnable task : dataExecutor.getQueue()) {
            DataPullTask dpt = (DataPullTask) task;
            boolean deleted = true;
            for (Service s : c.getServices()) {
                for (Resource r : s.getResources()) {
                    if (r.getId() == dpt.getResource().getId()) {
                        deleted = false;
                    }
                }
            }
            
            // If the resource is no longer present in the XML config files,
            // cancel the scheduled resource
            if (deleted) {
                dpt.cancel(true);
            }
        }
    }

    private void rescheduleModifiedResources() {

    }
    
    
}
