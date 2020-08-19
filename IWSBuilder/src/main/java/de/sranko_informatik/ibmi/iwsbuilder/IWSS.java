/**
 * 
 */
package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.List;

/**
 * @author juraj
 *
 */
public class IWSS {
	protected IWSServer server;
	protected List<IWSService> services;
	
	public IWSServer getServer() {
		return server;
	}
	public void setServer(IWSServer server) {
		this.server = server;
	}
	public List<IWSService> getServices() {
		return services;
	}
	public void addServices(IWSService services) {
		this.services.add(services);
	}
	public void setServices(List<IWSService> services) {
		this.services = services;
	}
}
