package net.sf.jpasecurity.multitenant;

public class NoSuchTenantException extends RuntimeException {

	public NoSuchTenantException(Integer tenantId) {
		super("Can not find Tenant config with id: " + tenantId);
	}
}
