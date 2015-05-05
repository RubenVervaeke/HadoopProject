/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.util;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Ruben
 */
public class PublicServices extends ResourceConfig {
    
    public PublicServices() {
        // Define the packages to look for rest resources
        packages("fi.karelia.publicservices.service");
        
        // Register additional modules
        register(ObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .register(PublicServicesEventListener.class);
    }
}
