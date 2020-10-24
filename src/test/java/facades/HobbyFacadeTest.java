package facades;

import entities.Hobby;
import javax.persistence.EntityManager;
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
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    private static Hobby hobby;

    public HobbyFacadeTest() {

    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        hobby = new Hobby("Test name", "Test description");

        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        hobby = null;

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getHobby_existingHobby() {
        // Arrange
        Hobby expected = hobby;

        // Act
        Hobby actual = facade.getHobby(expected.getHobbyName(), expected.getHobbyDescription());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getHobby_existingHobbyName() {
        // Arrange
        Hobby expected = hobby;

        // Act
        Hobby actual = facade.getHobby(expected.getHobbyName(), "");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getHobby_notExistingHobby() {
        // Act
        Hobby actual = facade.getHobby("Tester", "Testing");

        // Assert
        assertTrue(actual.getId() == 0);
    }

}
