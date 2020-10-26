package facades;

import entities.Address;
import entities.City;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class AddressFacadeTest {

    private static EntityManagerFactory emf;
    private static AddressFacade facade;
    private static Address address;
    private static City city;

    public AddressFacadeTest() {

    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AddressFacade.getAddressFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        city = new City(2000, "Frederiksberg");
        address = new Address("Skovvej", 82, "", city);

        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        address = null;
        city = null;

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getAddress_existingAddress() {
        // Arrange
        Address expected = address;

        // Act
        Address actual = facade.getAddress(expected.getStreet(),
                expected.getHouseNumber(), expected.getFloor(), expected.getCity());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getAddress_newAddress() {
        // Act
        Address actual = facade.getAddress(address.getStreet(),
                address.getHouseNumber(), address.getFloor(), address.getCity());

        // Assert
        assertNotNull(actual.getId());
    }
}
