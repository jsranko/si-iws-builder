package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ast.ThrowStatement;


public class IWSWebServicesServerProperties {
	private IWS iws;
	private List<String> propertiesLines;

	public IWSWebServicesServerProperties() {
		super();
	}
	
	public IWSWebServicesServerProperties(IWS iws) throws IOException, InterruptedException, IWSSExceptions {
		super();
		getProperties(iws);
	}

	public void setIWS(IWS iws) throws IOException, InterruptedException, IWSSExceptions {
		getProperties(iws);
	}
	
	public String getInstancePath() {
	
		return searchValue("Instance path: ");
	}
	
	public String getApplicationServer() {
	
		return searchValue("Application server: ");
	}
	
	public String getApplicationServerPorts() {
	
		return searchValue("Application server ports: ");
	}	
	
	public String getSubsystem() {
	
		return searchValue("Subsystem: ");
	}	
	
	public String getJobname() {
	
		return searchValue("Job name: ");
	}		

	public String getRuntimeUserId() {
		
		return searchValue("Runtime user ID: ");
	}	
	
	public String getJVMVersion() {
		
		return searchValue("JVM version: ");
	}		
	
	public String getJVMType() {
		
		return searchValue("JVM type: ");
	}	
	
	public String getWebServicesRuntime() {
	
		return searchValue("Web services runtime: ");
	}	
	
	public String getWebServicesInstallPath() {

		return searchValue("Web services install path: ");
	}	
	
	public String getContextRoot() {
	
		return searchValue("Context root: ");
	}	
	
	public String getJavaToolboxTracing() {
	
		return searchValue("Java toolbox tracing: ");
	}	
	
	public String getToolboxTraceOutputFileName() {
	
		return searchValue("Toolbox trace output file name: ");
	}	
	
	public String getServerLogFileName() {
	
		return searchValue("Server log file name: ");
	}	
	
	public String getHTTPServerName() {
	
		return searchValue("HTTP server name: ");
	}	
	
	public String getHTTPServerPorts() {
	
		return searchValue("HTTP server ports: ");
	}	
	
	private String searchValue(String key) {
		
	    for (String element : this.propertiesLines){
	    	if (element.contains(key)){
	    		return element.substring(element.indexOf(key) + key.length());
	    	}
	    }
		return null;
	}
	
	private String getProperties(IWS iws) throws IOException, InterruptedException, IWSSExceptions {
		PaseCommand paseCmd;
		this.iws = iws;
		
		paseCmd = iws.getWebServicesServerProperties();
		if (paseCmd.getExitCode() != 0) {
			throw new IWSSExceptions("x");
		}
		
		this.propertiesLines = IOUtils.readLines(paseCmd.getOutput(), "UTF-8");
		return null;
	}
}


