package net.sf.jpasecurity.multitenant;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import net.sf.jpasecurity.persistence.SecureEntityManagerFactory;

public class MultitenantEntityManagerFactory implements EntityManagerFactory {

	private HashMap<Integer, SecureEntityManagerFactory> factories = new HashMap<Integer, SecureEntityManagerFactory>();

	public void addFactory(Integer id, SecureEntityManagerFactory factory) {
		if (factories.containsKey(id))
			throw new RuntimeException("Factory with id: " + id + "exists");
		factories.put(id, factory);
	}

	// this method to wrap the exception ( DRY )
	private EntityManagerFactory getFactory(Integer id) {
		if (!factories.containsKey(id))
			throw new NoSuchTenantException(id);
		return factories.get(id);
	}

	public EntityManager createEntityManager() {
		Integer tId = getTenant();
		return getFactory(tId).createEntityManager();
	}

	public EntityManager createEntityManager(Map map) {
		Integer tId = getTenant();
		return getFactory(tId).createEntityManager(map);
	}

	public void close() {
		Integer tId = getTenant();
		getFactory(tId).close();
	}

	public boolean isOpen() {
		Integer tId = getTenant();
		return getFactory(tId).isOpen();
	}

	private Integer getTenant() {
		int id = (Math.random() < 0.5) ? 1 : 2; // for now
		return id; // TODO change this to read user info maybe thread local
	}

	public boolean exists(Integer id) {
		return factories.containsKey(id);
	}

	//
	public Cache getCache() {
		Integer tId = getTenant();
		return getFactory(tId).getCache();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		Integer tId = getTenant();
		return getFactory(tId).getCriteriaBuilder();
	}

	public Metamodel getMetamodel() {
		Integer tId = getTenant();
		return getFactory(tId).getMetamodel();
	}

	public PersistenceUnitUtil getPersistenceUnitUtil() {
		Integer tId = getTenant();
		return getFactory(tId).getPersistenceUnitUtil();
	}

	public Map<String, Object> getProperties() {
		Integer tId = getTenant();
		return getFactory(tId).getProperties();
	}

}
