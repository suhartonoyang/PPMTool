/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.PPMToolFullStack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author 51974
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException{

    public ProjectIdException(String message) {
        super(message);
    }
    
}
