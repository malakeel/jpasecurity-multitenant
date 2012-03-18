package net.sf.jpasecurity.multitenant;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class JpaSecurityNameSpace implements NamespaceContext {

	public String getNamespaceURI(String prefix) {
		if (prefix == null)
			throw new NullPointerException("Null prefix");

		else if ("t".equals(prefix))
			return "http://http://jpasecurity.sourceforge.net/tenants";

		else if ("r".equals(prefix))
			return "http://http://jpasecurity.sourceforge.net/rules";

		
		else if ("xml".equals(prefix))
			return XMLConstants.XML_NS_URI;

		return XMLConstants.NULL_NS_URI;
	}

	// This method isn't necessary for XPath processing.
	public String getPrefix(String uri) {
		throw new UnsupportedOperationException();
	}

	// This method isn't necessary for XPath processing either.
	public Iterator<String> getPrefixes(String uri) {
		throw new UnsupportedOperationException();
	}

}
