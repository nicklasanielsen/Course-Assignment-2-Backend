package utils.converters;

import dtos.PersonDTO;
import entities.Person;
import exceptions.FixedDataNotFoundException;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
@Disabled
public class PersonConverterTest {

    private static EntityManagerFactory emf;
    private static Converter converter;
    private static Person person;

    public PersonConverterTest() {

    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        converter = PersonConverter.getConverter(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setup() throws FixedDataNotFoundException {
        person = new Person("TestMand", "Tester", "valid@email.test");
    }

    @AfterEach
    public void tearDown() {
        person = null;
    }

    @Test
    public void toDTO() {
        // Arrange
        Person objectToConvert = person;
        PersonDTO expected = new PersonDTO(objectToConvert);

        // Act
        PersonDTO actual = (PersonDTO) converter.toDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void fromDTO() throws FixedDataNotFoundException {
        // Arrange
        Person expected = person;
        PersonDTO objectToConvert = new PersonDTO(person);

        // Act
        Person actual = (Person) converter.fromDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }

}
