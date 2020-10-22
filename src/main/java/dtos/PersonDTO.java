package dtos;

import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.fullName);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.address);
        hash = 89 * hash + Objects.hashCode(this.phones);
        hash = 89 * hash + Objects.hashCode(this.hobbies);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonDTO other = (PersonDTO) obj;
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phones, other.phones)) {
            return false;
        }
        if (!Objects.equals(this.hobbies, other.hobbies)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "fullName=" + fullName + ", email=" + email + ", address=" + address + ", phones=" + phones + ", hobbies=" + hobbies + '}';
    }

}
