package dtos;

import entities.Phone;

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

}
