package utils;

import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Address;
import entities.City;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import entities.PhoneType;
import exceptions.FixedDataNotFoundException;
import facades.AddressFacade;
import facades.CityFacade;
import facades.HobbyFacade;
import facades.PersonFacade;
import facades.PhoneTypeFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 * @author Mathias Nielsen
 */
public class ConvertDTO {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade PERSON_FACADE = PersonFacade.getPersonFacade(EMF);
    private static final AddressFacade ADDRESS_FACADE = AddressFacade.getAddressFacade(EMF);
    private static final CityFacade CITY_FACADE = CityFacade.getCityFacade(EMF);
    private static final PhoneTypeFacade PHONE_TYPE_FACADE = PhoneTypeFacade.getPhoneTypeFacade(EMF);
    private static final HobbyFacade HOBBY_FACADE = HobbyFacade.getHobbyFacade(EMF);

    public static Person convertFromDTO(PersonDTO personDTO) {
        String firstname, lastname, email;

        firstname = personDTO.getFullName().split(" ")[0];
        lastname = personDTO.getFullName().split(" ")[1];
        email = personDTO.getEmail();

        return new Person(firstname, lastname, email);
    }

    public static Address convertFromDTO(AddressDTO addressDTO) throws FixedDataNotFoundException {
        String street, floor, cityName;
        int houseNumber, zip;
        City city;

        String[] addressParts = addressDTO.getAddress().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        street = addressParts[0];
        houseNumber = Integer.parseInt(addressParts[1]);
        floor = addressParts[2];

        String[] cityParts = addressDTO.getCity().split(" ");

        zip = Integer.parseInt(cityParts[0]);
        cityName = cityParts[1];

        city = CITY_FACADE.getCity(zip, cityName);
        Address address = ADDRESS_FACADE.getAddress(street, houseNumber, floor, city);

        return address;
    }

    public static List<Phone> convertPhonesFromDTO(List<PhoneDTO> phoneDTOs) throws FixedDataNotFoundException {
        List<Phone> phones = new ArrayList<>();

        int number;
        PhoneType phoneType;

        for (PhoneDTO phoneDTO : phoneDTOs) {
            number = phoneDTO.getNumber();
            phoneType = PHONE_TYPE_FACADE.getPhoneType(phoneDTO.getType());

            phones.add(new Phone(number, phoneType));
        }

        return phones;
    }

    public static List<Hobby> convertHobbiesFromDTO(List<HobbyDTO> hobbiesDTO) {
        List<Hobby> hobbies = new ArrayList<>();

        String name, description;

        for (HobbyDTO hobbyDTO : hobbiesDTO) {
            name = hobbyDTO.getHobbyName();
            description = hobbyDTO.getHobbyDescription();

            Hobby hobby = HOBBY_FACADE.getHobby(name, description);

            hobbies.add(hobby);
        }

        return hobbies;
    }

    public static List<PersonDTO> convertToDTO(List<Person> persons) {
        List<PersonDTO> personDTOs = new ArrayList<>();

        persons.forEach(person -> {
            personDTOs.add(convertToDTO(person));
        });

        return personDTOs;
    }

    public static PersonDTO convertToDTO(Person person) {
        return new PersonDTO(person);
    }

}
