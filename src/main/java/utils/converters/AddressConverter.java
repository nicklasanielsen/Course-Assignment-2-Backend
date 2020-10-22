package utils.converters;

import dtos.AddressDTO;
import entities.Address;
import entities.City;
import exceptions.FixedDataNotFoundException;
import facades.AddressFacade;
import facades.CityFacade;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 */
public class AddressConverter implements Converter {

    private static AddressConverter instance;
    private static EntityManagerFactory emf;
    private static CityFacade cityFacade;
    private static AddressFacade addressFacade;

    private AddressConverter() {
        // Private constructor to ensure Singleton
    }

    public static Converter getConverter(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressConverter();
            cityFacade = CityFacade.getCityFacade(emf);
            addressFacade = AddressFacade.getAddressFacade(emf);
        }

        return instance;
    }

    @Override
    public Object toDTO(Object object) {
        Address entity = castToEntity(object);
        return new AddressDTO(entity);
    }

    @Override
    public Object fromDTO(Object object) throws FixedDataNotFoundException {
        AddressDTO dto = castToDTO(object);
        String street, floor, cityName;
        int houseNumber, zip;
        City city;

        String[] addressParts = dto.getAddress().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        street = addressParts[0].trim();
        houseNumber = Integer.parseInt(addressParts[1]);
        floor = addressParts[2].trim();

        String[] cityParts = dto.getCity().split(" ");

        zip = Integer.parseInt(cityParts[0]);
        cityName = cityParts[1].trim();

        city = cityFacade.getCity(zip, cityName);
        Address address = addressFacade.getAddress(street, houseNumber, floor, city);

        return address;
    }

    private Address castToEntity(Object entity) {
        return (Address) entity;
    }

    private AddressDTO castToDTO(Object dto) {
        return (AddressDTO) dto;
    }

}
