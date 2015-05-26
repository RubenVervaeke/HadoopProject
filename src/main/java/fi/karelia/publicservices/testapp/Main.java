/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.testapp;

import fi.karelia.publicservices.data.domain.City;
import fi.karelia.publicservices.util.XMLReader;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

 //Class that has nothing but a main.
 //Does a Put, Get and a Scan against an hbase table.
 //The API described here is since HBase 1.0.
public class Main {
  public static void main(String[] args) throws IOException {
      XMLReader myReader = XMLReader.getInstance();
      List<City> myList = myReader.getAllCities();
      int i = 2;
  }
}
