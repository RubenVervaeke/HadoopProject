/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.testapp;
 //Does a Put, Get and a Scan against an hbase table.
 //The API described here is since HBase 1.0
import fi.karelia.publicservices.bll.SensorReadingBLL;
import fi.karelia.publicservices.domain.SensorReading;
import fi.karelia.publicservices.exception.ApplicationException;
import java.util.List;


public class Main {
  public static void main(String[] args) throws ApplicationException {
      SensorReadingBLL myBLL = new SensorReadingBLL();
      Long myLong = Long.valueOf("2015030201");
      List<SensorReading> myReading;
      myReading = myBLL.findReadingsByTimestamp(myLong);
      int i = 1+2;
  }}