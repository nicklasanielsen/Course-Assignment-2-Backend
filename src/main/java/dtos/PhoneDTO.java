package dtos;

import entities.Phone;
import java.util.Objects;

/**
 *
 * @author Nicklas Nielsen
 */
public class PhoneDTO {

    private int number;
    private String type;

    public PhoneDTO(Phone phone) {
        number = phone.getNumber();
        type = phone.getType().getType();
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.number;
        hash = 23 * hash + Objects.hashCode(this.type);
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
        final PhoneDTO other = (PhoneDTO) obj;
        if (this.number != other.number) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

}
