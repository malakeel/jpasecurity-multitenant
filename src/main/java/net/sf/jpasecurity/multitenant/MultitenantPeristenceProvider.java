package net.sf.jpasecurity.multitenant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import net.sf.jpasecurity.persistence.SecureEntityManagerFactory;
import net.sf.jpasecurity.persistence.SecurePersistenceProvider;

public class MultitenantPeristenceProvider extends SecurePersistenceProvider
		implements PersistenceProvider {

	EntityMangersConfig config = new EntityMangersConfig("tenant.xml");

	List<MultitenantEntityManagerFactory> factoriesList = new LinkedList<MultitenantEntityManagerFactory>();

	public MultitenantPeristenceProvider() {
		System.out.println("Initializing :" + this.getClass().getCanonicalName());
	}

	// this is called from SDK env (like unit testing)
	@Override
	public EntityManagerFactory createEntityManagerFactory(String emName,
			Map map) {

		MultitenantEntityManagerFactory entityManagerFactory = new MultitenantEntityManagerFactory();
		EntityManagerFactory factory;
		// avoid over writing any original values.
		Map tmp = new HashMap<String, String>();
		tmp.putAll(map);

		// for each tenant create a factory
		Set<Integer> tenantsId = config.getIds();

		for (Integer id : tenantsId) {

			Map<String, String> c = config.getConfigForTenant(id);

			tmp.putAll(c);

			factory = super.createEntityManagerFactory(emName, tmp);

			entityManagerFactory.addFactory(id,
					(SecureEntityManagerFactory) factory);

		}

		return entityManagerFactory;

	}

	// This is called one when starting the server.
	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(
			PersistenceUnitInfo info, Map map) {
		MultitenantEntityManagerFactory entityManagerFactory = new MultitenantEntityManagerFactory();
		EntityManagerFactory factory;
		// avoid over writing any original values.
		Map tmp = new HashMap<String, String>();
		tmp.putAll(map);

		// for each tenant create a factory
		Set<Integer> tenantsId = config.getIds();

		for (Integer id : tenantsId) {
			
			Map<String, String> c = config.getConfigForTenant(id);

			tmp.putAll(c);

			factory = super.createContainerEntityManagerFactory(info, tmp);

			entityManagerFactory.addFactory(id,
					(SecureEntityManagerFactory) factory);

		}

		return entityManagerFactory;
	}

//	public int getTenantId() {
//		// TODO Auto-generated method stub
//		return 1;
//	}

}
