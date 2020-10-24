package facades;

import dtos.CityDTO;
import entities.City;
import exceptions.FixedDataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import utils.ConvertDTO;

/**
 *
 * @author Nicklas Nielsen
 */
public class CityFacade {

    private static CityFacade instance;
    private static EntityManagerFactory emf;
    private static ConvertDTO convertDTO;

    private CityFacade() {
        // Private constructor to ensure Singleton
    }

    public static CityFacade getCityFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public City getCity(int zip, String cityName) throws FixedDataNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("City.getCityByNameAndZip");
            query.setParameter("zip", zip);
            query.setParameter("cityName", cityName);

            return (City) query.getSingleResult();
        } catch (NoResultException e) {
            try {
                return getCityByZip(zip);
            } catch (NoResultException ex) {
                try {
                    return getCityByName(cityName);
                } catch (NoResultException exx) {
                    throw new FixedDataNotFoundException("By ej fundet");
                }
            }
        } finally {
            em.close();
        }
    }

    private City getCityByZip(int zip) throws NoResultException {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("City.getCityByZip");
            query.setParameter("zip", zip);

            return (City) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    private City getCityByName(String cityName) throws NoResultException {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("City.getCityByName");
            query.setParameter("cityName", cityName);

            return (City) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<CityDTO> getAllZips() {

        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("City.getAllZips");
            List<City> cities = query.getResultList();
            
            List<CityDTO> cityDTOs = new ArrayList();
            
            for(City city : cities){
                cityDTOs.add(new CityDTO(city));
            }
            
            return cityDTOs;
        } finally {
            em.close();
        }
    }

}
