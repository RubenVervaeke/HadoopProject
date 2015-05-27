/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.service;

import fi.karelia.publicservices.bll.SensorBLL;
import fi.karelia.publicservices.domain.Sensor;
import fi.karelia.publicservices.exception.ApplicationException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Jonas
 */
@Path("/sensor")
public class SensorService {
    
    private final SensorBLL sBLL;

    public SensorService() {
        sBLL = new SensorBLL();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") int id) {
        Sensor s = null;
        try {
            s = sBLL.findById(id);
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(s).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Sensor> list = sBLL.getAll();
        return Response.ok(list).build();
    }
}
