package utils.converters;

import dtos.HobbyDTO;
import entities.Hobby;
import exceptions.FixedDataNotFoundException;
import facades.HobbyFacade;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 */
public class HobbyConverter implements Converter {

    private static HobbyConverter instance;
    private static EntityManagerFactory emf;
    private static HobbyFacade hobbyFacade;

    private HobbyConverter() {
        // Private constructor to ensure Singleton
    }

    public static Converter getConverter(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyConverter();
            hobbyFacade = HobbyFacade.getHobbyFacade(emf);
        }

        return instance;
    }

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

        return hobbyFacade.getHobby(name, description);
    }

    private Hobby castToEntity(Object entity) {
        return (Hobby) entity;
    }

    private HobbyDTO castToDTO(Object dto) {
        return (HobbyDTO) dto;
    }

}
