/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data;

import fi.karelia.publicservices.data.domain.Resource;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Ruben
 */
public class DataPuller {
    
    private ResponseHandler<String> responseHandler;
    private boolean processed;
    
    public DataPuller() {
        processed = true;
    }
    
    /**
    * The pull method which pulls a specific resource.
    * @param resource The specific resource to pull.
    */
    public void pull(Resource resource) throws IOException {
        processed = false;
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final String url = resource.getUrl();
        
        try {
            HttpGet httpGet = new HttpGet(url);
            System.out.println("Executing request " + httpGet.getRequestLine());
            
            responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse hr) throws ClientProtocolException, IOException {
                    int status = hr.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = hr.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected reponse for resource pull with url: " + url + ". Got response code: " + status);
                    }
                }
            };
           String responseBody = httpClient.execute(httpGet, responseHandler);
            System.out.println(responseBody);
        } finally {
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
