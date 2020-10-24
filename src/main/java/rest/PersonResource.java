package rest;

import dtos.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.DatabaseException;
import exceptions.FixedDataNotFoundException;
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import facades.CityFacade;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    private static final CityFacade FACADE2 = CityFacade.getCityFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("phone/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByPhone(@PathParam("number") int number) throws InvalidInputException {
        return Response.ok(FACADE.getPersonsByPhone(number)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(String person) throws MissingInputException, InvalidInputException, DatabaseException, FixedDataNotFoundException {
        PersonDTO incomingData = GSON.fromJson(person, PersonDTO.class);
        PersonDTO personAdded = FACADE.createPerson(incomingData);
        return Response.ok(personAdded).build();
    }

    @PUT
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPerson(@PathParam("id") long id, String person) throws MissingInputException, InvalidInputException, FixedDataNotFoundException, DatabaseException {
        PersonDTO incomingData = GSON.fromJson(person, PersonDTO.class);
        PersonDTO personEdited = FACADE.editPerson(id, incomingData);
        return Response.ok(personEdited).build();
    }
    
    @GET
    @Path("hobby/{hobbyName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByHobby(@PathParam("hobbyName") String hobbyName){
        return Response.ok(FACADE.getPersonsByHobby(hobbyName)).build();
    }
    
    @GET
    @Path("city/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByCity(@PathParam("city") String city){
        return Response.ok(FACADE.getPersonsByCity(city)).build();
    }
    
    @GET
    @Path("zip/{zip}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByZip(@PathParam("zip") int zip){
        return Response.ok(FACADE.getPersonsByZip(zip)).build();
    }
    
    @GET
    @Path("city/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllZips(){
        return Response.ok(FACADE2.getAllZips()).build();
    }
    
    @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") int id){
        return Response.ok(FACADE.getPersonsById(id)).build();
    }
}