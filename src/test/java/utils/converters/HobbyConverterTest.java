package utils.converters;

import dtos.HobbyDTO;
import entities.Hobby;
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
public class HobbyConverterTest {
    
    private static EntityManagerFactory emf;
    private static Converter converter;
    private static Hobby hobby;
    
    public HobbyConverterTest() {
    
    }

    @BeforeAll
    public static void setupClass(){
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        converter = HobbyConverter.getConverter(emf);
    }
    
    @AfterAll
    public static void tearDownClass(){
        emf.close();
    }
    
    @BeforeEach
    public void setup(){
        hobby = new Hobby("Tester", "Tester tingene");
    }
    
    @AfterEach
    public void tearDown(){
        hobby = null;
    }

    @Test
    public void toDTO() {
        // Arrange
        Hobby objectToConvert = hobby;
        HobbyDTO expected = new HobbyDTO(objectToConvert);

        // Act
        HobbyDTO actual = (HobbyDTO) converter.toDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void fromDTO() throws FixedDataNotFoundException  {
        // Arrange
        Hobby expected = hobby;
        HobbyDTO objectToConvert = new HobbyDTO(expected);

        // Act
        Hobby actual = (Hobby) converter.fromDTO(objectToConvert);

        // Assert
        assertEquals(expected, actual);
    }
    
}
