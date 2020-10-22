package dtos;

import entities.Hobby;
import java.util.Objects;

/**
 *
 * @author Nikolaj Larsen
 */
public class HobbyDTO {

    private String hobbyName;
    private String hobbyDescription;

    public HobbyDTO(Hobby hobby) {
        hobbyName = hobby.getHobbyName();
        hobbyDescription = hobby.getHobbyDescription();
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public String getHobbyDescription() {
        return hobbyDescription;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.hobbyName);
        hash = 79 * hash + Objects.hashCode(this.hobbyDescription);
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
        final HobbyDTO other = (HobbyDTO) obj;
        if (!Objects.equals(this.hobbyName, other.hobbyName)) {
            return false;
        }
        if (!Objects.equals(this.hobbyDescription, other.hobbyDescription)) {
            return false;
        }
        return true;
    }

}
