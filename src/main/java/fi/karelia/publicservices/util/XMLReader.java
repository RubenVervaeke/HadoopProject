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

/**
 *
 * @author Jonas
 */
public class XMLReader {

    private static volatile XMLReader reader = null;

    private XMLReader() {
    }

    public static XMLReader getInstance() {
        if (reader == null) {
            synchronized (XMLReader.class) {
                if (reader == null) {
                    reader = new XMLReader();
                }
            }
        }
        return reader;
    }
    //public static void main(String argv[]) {
//        try {
//            File fXmlFile = new File("/Users/Jonas/Dropbox/KATHO/Derde Jaar/FINLAND/Thesis/XML files/cities.xml");
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(fXmlFile);
//            
//            doc.getDocumentElement().normalize();
//            
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//            
//            NodeList nList = doc.getElementsByTagName("city");
//            System.out.println("--------------------------");
//            
//            for (int temp = 0; temp < nList.getLength(); temp++)
//            {
//                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
//                if (nNode.getNodeType() == Node.ELEMENT_NODE)
//                {
//                    Element eElement = (Element) nNode;
//                    System.out.println("\n" + eElement.getElementsByTagName("name").item(0).getTextContent());
//                    System.out.println("\n" + eElement.getElementsByTagName("file").item(0).getTextContent());
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try{
//            
//            
//        } catch(Exception e){
//            
//        }
    // }
    public String[] getCityFiles() {
        return new String[10];
    }

    public City getCity(String pFileName) {
        return new City();
    }
}
