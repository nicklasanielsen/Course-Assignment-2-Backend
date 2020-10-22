package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.City;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import entities.PhoneType;
import exceptions.DatabaseException;
import exceptions.FixedDataNotFoundException;
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static utils.ConvertDTO.convertToDTO;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonFacadeTest {
    
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person person;
    private static PersonDTO personDTO;
    private static Address address;
    private static Phone phone;
    private static Hobby hobby;
    
    public PersonFacadeTest() {
        
    }
    
    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
    
    @BeforeEach
    public void setUp() throws FixedDataNotFoundException {
        EntityManager em = emf.createEntityManager();

        person = new Person("Test", "Testersen", "contact@test.test");
        address = new Address("Skovvej", 82, "", new City(2000, "Frederiksberg"));
        phone = new Phone(13371337, PhoneTypeFacade.getPhoneTypeFacade(emf).getPhoneType("Privat"));
        hobby = new Hobby("Test name", "Test description");

        try {
            em.getTransaction().begin();
            em.persist(person);
            person.setAddress(address);
            person.addPhone(phone);
            person.addHobby(hobby);
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        personDTO = (PersonDTO) convertToDTO(person);
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        person = null;
        personDTO = null;
        address = null;
        phone = null;
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Test
    public void createPerson() throws MissingInputException, InvalidInputException, DatabaseException, FixedDataNotFoundException{
        // Arrange
        PersonDTO expected = personDTO;
        
        // Act
        PersonDTO actual = facade.createPerson(expected);
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void getPersonsByPhone_existingPersons() throws InvalidInputException{
        // Arrange
        List<PersonDTO> expected = new ArrayList<>();
        expected.add(personDTO);
        int phoneNumber = 13371337;
        
        // Act
        List<PersonDTO> actual = facade.getPersonsByPhone(phoneNumber);
        
        // Assert
        assertTrue(actual.containsAll(expected));
    }
    
    @Test
    public void getPersonsByPhone_noExistingPersons() throws InvalidInputException{
        // Arrange
        int phoneNumber = 0;
        
        // Act
        List<PersonDTO> actual = facade.getPersonsByPhone(phoneNumber);
        
        // Assert
        assertTrue(actual.isEmpty());
    }
}
