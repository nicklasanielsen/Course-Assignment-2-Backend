package utils.converters;

import dtos.PersonDTO;
import entities.Person;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonConverter implements Converter {

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
