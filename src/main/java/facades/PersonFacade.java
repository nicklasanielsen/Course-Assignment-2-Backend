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
import utils.ConvertDTO;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static AddressFacade addressFacade;
    private static ConvertDTO convertDTO;
    private static EntityManagerFactory emf;

    private PersonFacade() {
        // Private constructor to ensure Singleton
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            convertDTO = ConvertDTO.getConvertDTO(emf);
            instance = new PersonFacade();
            addressFacade = AddressFacade.getAddressFacade(emf);
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

        Person person = (Person) convertDTO.convertFromDTO(incomingData);
        Address address = (Address) convertDTO.convertFromDTO(incomingData.getAddress());
        List<Phone> phones = (List<Phone>) convertDTO.convertFromDTO(incomingData.getPhones());
        List<Hobby> hobbies = (List<Hobby>) convertDTO.convertFromDTO(incomingData.getHobbies());

        try {
            em.getTransaction().begin();
            em.persist(person);
            person.setAddress(address);
            person.setPhones(phones);
            person.setHobbies(hobbies);
            em.merge(person);
            em.getTransaction().commit();

            return (PersonDTO) convertDTO.convertToDTO(person);
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
            List<PersonDTO> personDTOs = (List<PersonDTO>) convertDTO.convertToDTO(persons);

            return personDTOs;
        } finally {
            em.close();
        }
    }

    /*
    TODO SKAL OPDATERES SÅ DEN SLETTE UBRUGT DATA
    */
    public PersonDTO editPerson(long id, PersonDTO incomingData) throws MissingInputException, InvalidInputException, FixedDataNotFoundException, DatabaseException {
        incomingDataIsValid(incomingData);

        EntityManager em = getEntityManager();

        Person existingPerson;
        Person person = (Person) convertDTO.convertFromDTO(incomingData);
        Address address = (Address) convertDTO.convertFromDTO(incomingData.getAddress());
        List<Phone> phones = (List<Phone>) convertDTO.convertFromDTO(incomingData.getPhones());
        List<Hobby> hobbies = (List<Hobby>) convertDTO.convertFromDTO(incomingData.getHobbies());

        try {
            em.getTransaction().begin();
            existingPerson = em.find(Person.class, id);

            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setEmail(person.getEmail());

            if (existingPerson.getAddress() != address) {
                Address temp = existingPerson.getAddress();
                existingPerson.removeAddress();

                existingPerson.setAddress(address);
            }

            existingPerson.setPhones(phones);
            existingPerson.setHobbies(hobbies);
            
            em.merge(existingPerson);
            em.getTransaction().commit();

            return (PersonDTO) convertDTO.convertToDTO(existingPerson);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new DatabaseException(e.toString());
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByHobby(String hobbyName) {

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getByHobby");
            query.setParameter("hobbyName", hobbyName);

            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOs = (List<PersonDTO>) convertDTO.convertToDTO(persons);

            return personDTOs;
        } finally {
            em.close();
        }
    }
    
    public List<PersonDTO> getPersonsByCity(String city) {

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getByCity");
            query.setParameter("city", city);

            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOs = (List<PersonDTO>) convertDTO.convertToDTO(persons);

            return personDTOs;
        } finally {
            em.close();
        }
    }
    
    public List<PersonDTO> getPersonsByZip(int zip) {

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getByZip");
            query.setParameter("zip", zip);

            List<Person> persons = query.getResultList();
            List<PersonDTO> personDTOs = (List<PersonDTO>) convertDTO.convertToDTO(persons);

            return personDTOs;
        } finally {
            em.close();
        }
    }
}
