package org.diginamic.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.diginamic.model.ProductCategory;
import org.diginamic.types.Dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class ProductCategoryRepository implements Dao {

    private EntityManager entityManager;

    public ProductCategoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional get(long id) {
        return Optional.ofNullable(entityManager.find(ProductCategory.class, id));
    }

    @Override
    public List getAll() {
        Query query = entityManager.createQuery("SELECT c FROM ProductCategory c");
        return query.getResultList();
    }

    public List<ProductCategory> getByLabel(String label) {
        Query query = entityManager
                .createQuery("SELECT c FROM ProductCategory c WHERE c.label = :label").setParameter("label", label);
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

    public void save(ProductCategory productCategory) {
        executeInsideTransaction(entityManager -> entityManager.persist(productCategory));
    }

    public void update(ProductCategory productCategory, String[] params) {
        productCategory.setLabel(Objects.requireNonNull(params[0], "Label cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(productCategory));
    }

    public void delete(ProductCategory productCategory) {
        executeInsideTransaction(entityManager -> entityManager.remove(productCategory));
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
