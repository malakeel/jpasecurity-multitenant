package net.sf.jpasecurity.multitenant;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.sf.jpasecurity.UserE;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import static junit.framework.Assert.*;

public class PersistenceTest {

	private static EntityManagerFactory factory;

	@BeforeClass
	public static void setUp() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		Properties props = new Properties();

		props.put("openjpa.ConnectionURL", "jdbc:derby:db/test1;create=true");
		props.put("openjpa.ConnectionDriverName", "org.apache.derby.jdbc.EmbeddedDriver");
//		props.put("openjpa.ConnectionUserName", "dbname");
//		props.put("openjpa.ConnectionPassword", "password");
		props.put("openjpa.jdbc.SynchronizeMappings", "buildSchema");
		props.put("openjpa.Log",
				"DefaultLevel=WARN, Runtime=INFO, Tool=INFO, SQL=TRACE");
		props.put("openjpa.RuntimeUnenhancedClasses", "supported");
		props.put("openjpa.MetaDataFactory", "jpa(Types=UserE.class)");

		factory = Persistence.createEntityManagerFactory("users");

	}

	@AfterClass
	public static void tearDown() {
		factory.close();
	}

	@Test
	public void testHowManyFactories() {


		EntityManager em = factory.createEntityManager();

		UserE user = new UserE();
		// user.setId(3);
		user.setName("Mansour");
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();

		em.close();

		Map<String, Object> m = em.getProperties();
		Iterator<String> keys = m.keySet().iterator();
		String k;
		while (keys.hasNext()) {
			k = keys.next();
			System.out.println(k + " = " + em.getProperties().get(k));
		}

	}

}
