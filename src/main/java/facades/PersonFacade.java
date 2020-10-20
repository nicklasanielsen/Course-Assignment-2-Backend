package facades;

import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.apache.commons.validator.EmailValidator;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade() {
        // Private constructor to ensure Singleton
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private List<PersonDTO> convertToDTO(List<Person> persons) {
        List<PersonDTO> personDTOs = new ArrayList<>();

        persons.forEach(person -> {
            personDTOs.add(new PersonDTO(person));
        });

        return personDTOs;
    }

    private PersonDTO convertToDTO(Person person) {
        return new PersonDTO(person);
    }

    private Person convertFromDTO(PersonDTO personDTO) {
        // TODO 
        return null;
    }

    private boolean incomingDataIsValid(PersonDTO incomingData) {
        if(incomingData.getFullName().isEmpty()){
            // TODO Throw exception
        } else if(incomingData.getEmail().isEmpty()){
            // TODO Throw exception
        } else if(EmailValidator.getInstance().isValid(incomingData.getEmail())){
            // TODO Throw exception
        } else if(incomingData.getPhones().isEmpty()){
            // TODO Throw exception
        } else if(incomingData.getAddress() == null){
            // TODO Throw exception
        } else if(incomingData.getHobbies().isEmpty()){
            // TODO Throw exception
        }
        
        incomingData.getPhones().forEach(phoneDTO -> {
            if(phoneDTO.getNumber() == 0){
                // TODO Throw exception
            } else if(phoneDTO.getType().isEmpty()){
                // TODO Throw exception
            }
        });
        
        incomingData.getHobbies().forEach(hobbyDTO -> {
            if(hobbyDTO.getHobbyName().isEmpty()){
                // TODO Throw exception
            } else if(hobbyDTO.getHobbyDescription().isEmpty()){
                // TODO Throw exception
            }
        });
        
        return true;
    }

    public PersonDTO createPerson(PersonDTO incomingData) {
        incomingDataIsValid(incomingData);

        EntityManager em = getEntityManager();

        Person person = convertFromDTO(incomingData);

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();

            return convertToDTO(person);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            // TODO throw exception
        } finally {
            em.close();
        }
        
        return null; // To be removed then exception handling have been added
    }

    public List<PersonDTO> getPersonsByPhone(int number) {
        if (String.valueOf(number).length() != 8) {
            // TODO throw exception
        }

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getByPhone");
            query.setParameter("number", number);

            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOs = convertToDTO(persons);

            return personDTOs;
        } catch (Exception e) {
            // TODO throw exception
        } finally {
            em.close();
        }

        return null; // To be removed then exception handling have been added
    }

}
