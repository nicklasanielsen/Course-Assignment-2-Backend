package utils.converters;

import dtos.PhoneDTO;
import entities.Phone;
import entities.PhoneType;
import exceptions.FixedDataNotFoundException;
import facades.PhoneTypeFacade;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 */
public class PhoneConverter implements Converter {

    private static PhoneConverter instance;
    private static EntityManagerFactory emf;
    private static PhoneTypeFacade phoneTypeFacade;

    private PhoneConverter() {
        // Private constructor to ensure Singleton
    }

    public static Converter getConverter(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneConverter();
            phoneTypeFacade = PhoneTypeFacade.getPhoneTypeFacade(emf);
        }

        return instance;
    }

    @Override
    public Object toDTO(Object object) {
        Phone entity = castToEntity(object);
        return new PhoneDTO(entity);
    }

    @Override
    public Object fromDTO(Object object) throws FixedDataNotFoundException {
        PhoneDTO dto = castToDTO(object);

        int number = dto.getNumber();
        PhoneType phoneType = phoneTypeFacade.getPhoneType(dto.getType().trim());

        return new Phone(number, phoneType);
    }

    private Phone castToEntity(Object entity) {
        return (Phone) entity;
    }

    private PhoneDTO castToDTO(Object dto) {
        return (PhoneDTO) dto;
    }

}
