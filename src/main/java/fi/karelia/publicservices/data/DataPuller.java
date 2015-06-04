/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.driver.Driver;
import fi.karelia.publicservices.driver.DriverFactory;
import fi.karelia.publicservices.exception.ApplicationException;
import fi.karelia.publicservices.exception.HadoopException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Ruben
 */
public class DataPuller {

    private ResponseHandler<HttpEntity> responseHandler = new ResponseHandler<HttpEntity>() {
        @Override
        public HttpEntity handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
            // Check the status code of the response
            StatusLine sl = hr.getStatusLine();
            if (sl.getStatusCode() >= 300) {
                throw new HttpResponseException(sl.getStatusCode(), sl.getReasonPhrase());
            }
            
            // Check if the response contains a body
            HttpEntity entity = hr.getEntity();
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
         
            return entity;
        }
    };

    private boolean processed;

    public DataPuller() {
        processed = true;
    }

    /**
     * The pull method which pulls a specific resource.
     *
     * @param resource The specific resource to pull.
     * @throws java.io.IOException
     * @throws fi.karelia.publicservices.exception.ApplicationException
     */
    public void pull(Resource resource) throws IOException, ApplicationException {
        processed = false;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        final String url = resource.getUrl();

        try {
            HttpGet httpGet = new HttpGet(url);
            System.out.println("Executing request " + httpGet.getRequestLine());
            
            // Execute the HTTP request
            HttpEntity entity = httpClient.execute(httpGet, responseHandler);
            
            // Convert the body of the response
            InputStream in = HttpContentManager.getInputStream(entity);
            
            // Write the file to HDFS
            HadoopContentManager.writeToHDFS(resource, in);
            
            // Load the driver for the resource
            Driver driver = DriverFactory.getDriver(resource);
            
            // Execute the driver
            driver.execute(resource);
            
        } catch (HadoopException ex) {
            throw new ApplicationException("Error pulling data from resource: " + resource.getName() + ". Reason: " + ex.getMessage());
        } finally {
            // Close the HTTP client
            httpClient.close();
        }

        processed = true;
    }

    /**
     * @return the processed
     */
    public boolean isProcessed() {
        return processed;
    }
}
