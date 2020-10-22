package facades;

import entities.PhoneType;
import exceptions.FixedDataNotFoundException;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class PhoneTypeFacadeTest {

    private static EntityManagerFactory emf;
    private static PhoneTypeFacade facade;
    private static PhoneType phoneType;

    public PhoneTypeFacadeTest() {
        
    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PhoneTypeFacade.getPhoneTypeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setUp() {
        phoneType = new PhoneType("Privat");
    }

    @AfterEach
    public void tearDown() {
        phoneType = null;
    }

    @Test
    public void getPhoneType_existingPhoneType() throws FixedDataNotFoundException {
        // Arrange
        PhoneType expected = phoneType;

        // Act
        PhoneType actual = facade.getPhoneType(expected.getType());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getPhoneType_notExistingPhoneType() {

        assertThrows(FixedDataNotFoundException.class, () -> {
            facade.getPhoneType("");
        });
    }

}
