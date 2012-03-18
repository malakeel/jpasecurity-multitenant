package net.sf.jpasecurity.multitenant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Provides config for multiple entity manager in somefile.
 * 
 * @author Mansour Al Akeel
 * 
 * 
 */
class EntityMangersConfig {

	public Set<Integer> getIds() {
		return config.keySet();
	}

	private HashMap<Integer, HashMap<String, String>> config;

	private Element getConfigRoot(XPath xpath, String fileName)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		// TODO: change this to use streams
		// InputStream conf_file =
		// this.getClass().getResourceAsStream("/META-INF/tenants.xml");
		//
		// conf_file = this.getClass().getResourceAsStream("tenants.xml");

		File conf_file = new File("tenants.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);

		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(conf_file);

		return (Element) xpath.evaluate("t:tenants", doc, XPathConstants.NODE);
	}

	public EntityMangersConfig(String configFileName) {

		config = new HashMap<Integer, HashMap<String, String>>();

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new JpaSecurityNameSpace());

		Element configRoot;
		try {
			configRoot = this.getConfigRoot(xpath, configFileName);
			NodeList tenants = (NodeList) xpath.evaluate("t:tenant[@id]",
					configRoot, XPathConstants.NODESET);
			Integer id;

			for (int i = 0; i < tenants.getLength(); i++) {
				Element t = (Element) tenants.item(i);
				HashMap<String, String> tenantConfig = parseTenant(xpath, t);
				id = Integer.parseInt(t.getAttribute("id"));
				config.put(id, tenantConfig);

			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadFromConfig() {
		// SecureEntityManagerFactory factory = new SecurityManager()
	}

	public HashMap<String, String> getConfigForTenant(Integer tId) {
		return config.get(tId);
	}

	private HashMap<String, String> parseTenant(XPath xpath, Element tenant)
			throws XPathExpressionException {

		HashMap<String, String> tmp = new HashMap<String, String>();

		NodeList properties = (NodeList) xpath.evaluate("t:property", tenant,
				XPathConstants.NODESET);

		for (int i = 0; i < properties.getLength(); i++) {
			Element prop = (Element) properties.item(i);
			String name = prop.getAttribute("name");
			String value = prop.getAttribute("value");
			System.out.println(name + "=" + value);
			tmp.put(name, value);
		}

		return tmp;
	}

}
