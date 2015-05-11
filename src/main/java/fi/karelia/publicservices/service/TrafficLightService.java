/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.service;

import fi.karelia.publicservices.bll.TrafficLightBLL;
import fi.karelia.publicservices.domain.TrafficLight;
import fi.karelia.publicservices.exception.ApplicationException;
import fi.karelia.publicservices.exception.DBException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Ruben
 */
@Path("/trafficlight")
public class TrafficLightService {

    private final TrafficLightBLL tlBLL;

    public TrafficLightService() {
        tlBLL = new TrafficLightBLL();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") int id) {
        TrafficLight tl = null;
        try {
            tl = tlBLL.findById(id);
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(tl).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<TrafficLight> list = tlBLL.getAll();
        return Response.ok(list).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TrafficLight entity) {
        TrafficLight resTL = tlBLL.add(entity);
        return Response.status(Status.CREATED).entity(resTL).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") int id, TrafficLight tl) {
        TrafficLight resTL = null;
        try {
            resTL = tlBLL.update(tl);
        } catch (ApplicationException | DBException ex) {
            return Response.status(Status.NOT_MODIFIED).entity(ex.getMessage()).build();
        }
        return Response.status(Status.OK).entity(resTL).build();
    }
}
