/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.exception;

/**
 *
 * @author Ruben
 */
public class DBException extends Exception {
    public DBException() {
        super();
    }

    public DBException(String s) {
        super(s);
    }
}
