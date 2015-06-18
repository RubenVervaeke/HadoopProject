/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.util;

import fi.karelia.publicservices.data.DataScheduler;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 *
 * @author hadoop
 */
public class PublicServicesEventListener implements ApplicationEventListener {

    private volatile int requestCount = 0;

    @Override
    public void onEvent(ApplicationEvent ae) {
        switch (ae.getType()) {
            case INITIALIZATION_FINISHED:
                System.out.println("Application " + ae.getResourceConfig().getApplicationName() + " was initialized.");
                // Start the data scheduler
                
                break;
            case DESTROY_FINISHED:
                System.out.println("Application " + ae.getResourceConfig().getApplicationName() + " destroyed.");
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent re) {
        requestCount++;
        System.out.println("Request " + requestCount + " started.");
        // Return the listener instance that will handle this request.
        return new PublicServicesRequestEventListener(requestCount);
    }
}
