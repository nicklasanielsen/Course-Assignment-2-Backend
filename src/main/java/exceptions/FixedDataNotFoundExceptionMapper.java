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
public class FixedDataNotFoundExceptionMapper implements ExceptionMapper<FixedDataNotFoundException> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(FixedDataNotFoundException exception) {
        Logger.getLogger(FixedDataNotFoundException.class.getName()).log(Level.SEVERE, null, exception);
        ExceptionDTO exceptionDTO = new ExceptionDTO(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(GSON.toJson(exceptionDTO))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}