package org.diginamic.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBAccessor implements AccessorInterface {

    
    /** 
     * @return EntityManager
     * @throws Exception
     */
    public  EntityManager getManager() throws Exception {
        EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("org.diginamic.model");

        EntityManager entityManager = sessionFactory.createEntityManager();
        return entityManager;
    }
}
