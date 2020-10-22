package dtos;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathias Nielsen
 */
public class PersonDTO {

    private String fullName;
    private String email;
    private AddressDTO address;
    private List<PhoneDTO> phones = new ArrayList<>();
    private List<HobbyDTO> hobbies = new ArrayList<>();

    public PersonDTO(Person person) {
        this.fullName = person.getFirstName() + " " + person.getLastName();
        this.email = person.getEmail();
        this.address = new AddressDTO(person.getAddress());

        person.getPhones().forEach(phone -> {
            phones.add(new PhoneDTO(phone));
        });

        person.getHobbies().forEach(hobby -> {
            hobbies.add(new HobbyDTO(hobby));
        });
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }
    
    

}
