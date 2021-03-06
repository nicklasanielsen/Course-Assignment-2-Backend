package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Nicklas Nielsen
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cors.CorsFilter.class);
        resources.add(exceptions.DatabaseExceptionMapper.class);
        resources.add(exceptions.FixedDataNotFoundExceptionMapper.class);
        resources.add(exceptions.GenericExceptionMapper.class);
        resources.add(exceptions.InvalidInputExceptionMapper.class);
        resources.add(exceptions.MissingInputExceptionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(rest.PersonResource.class);
    }
    
}
