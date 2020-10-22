package dtos;

import entities.Address;
import java.util.Objects;

/**
 *
 * @author Mathias Nielsen
 */
public class AddressDTO {

    private String address;
    private String city;

    public AddressDTO(Address address) {
        this.address = address.getStreet() + " " + address.getHouseNumber() + " " + address.getFloor();
        this.city = address.getCity().getZip() + " " + address.getCity().getCityName();
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.address);
        hash = 29 * hash + Objects.hashCode(this.city);
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
        final AddressDTO other = (AddressDTO) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }

}
