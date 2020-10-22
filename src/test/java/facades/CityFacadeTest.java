package facades;

import entities.City;
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
public class CityFacadeTest {

    private static EntityManagerFactory emf;
    private static CityFacade facade;
    private static City city;

    public CityFacadeTest() {

    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CityFacade.getCityFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setUp() {
        city = new City(2000, "Frederiksberg");
    }

    @AfterEach
    public void tearDown() {
        city = null;
    }

    @Test
    public void getCity_existingCity() throws FixedDataNotFoundException {
        // Arrange
        City expected = city;

        // Act
        City actual = facade.getCity(expected.getZip(), expected.getCityName());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCity_invalidZipCode() throws FixedDataNotFoundException {
        // Arrange
        City expected = city;

        // Act
        City actual = facade.getCity(0, expected.getCityName());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCity_invalidCityName() throws FixedDataNotFoundException {
        // Arrange
        City expected = city;

        // Act
        City actual = facade.getCity(expected.getZip(), "");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCity_notExistingCity() {

        assertThrows(FixedDataNotFoundException.class, () -> {
            facade.getCity(0, "");
        });
    }

}
