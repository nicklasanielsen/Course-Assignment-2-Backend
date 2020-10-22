package utils.converters;

import dtos.PhoneDTO;
import entities.Phone;
import entities.PhoneType;
import exceptions.FixedDataNotFoundException;
import facades.PhoneTypeFacade;
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
public class PhoneConverterTest {

    private static EntityManagerFactory emf;
    private static Converter converter;
    private static PhoneTypeFacade phoneTypeFacade;
    private static Phone phone;
    private static PhoneType phoneType;

    public PhoneConverterTest() {

    }

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        converter = PhoneConverter.getConverter(emf);
        phoneTypeFacade = PhoneTypeFacade.getPhoneTypeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setup() throws FixedDataNotFoundException {
        phoneType = phoneTypeFacade.getPhoneType("Privat");
        phone = new Phone(12345678, phoneType);
    }

    @AfterEach
    public void tearDown() {
        phone = null;
        phoneType = null;
    }

    @Test
    public void toDTO() {
        // Arrange
        Phone objectToConvert = phone;
        PhoneDTO expceted = new PhoneDTO(objectToConvert);

        // Act
        PhoneDTO actual = (PhoneDTO) converter.toDTO(objectToConvert);

        // Assert
        assertEquals(expceted, actual);
    }

    @Test
    public void fromDTO() throws FixedDataNotFoundException {
        // Arrange
        Phone expected = phone;
        PhoneDTO objectToConvert = new PhoneDTO(expected);

        // Act
        Phone actual = (Phone) converter.fromDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }

}
