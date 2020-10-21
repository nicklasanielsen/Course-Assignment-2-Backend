package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Address;
import entities.City;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import entities.PhoneType;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;
import exceptions.MissingInputException;
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
        Person person = new Person();
        Address address = new Address();
        City city = new City();
        List<Phone> phones = new ArrayList<>();
        List<Hobby> hobbies = new ArrayList<>();

        String[] personParts = personDTO.getFullName().split(" ");
        String[] addressParts = personDTO.getAddress().getAddress().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String[] cityParts = personDTO.getAddress().getCity().split(" ");

        city.setZip(Integer.parseInt(cityParts[0]));
        city.setCityName(cityParts[1]);

        address.setStreet(addressParts[0]);
        address.setHouseNumber(Integer.parseInt(addressParts[1]));
        address.setFloor(addressParts[2]);
        address.setCity(city);

        person.setFirstName(personParts[0]);
        person.setLastName(personParts[1]);
        person.setEmail(personDTO.getEmail());
        person.setAddress(address);

        personDTO.getPhones().forEach(phone -> {
            person.addPhone(new Phone(phone.getNumber(), new PhoneType(phone.getType())));
        });

        personDTO.getHobbies().forEach(hobby -> {
            hobbies.add(new Hobby(hobby.getHobbyName(), hobby.getHobbyDescription()));
        });
        person.setHobbies(hobbies);

        return person;
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

    public PersonDTO createPerson(PersonDTO incomingData) throws MissingInputException, InvalidInputException, DatabaseException {
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
