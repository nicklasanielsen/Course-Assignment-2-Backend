package facades;

import entities.Hobby;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    private HobbyFacade() {
        // Private constructor to ensure Singleton
    }

    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Hobby getHobby(String name, String description) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Hobby.getByNameAndDescription");
            query.setParameter("hobbyName", name);
            query.setParameter("hobbyDescription", description);

            return (Hobby) query.getSingleResult();
        } catch (NoResultException e) {
            try {
                return getHobbyByName(name);
            } catch (NoResultException ex) {
                return new Hobby(name, description);
            }
        } finally {
            em.close();
        }
    }

    private Hobby getHobbyByName(String name) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Hobby.getByName");
            query.setParameter("hobbyName", name);

            return (Hobby) query.getSingleResult();
        } finally {
            em.close();
        }
    }

}
