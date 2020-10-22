package utils.converters;

import dtos.HobbyDTO;
import entities.Hobby;
import exceptions.FixedDataNotFoundException;
import facades.HobbyFacade;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class HobbyConverter implements Converter {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HobbyFacade HOBBY_FACADE = HobbyFacade.getHobbyFacade(EMF);

    @Override
    public Object toDTO(Object object) {
        Hobby entity = castToEntity(object);
        return new HobbyDTO(entity);
    }

    @Override
    public Object fromDTO(Object object) throws FixedDataNotFoundException {
        HobbyDTO dto = castToDTO(object);

        String name, description;
        name = dto.getHobbyName().trim();
        description = dto.getHobbyDescription().trim();

        return HOBBY_FACADE.getHobby(name, description);
    }

    private Hobby castToEntity(Object entity) {
        return (Hobby) entity;
    }

    private HobbyDTO castToDTO(Object dto) {
        return (HobbyDTO) dto;
    }

}
