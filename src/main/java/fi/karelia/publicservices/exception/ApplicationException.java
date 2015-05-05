/*
 * ApplicationException.java
 *
 * Created on 29 maart 2005, 14:02
 */
package fi.karelia.publicservices.exception;

/**
 *
 * @author Katrien
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String s) {
        super(s);
    }
}
