package org.diginamic.orm;

import jakarta.persistence.EntityManager;

public interface AccessorInterface  {

     EntityManager getManager() throws Exception;
}
