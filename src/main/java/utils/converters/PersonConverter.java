package utils.converters;

import dtos.PersonDTO;
import entities.Person;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonConverter implements Converter {

    private static PersonConverter instance;
    private static EntityManagerFactory emf;

    private PersonConverter() {
        // Private constructor to ensure Singleton
    }

    public static Converter getConverter(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonConverter();
        }

        return instance;
    }

    @Override
    public Object toDTO(Object object) {
        Person entity = castToEntity(object);
        return new PersonDTO(entity);
    }

    @Override
    public Object fromDTO(Object object) {
        PersonDTO dto = castToDTO(object);
        String firstname, lastname, email;

        firstname = dto.getFullName().split(" ")[0].trim();
        lastname = dto.getFullName().split(" ")[1].trim();
        email = dto.getEmail().trim();

        return new Person(firstname, lastname, email);
    }

    private Person castToEntity(Object entity) {
        return (Person) entity;
    }

    private PersonDTO castToDTO(Object dto) {
        return (PersonDTO) dto;
    }

}
