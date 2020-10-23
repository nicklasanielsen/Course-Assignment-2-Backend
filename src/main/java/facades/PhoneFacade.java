package facades;

import entities.Phone;
import entities.PhoneType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class PhoneFacade {

    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    private PhoneFacade() {
        // Private constructor to ensure Singleton
    }

    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Phone getPhone(int number, PhoneType type) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Phone.getPhone");
            query.setParameter("number", number);
            query.setParameter("type", type);

            return (Phone) query.getSingleResult();
        } catch (NoResultException e) {
            return new Phone(number, type);
        } finally {
            em.close();
        }
    }

}
