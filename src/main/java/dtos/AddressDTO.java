package dtos;

import entities.Address;
import entities.City;

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
    
}
