package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;

/**
 *
 * @author Mathias Nielsen
 */
public class PersonDTO {

    private String fullName;
    private String email;
    private AddressDTO address;
    private List<PhoneDTO> phones;
    private List<HobbyDTO> hobbies;

    public PersonDTO(Person person) {
        this.fullName = person.getFirstName() + " " + person.getLastName();
        this.email = person.getEmail();
        this.address = new AddressDTO(person.getAddress());

        for (Phone phone : person.getPhones()) {
            phones.add(new PhoneDTO(phone));
        }

        for (Hobby hobby : person.getHobbies()) {
            hobbies.add(new HobbyDTO(hobby));
        }
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
