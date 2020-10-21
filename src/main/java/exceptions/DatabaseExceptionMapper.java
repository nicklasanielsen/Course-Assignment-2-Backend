/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ExceptionDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nicklas Nielsen
 */
@Provider
public class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(DatabaseException exception) {
        Logger.getLogger(DatabaseException.class.getName()).log(Level.SEVERE, null, exception);
        ExceptionDTO exceptionDTO = new ExceptionDTO(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(GSON.toJson(exceptionDTO))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}