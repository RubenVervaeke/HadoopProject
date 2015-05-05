/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.util;

import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 *
 * @author hadoop
 */
public class PublicServicesRequestEventListener implements RequestEventListener {

    private final int requestNumber;
    private final long startTime;
    
    public PublicServicesRequestEventListener(int requestNumber) {
        this.requestNumber = requestNumber;
        startTime = System.currentTimeMillis();
    }
    
    @Override
    public void onEvent(RequestEvent re) {
        switch (re.getType()) {
            case RESOURCE_METHOD_START:
                System.out.println("Resource method " + re.getUriInfo().getMatchedResourceMethod().getHttpMethod() + " started for request " + requestNumber);
                break;
            case FINISHED: 
                System.out.println("Request " + requestNumber + " finished. Processing time " + (System.currentTimeMillis() - startTime) + " ms.");
                break;
        }
    }
}
