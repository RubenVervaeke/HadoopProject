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
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

        HttpClient httpClient = HttpClients.createDefault();
        final String url = resource.getUrl();

        try {
            HttpGet httpGet = new HttpGet("http://" + url);
            System.out.println("Executing request " + httpGet.getRequestLine());

            // Execute the HTTP request
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = new BufferedHttpEntity(response.getEntity());
            System.out.println("Request executed");

            // Write the file to HDFS
            System.out.println("Writing response to HDFS");
            HadoopContentManager.writeToHDFS(resource, entity);

            // Load the driver for the resource
            System.out.println("Loading Driver for resource");
            Driver driver = DriverFactory.getDriver(resource);

            // Execute the driver
            System.out.println("Executing driver");
            driver.execute(resource);

        } catch (HadoopException ex) {
            throw new ApplicationException("Error pulling data from resource: " + resource.getName() + ". Reason: " + ex.getMessage());
        } finally {
            // Close the HTTP client
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
