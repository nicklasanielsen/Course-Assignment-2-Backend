package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Mathias Nielsen
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person"),
    @NamedQuery(name = "Person.getByPhone", query = "SELECT p FROM Person p JOIN p.phones n WHERE n.number = :number"),
    @NamedQuery(name = "Person.getByHobby", query = "SELECT p FROM Person p JOIN p.hobbies h WHERE h.hobbyName = :hobbyName"),
    @NamedQuery(name = "Person.getByCity", query = "SELECT p FROM Person p WHERE p.address.city.cityName = :city"),
    @NamedQuery(name = "Person.getByZip", query = "SELECT p FROM Person p WHERE p.address.city.zip = :zip")
})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String firstName;

    @Column(length = 45, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false)
    private String email;

    @JoinColumn(nullable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Phone> phones;

    @ManyToMany(mappedBy = "persons", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Hobby> hobbies;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phones = new ArrayList<>();
        this.hobbies = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        address.addPerson(this);
    }
    
    public void removeAddress(){
        address.removePerson(this);
        address = null;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {        
        this.phones.forEach(phone -> {
            phone.removePerson();
        });
        
        this.phones.clear();
        
        phones.forEach(phone -> {
            addPhone(phone);
        });
    }

    public void addPhones(List<Phone> phones) {
        phones.forEach(phone -> {
            addPhone(phone);
        });
    }

    public void addPhone(Phone phone) {
        phones.add(phone);

        if (phone != null) {
            phone.setPerson(this);
        }
    }
    
    public void removePhone(Phone phone){
        phones.remove(phone);
        phone.removePerson();
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies.forEach(hobby -> {
            hobby.removePerson(this);
        });
        
        this.hobbies.clear();
        
        hobbies.forEach(hobby -> {
            addHobby(hobby);
        });
    }

    public void addHobbies(List<Hobby> hobbies) {
        hobbies.forEach(hobby -> {
            addHobby(hobby);
        });
    }

    public void addHobby(Hobby hobby) {
        hobbies.add(hobby);

        if (hobby != null) {
            hobby.addPerson(this);
        }
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
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
        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", address=" + address + ", phones=" + phones + ", hobbies=" + hobbies + '}';
    }

}
