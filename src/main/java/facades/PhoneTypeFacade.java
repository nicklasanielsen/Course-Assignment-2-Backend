package facades;

import entities.PhoneType;
import exceptions.FixedDataNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class PhoneTypeFacade {

    private static PhoneTypeFacade instance;
    private static EntityManagerFactory emf;

    private PhoneTypeFacade() {
        // Private constructor to ensure Singleton
    }

    public static PhoneTypeFacade getPhoneTypeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneTypeFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PhoneType getPhoneType(String type) throws FixedDataNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("PhoneType.getType");
            query.setParameter("type", type);

            return (PhoneType) query.getSingleResult();
        } catch (NoResultException e) {
            throw new FixedDataNotFoundException("Telefon type ej fundet");
        } finally {
            em.close();
        }
    }

}
