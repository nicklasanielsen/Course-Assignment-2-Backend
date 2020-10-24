package dtos;

import entities.City;
import java.util.Objects;

/**
 *
 * @author Nikolaj Larsen
 */
public class CityDTO {
    
    private int zip;
    private String city;

    public CityDTO(City city) {
        this.zip = city.getZip();
        this.city = city.getCityName();
    }

    public int getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.zip;
        hash = 37 * hash + Objects.hashCode(this.city);
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
        final CityDTO other = (CityDTO) obj;
        if (this.zip != other.zip) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }
    
}
