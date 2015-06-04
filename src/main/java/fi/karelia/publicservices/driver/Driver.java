/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.karelia.publicservices.driver;

import fi.karelia.publicservices.data.domain.Resource;
import fi.karelia.publicservices.exception.HadoopException;

/**
 *
 * @author ruben
 */
public interface Driver {
    
    void execute(Resource r) throws HadoopException;
}
