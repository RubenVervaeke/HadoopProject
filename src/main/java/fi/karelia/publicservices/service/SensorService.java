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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final SensorReadingBLL sBLL;

    public SensorService() {
        sBLL = new SensorReadingBLL();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        List<SensorReading> s = null;
        try {
            s = sBLL.findReadingsById(id);
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(s).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        SensorReadingBLL myBLL = new SensorReadingBLL();
        try{
            myBLL.getAllSensorReadings();
        } catch (ApplicationException ex){}
        List<SensorReading> list = null;
        try {
            list = sBLL.getAllSensorReadings();
        } catch (ApplicationException ex) {
            return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok(list).build();
    }
}
