package org.diginamic.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import org.diginamic.model.Brand;
import org.diginamic.types.Dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class BrandRepository implements Dao {

    private EntityManager entityManager;

    public BrandRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    
    /** 
     * @param id
     * @return Optional
     */
    @Override
    public Optional get(long id) {
        return Optional.ofNullable(entityManager.find(Brand.class, id));
    }

    
    /** 
     * @return List
     */
    @Override
    public List getAll() {
        Query query = entityManager.createQuery("SELECT c FROM Brand c");
        return query.getResultList();
    }

    public List<Brand> getByName(String name) {
        Query query = entityManager
                .createQuery("SELECT c FROM Brand c WHERE c.name = :name").setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o, String[] params) {

    }

    @Override
    public void delete(Object o) {

    }

    public void save(Brand brand) {
        executeInsideTransaction(entityManager -> entityManager.persist(brand));
    }

    public void update(Brand brand, String[] params) {
        brand.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(brand));
    }

    public void delete(Brand brand) {
        executeInsideTransaction(entityManager -> entityManager.remove(brand));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
