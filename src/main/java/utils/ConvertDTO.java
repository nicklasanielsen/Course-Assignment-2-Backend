package utils;

import dtos.*;
import entities.*;
import exceptions.FixedDataNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static HashMap<Class, Converter> converters;

    private static void initConverters() {
        converters = new HashMap<>();
        converters.put(Person.class, new PersonConverter());
        converters.put(PersonDTO.class, new PersonConverter());
        converters.put(Address.class, new AddressConverter());
        converters.put(AddressDTO.class, new AddressConverter());
        converters.put(Phone.class, new PhoneConverter());
        converters.put(PhoneDTO.class, new PhoneConverter());
        converters.put(Hobby.class, new HobbyConverter());
        converters.put(HobbyDTO.class, new HobbyConverter());
    }

    private static Converter getConverter(Class requestedClass) {
        if (converters == null) {
            initConverters();
        }

        Converter converter = converters.get(requestedClass);

        if (converter == null) {
            throw new UnsupportedOperationException("Requested converter not supported: " + requestedClass);
        }

        return converter;
    }

    public static List convertFromDTO(List dtos) throws FixedDataNotFoundException {
        List entities = new ArrayList<>();

        for (Object dto : dtos) {
            entities.add(convertFromDTO(dto));
        }

        return entities;
    }

    public static Object convertFromDTO(Object dto) throws FixedDataNotFoundException {
        Converter converter = getConverter(dto.getClass());

        return converter.fromDTO(dto);
    }

    public static List convertToDTO(List entities) {
        List dtos = new ArrayList<>();

        entities.forEach(entity -> {
            dtos.add(convertToDTO(entity));
        });

        return dtos;
    }

    public static Object convertToDTO(Object entity) {
        Converter converter = getConverter(entity.getClass());

        return converter.toDTO(entity);
    }

}
