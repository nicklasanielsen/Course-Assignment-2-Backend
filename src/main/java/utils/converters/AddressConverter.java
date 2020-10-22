package utils.converters;

import dtos.AddressDTO;
import entities.Address;
import entities.City;
import exceptions.FixedDataNotFoundException;
import facades.AddressFacade;
import facades.CityFacade;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class AddressConverter implements Converter {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CityFacade CITY_FACADE = CityFacade.getCityFacade(EMF);
    private static final AddressFacade ADDRESS_FACADE = AddressFacade.getAddressFacade(EMF);

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

        street = addressParts[0];
        houseNumber = Integer.parseInt(addressParts[1]);
        floor = addressParts[2];

        String[] cityParts = dto.getCity().split(" ");

        zip = Integer.parseInt(cityParts[0]);
        cityName = cityParts[1];

        city = CITY_FACADE.getCity(zip, cityName);
        Address address = ADDRESS_FACADE.getAddress(street, houseNumber, floor, city);

        return address;
    }

    private Address castToEntity(Object entity) {
        return (Address) entity;
    }

    private AddressDTO castToDTO(Object dto) {
        return (AddressDTO) dto;
    }

}
