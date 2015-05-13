/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.exception;

/**
 *
 * @author hadoop
 */
public class HadoopException extends Exception {
    public HadoopException() {
        super();
    }
    
    public HadoopException(String s) {
        super(s);
    }
}
