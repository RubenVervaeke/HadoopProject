/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.service;

import fi.karelia.publicservices.bll.SensorReadingBLL;
import fi.karelia.publicservices.domain.SensorReading;
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
@Path("/sensorreading")
public class SensorService {

    private final SensorReadingBLL sBLL;

    public SensorService() {
        sBLL = new SensorReadingBLL();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        SensorReading s = null;
        try {
            s = sBLL.findReadingById(id);
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(s).build();
    }
    
    @GET
    @Path("/sensor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBySensorId(@PathParam("id") String id) {
        List <SensorReading> s = null;
        try {
            s = sBLL.findReadingsBySensorId(id);
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(s).build();
    }
    
     @GET
    @Path("/timestamp/{timestamp}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByTimestamp(@PathParam("timestamp") Long timestamp) {
        List <SensorReading> s = null;
        try {
            s = sBLL.findReadingsByTimestamp(timestamp);
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(s).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        SensorReadingBLL myBLL = new SensorReadingBLL();
        List<SensorReading> list = null;
        try {
            list = sBLL.getAllSensorReadings();
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(list).build();
    }
}
