package org.diginamic.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import org.diginamic.model.Ingredient;
import org.diginamic.types.Dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class IngredientRepository implements Dao {

    private EntityManager entityManager;

    public IngredientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional get(long id) {
        return Optional.ofNullable(entityManager.find(Ingredient.class, id));
    }

    @Override
    public List getAll() {
        Query query = entityManager.createQuery("SELECT i FROM Ingredient i");
        return query.getResultList();
    }

    public List<Ingredient> getByName(String name) {
        Query query = entityManager
                .createQuery("SELECT i FROM Ingredient i WHERE i.name = :name").setParameter("name", name);
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

    public void save(Ingredient brand) {
        executeInsideTransaction(entityManager -> entityManager.persist(brand));
    }

    public void update(Ingredient brand, String[] params) {
        brand.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(brand));
    }

    public void delete(Ingredient brand) {
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
