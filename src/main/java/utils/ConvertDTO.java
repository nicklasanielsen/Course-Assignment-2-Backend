package utils;

import dtos.*;
import entities.*;
import exceptions.FixedDataNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import utils.converters.AddressConverter;
import utils.converters.Converter;
import utils.converters.HobbyConverter;
import utils.converters.PersonConverter;
import utils.converters.PhoneConverter;

/**
 *
 * @author Nicklas Nielsen
 */
public class ConvertDTO {

    private static ConvertDTO instance;
    private static EntityManagerFactory emf;
    private static HashMap<Class, Converter> converters;

    private ConvertDTO(){
        // Private constructor to ensure Singleton
    }

    public static ConvertDTO getConvertDTO(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new ConvertDTO();
        }
        
        return instance;
    }
    
    private void initConverters() {
        converters = new HashMap<>();
        converters.put(Person.class, PersonConverter.getConverter(emf));
        converters.put(PersonDTO.class, PersonConverter.getConverter(emf));
        converters.put(Address.class, AddressConverter.getConverter(emf));
        converters.put(AddressDTO.class, AddressConverter.getConverter(emf));
        converters.put(Phone.class, PhoneConverter.getConverter(emf));
        converters.put(PhoneDTO.class, PhoneConverter.getConverter(emf));
        converters.put(Hobby.class, HobbyConverter.getConverter(emf));
        converters.put(HobbyDTO.class, HobbyConverter.getConverter(emf));
    }

    private Converter getConverter(Class requestedClass) {
        if (converters == null) {
            initConverters();
        }

        Converter converter = converters.get(requestedClass);

        if (converter == null) {
            throw new UnsupportedOperationException("Requested converter not supported: " + requestedClass);
        }

        return converter;
    }
    
    public List convertFromDTO(List dtos) throws FixedDataNotFoundException {
        List entities = new ArrayList<>();

        for (Object dto : dtos) {
            entities.add(convertFromDTO(dto));
        }

        return entities;
    }

    public Object convertFromDTO(Object dto) throws FixedDataNotFoundException {
        Converter converter = getConverter(dto.getClass());

        return converter.fromDTO(dto);
    }

    public List convertToDTO(List entities) {
        List dtos = new ArrayList<>();

        entities.forEach(entity -> {
            dtos.add(convertToDTO(entity));
        });

        return dtos;
    }

    public Object convertToDTO(Object entity) {
        Converter converter = getConverter(entity.getClass());

        return converter.toDTO(entity);
    }

}
