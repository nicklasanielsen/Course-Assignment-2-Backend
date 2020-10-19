package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Nikolaj Larsen
 */
@Entity
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "zip", nullable = false)
    private int zip;
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    
    @OneToMany( mappedBy = "city", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Address> addresses;

    public City() {
    }

    public City(int zip, String city) {
        this.zip = zip;
        this.city = city;
        addresses = new ArrayList<>();
    }
    
    

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public void addAddress(Address address) {
        addresses.add(address)
                
            if(address != null){
                address.setCity(this)
            }
    }
    
    public void removeAddress(Address address){
        addresses.remove(address);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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
        final City other = (City) obj;
        if (this.zip != other.zip) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }
    
}