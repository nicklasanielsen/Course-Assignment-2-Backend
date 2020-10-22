package utils.converters;

import dtos.PhoneDTO;
import entities.Phone;
import entities.PhoneType;
import exceptions.FixedDataNotFoundException;
import facades.PhoneTypeFacade;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class PhoneConverter implements Converter {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PhoneTypeFacade PHONE_TYPE_FACADE = PhoneTypeFacade.getPhoneTypeFacade(EMF);

    @Override
    public Object toDTO(Object object) {
        Phone entity = castToEntity(object);
        return new PhoneDTO(entity);
    }

    @Override
    public Object fromDTO(Object object) throws FixedDataNotFoundException {
        PhoneDTO dto = castToDTO(object);

        int number = dto.getNumber();
        PhoneType phoneType = PHONE_TYPE_FACADE.getPhoneType(dto.getType());

        return new Phone(number, phoneType);
    }

    private Phone castToEntity(Object entity) {
        return (Phone) entity;
    }

    private PhoneDTO castToDTO(Object dto) {
        return (PhoneDTO) dto;
    }

}
