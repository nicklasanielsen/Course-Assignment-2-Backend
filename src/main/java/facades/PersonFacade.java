package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.DatabaseException;
import exceptions.FixedDataNotFoundException;
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import static utils.ConvertDTO.*;

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

    private boolean incomingDataIsValid(PersonDTO incomingData) throws MissingInputException, InvalidInputException {
        // TODO Add email validation

        if (incomingData.getFullName().isEmpty()) {
            throw new MissingInputException("For- eller efternavn mangler");
        } else if (incomingData.getEmail().isEmpty()) {
            throw new MissingInputException("E-mail adresse mangler");
        } else if (incomingData.getPhones().isEmpty()) {
            throw new MissingInputException("Telefon oplysninger ej gyldige");
        } else if (incomingData.getAddress() == null) {
            throw new MissingInputException("Adresse mangler");
        }

        for (PhoneDTO phoneDTO : incomingData.getPhones()) {
            if (phoneDTO.getNumber() == 0) {
                throw new MissingInputException("Telefonnummer mangler");
            } else if (phoneDTO.getType().isEmpty()) {
                throw new MissingInputException("Telefon type mangler");
            }
        }

        for (HobbyDTO hobbyDTO : incomingData.getHobbies()) {
            if (hobbyDTO.getHobbyName().isEmpty()) {
                throw new MissingInputException("Hobby navn mangler");
            } else if (hobbyDTO.getHobbyDescription().isEmpty()) {
                throw new MissingInputException("Hobby beskivelse mangler");
            }
        }

        return true;
    }

    public PersonDTO createPerson(PersonDTO incomingData) throws MissingInputException, InvalidInputException, DatabaseException, FixedDataNotFoundException {
        incomingDataIsValid(incomingData);

        EntityManager em = getEntityManager();

        Person person = convertFromDTO(incomingData);
        Address address = convertFromDTO(incomingData.getAddress());
        List<Phone> phones = convertPhonesFromDTO(incomingData.getPhones());
        List<Hobby> hobbies = convertHobbiesFromDTO(incomingData.getHobbies());

        try {
            em.getTransaction().begin();
            em.persist(person);
            person.setAddress(address);
            person.setPhones(phones);
            person.setHobbies(hobbies);
            em.merge(person);
            em.getTransaction().commit();

            return convertToDTO(person);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new DatabaseException("Personen kunne ikke oprettes, prøv igen senere");
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByPhone(int number) throws InvalidInputException {
        if (String.valueOf(number).length() != 8) {
            throw new InvalidInputException("Telefonnummer ej gyldigt");
        }

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getByPhone");
            query.setParameter("number", number);

            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOs = convertToDTO(persons);

            return personDTOs;
        } finally {
            em.close();
        }
    }

}
