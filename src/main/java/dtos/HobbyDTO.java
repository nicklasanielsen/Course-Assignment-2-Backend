package dtos;

import entities.Hobby;

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
    
}
