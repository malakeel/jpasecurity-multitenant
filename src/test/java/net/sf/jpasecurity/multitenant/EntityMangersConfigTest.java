package net.sf.jpasecurity.multitenant;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

public class EntityMangersConfigTest {

	static EntityMangersConfig config;

	@BeforeClass
	public static void setUp() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException { 
		config = new EntityMangersConfig("tenants.xml");
	}

	
	/*
	 * private static void printXmlNode(Element ele) { try { TransformerFactory
	 * transfac = TransformerFactory.newInstance(); Transformer trans =
	 * transfac.newTransformer();
	 * trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	 * trans.setOutputProperty(OutputKeys.INDENT, "yes"); StringWriter sw = new
	 * StringWriter(); StreamResult result = new StreamResult(sw); DOMSource
	 * source = new DOMSource(ele); trans.transform(source, result);
	 * System.out.println(sw); } catch (TransformerException e) {
	 * e.printStackTrace(); } }
	 */

	
	
	@Test
	public void test() {
		HashMap<String, String> map = config.getConfigForTenant(1);
//		assertMapContents(map, "hibernate.connection.username", "sa");
		// assertMapContents(map, "hibernate.connection.username", "sa");

	}

	private void assertMapContents(HashMap<String, String> map, String k,
			String expected) {
		String actual = map.get(k);
		assertNotNull(actual);
		assertEquals(k + " wrong value", expected, actual);
	}
}
