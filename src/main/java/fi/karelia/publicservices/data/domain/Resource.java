/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.data.domain;

/**
 *
 * @author hadoop
 */
public class Resource {
    
    private int id;
    private String name;
    private String url;
    private SchedulingType schedulingType;
    private Long schedulingInterval;
    private String driverJarName;
    
    public Resource() {
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the schedulingType
     */
    public SchedulingType getSchedulingType() {
        return schedulingType;
    }

    /**
     * @param schedulingType the schedulingType to set
     */
    public void setSchedulingType(SchedulingType schedulingType) {
        this.schedulingType = schedulingType;
    }

    /**
     * @return the schedulingInterval
     */
    public Long getSchedulingInterval() {
        return schedulingInterval;
    }

    /**
     * @param schedulingInterval the schedulingInterval to set
     */
    public void setSchedulingInterval(Long schedulingInterval) {
        this.schedulingInterval = schedulingInterval;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the driverJarName
     */
    public String getDriverJarName() {
        return driverJarName;
    }

    /**
     * @param driverJarName the driverJarName to set
     */
    public void setDriverJarName(String driverJarName) {
        this.driverJarName = driverJarName;
    }
}
