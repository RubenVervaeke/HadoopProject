/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.data.domain.SchedulingType;
import fi.karelia.publicservices.exception.ApplicationException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadoop
 */
public class DataScheduledTask implements RunnableScheduledFuture {

    private Resource resource;
    private DataPuller puller;
    private boolean running;
    private boolean cancelled;
    private long startTime;
    
    public DataScheduledTask(Resource resource) {
        this.resource = resource;
        cancelled = false;
        running = false;
    }
    
    @Override
    public boolean isPeriodic() {
        return getResource().getSchedulingType() != SchedulingType.INITIAL;
    }

    @Override
    public void run() {
        try {
            if (puller == null) {
                puller = new DataPuller();
            }
            
            running = true;
            startTime = System.currentTimeMillis();
            System.out.println("Start running task with resource id: " + resource.getId());          
            puller.pull(getResource());
            running = false;
        } catch (IOException | ApplicationException ex) {
            Logger.getLogger(DataScheduledTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (puller.isProcessed()) {
            cancelled = true;
        }
        return cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean isDone() {
        return !running;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    } 

    /**
     * @return the resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @param resource the resource to set
     */
    public void setResource(Resource resource) {
        this.resource = resource;
        
    }
}
