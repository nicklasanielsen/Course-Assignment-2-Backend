package utils.converters;

import dtos.AddressDTO;
import entities.Address;
import entities.City;
import exceptions.FixedDataNotFoundException;
import facades.CityFacade;
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
@Disabled
public class AddressConverterTest {

    private static EntityManagerFactory emf;
    private static Converter converter;
    private static City city;
    private static Address address;
    private static CityFacade cityFacade;

    public AddressConverterTest() {

    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        converter = AddressConverter.getConverter(emf);
        cityFacade = CityFacade.getCityFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setup() throws FixedDataNotFoundException {
        city = cityFacade.getCity(2000, "Frederiksberg");
        address = new Address("Nymarksgyden", 57, "", city);
    }

    @AfterEach
    public void tearDown() {
        city = null;
        address = null;
    }

    @Test
    public void toDTO() {
        // Arrange
        Address objectToConvert = address;
        AddressDTO expected = new AddressDTO(objectToConvert);

        // Act
        AddressDTO actual = (AddressDTO) converter.toDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void fromDTO() throws FixedDataNotFoundException {
        // Arrange
        Address expected = address;
        AddressDTO objectToConvert = new AddressDTO(expected);

        // Act
        Address actual = (Address) converter.fromDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }

}
