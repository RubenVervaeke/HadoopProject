/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.util;

import fi.karelia.publicservices.data.domain.City;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import fi.karelia.publicservices.data.domain.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class XMLReader {
    
    private static volatile XMLReader reader = null;
    
    private XMLReader() {}
    
    public static XMLReader getInstance() {
        if (reader == null) {
            synchronized(XMLReader.class) {
                if (reader == null) {
                    reader = new XMLReader();
                }
            }
        }
        return reader;
    }

    public List<City> getAllCities()
    {
        List<City> cities = new LinkedList();
        try {
            File fXmlFile = new File("ConfigFiles/cities.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("city");
            //cities = new String[nList.getLength()];
            
            
            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    City tempCity = new City();
                    tempCity.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    addServicesToCity(tempCity);
                    cities.add(tempCity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }
    
    private City addServicesToCity(City pCity){
        try {
            // Open the desired XML file and load it into an XML object.
            File fXmlFile = new File("ConfigFiles/" + pCity.getName() + "Services.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            doc.getDocumentElement().normalize();
            
            // Contain the services of a city in a list.
            List<Service> cityServices = new ArrayList();
            
            // Store all the service nodes.
            NodeList nList = doc.getElementsByTagName("service");
            
            //for each service node.
            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                // Make a new serviceobject.
                Service tmpService = new Service();
                List<Resource> rscList = new ArrayList<>();
                // Get the node of the service.
                Node nNode = nList.item(temp);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    // Get the element of the node.
                    Element eElement = (Element) nNode;
                    
                    // Get all resource tags under a service.
                    NodeList resourceList = eElement.getElementsByTagName("resource");
                    
                    // Loop over every resource for a service.
                    for(int temp2 = 0; temp2 < resourceList.getLength(); temp2++)
                    {
                        Resource tmpResource = new Resource();
                        Node tempNode = resourceList.item(temp2);
                        if(tempNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element tempElement = (Element) tempNode;
                            tmpResource.setId(Integer.valueOf(tempElement.getElementsByTagName("id").item(0).getTextContent()));
                            tmpResource.setName(tempElement.getElementsByTagName("name").item(0).getTextContent());
                            tmpResource.setUrl(tempElement.getElementsByTagName("datalocation").item(0).getTextContent());
                            tmpResource.setSchedulingType(SchedulingType.valueOf(tempElement.getElementsByTagName("type").item(0).getTextContent()));
                            tmpResource.setSchedulingInterval(Long.valueOf(tempElement.getElementsByTagName("interval").item(0).getTextContent()));     
                        }
                        rscList.add(tmpResource);
                    }
                    tmpService.setResources(rscList);                    
                }
                cityServices.add(tmpService);
            }
            pCity.setServices(cityServices);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pCity;
    }
}
