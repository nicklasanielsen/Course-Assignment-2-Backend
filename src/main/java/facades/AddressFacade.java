package facades;

import entities.Address;
import entities.City;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class AddressFacade {

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    private AddressFacade() {
        // Private constructor to ensure Singleton
    }

    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Address getAddress(String street, int houseNumber, String floor, City city) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Address.getAddress");
            query.setParameter("street", street);
            query.setParameter("houseNumber", houseNumber);
            query.setParameter("floor", floor);
            query.setParameter("city", city);

            return (Address) query.getSingleResult();
        } catch (NoResultException e) {
            return new Address(street, houseNumber, floor, city);
        } finally {
            em.close();
        }
    }

}
