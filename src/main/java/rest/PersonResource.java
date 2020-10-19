package rest;

import dtos.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
@Path("person")
public class PersonResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("phone/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByPhone(@PathParam("number") int number){
        return Response.ok(FACADE.getPersonsByPhone(number)).build();
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(String person){
        PersonDTO incomingData = GSON.fromJson(person, PersonDTO.class);
        PersonDTO personAdded = FACADE.createPerson(incomingData);
        return Response.ok(personAdded).build();
    }
    
}
