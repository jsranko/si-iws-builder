package de.sranko_informatik.ibmi.iwsbuilder;

public class IWSSServer {
	protected String name;
	protected int port;
	protected String userId;
	protected String version;
	protected boolean printErrorDetails;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isPrintErrorDetails() {
		return printErrorDetails;
	}
	public void setPrintErrorDetails(boolean printErrorDetails) {
		this.printErrorDetails = printErrorDetails;
	}
	
	
}
