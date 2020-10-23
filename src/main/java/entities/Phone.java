package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Nicklas Nielsen
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Phone.deleteAllRows", query = "DELETE FROM Phone"),
    @NamedQuery(name = "Phone.getByPerson", query = "SELECT p FROM Phone p "
            + "WHERE p.person.id = :id"),
    @NamedQuery(name = "Phone.getPhone", query = "SELECT p FROM Phone p "
            + "WHERE p.number = :number AND p.type = :type")
})
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false)
    private int number;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Person person;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private PhoneType type;

    public Phone(int number, PhoneType type) {
        this.number = number;
        this.type = type;
    }

    public Phone() {

    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    public void removePerson(){
        person = null;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.number;
        hash = 37 * hash + Objects.hashCode(this.person);
        hash = 37 * hash + Objects.hashCode(this.type);
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
        final Phone other = (Phone) obj;
        if (this.number != other.number) {
            return false;
        }
        if (!Objects.equals(this.person, other.person)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

}
