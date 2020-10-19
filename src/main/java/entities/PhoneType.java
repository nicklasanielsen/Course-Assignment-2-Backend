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
import javax.persistence.OneToMany;

/**
 *
 * @author Nicklas Nielsen
 */
@Entity
public class PhoneType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String type;

    @OneToMany(mappedBy = "type", cascade = CascadeType.PERSIST)
    private List<Phone> numbers;

    public PhoneType(String type) {
        this.type = type;
        numbers = new ArrayList<>();
    }

    public PhoneType() {
    }

    public Long getId() {
        return id;
    }

    public List<Phone> getNumbers() {
        return numbers;
    }

    public void addNumber(Phone phone) {
        numbers.add(phone);

        if (phone != null) {
            phone.setType(this);
        }
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.type);
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
        final PhoneType other = (PhoneType) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

}
