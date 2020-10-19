package facades;

import dtos.PersonDTO;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
        // TODO add validation
        return false;
    }

    public PersonDTO createPerson(PersonDTO incomingData) {
        if (!incomingDataIsValid(incomingData)) {
            // TODO throw expection
        }

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

            // TODO throw expection
        } finally {
            em.close();
        }
        
        return null; // To be removed then expection handling have been added
    }

    public List<PersonDTO> getPersonsByPhone(int number) {
        if (String.valueOf(number).length() != 8) {
            // TODO throw expection
        }

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getByPhone");
            query.setParameter("number", number);

            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOs = convertToDTO(persons);

            return personDTOs;
        } catch (Exception e) {
            // TODO throw expection
        } finally {
            em.close();
        }

        return null; // To be removed then expection handling have been added
    }

}
