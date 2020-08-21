package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class IWS {
	
	private IWSServer server;
	private String shellPath = new String("/QIBM/ProdData/OS/WebServices/bin");
	private String createWebServicesServer = new String("createWebServicesServer.sh");
	private String startWebServicesServer = new String("startWebServicesServer.sh");
	private String installWebService = new String("installWebService.sh");
	private String stopWebServicesServer = new String("stopWebServicesServer.sh");
	private String deleteWebServicesServer = new String("deleteWebServicesServer.sh");
	private String getWebServicesServerProperties = new String("getWebServicesServerProperties.sh");
	private InputStream output;
	private List<String> propertiesLines;
	
	public IWS(IWSServer server) {
		super();
		this.server = server;
	}
	
	public IWSServer getServer() {
		return server;
	}

	public void setServer(IWSServer server) {
		this.server = server;
	}

	
	public int createWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, createWebServicesServer));
		command.add("-server");
		command.add(this.server.name);
		command.add("-startingPort");
		command.add(String.valueOf(server.port));
		command.add("-userid");
		command.add(this.server.userId);
		command.add("-version");
		command.add(this.server.version);
		
		return run(command);
	}
	
	public int startWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, startWebServicesServer));
		command.add("-server");
		command.add(this.server.name);
	
		return run(command);
	}
	
	public int installWebService(IWSService service) throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, installWebService));
		command.add("-server");
		command.add(this.server.name);
		command.add("-programObject");
		command.add(service.getProgramObject());
		command.add("-service");
		command.add(service.getName());
		//command.add(String.format("-pcml"));
		//command.add(service.get);
		command.add("-userid");
		command.add(service.getUserId());
		command.add("-detectFieldLengths");
		command.add("-serviceType");
		command.add(service.getServiceType());
		command.add("-host");
		command.add(service.getServiceType());
		command.add("-targetNamespace");
		command.add(service.getTargetNamespace());
		command.add("-propertiesFile");
		command.add(service.getPropertiesFile());
		command.add("-libraryList");
		command.add(service.getLibraryList());
		command.add("-libraryListPosition");
		command.add(service.getLibraryListPosition());
		command.add("-transportMetadata");
		command.add(service.getTransportMetadata());
		if (service.isUseParamNameAsElementName()) {
			command.add("-useParamNameAsElementName");
		}
		command.add("-transportHeaders");
		command.add(String.valueOf(service.getTransportHeaders()));
		if (service.isUseParamNameAsElementName()) {
			command.add("-useParamNameAsElementName");
		}		
		if (this.server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
		
		return run(command);	
	}
	
	public int stopWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, stopWebServicesServer));
		command.add("-server");
		command.add(this.server.name);
		
		return run(command);
	}	
	
	public int deleteWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, deleteWebServicesServer));
		command.add("-server");
		command.add(this.server.name);
		if (server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
	
		return run(command);
	}	
	
	public int getWebServicesServerProperties() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, getWebServicesServerProperties));
		command.add("-server");
		command.add(this.server.name);
		if (this.server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
		
		return run(command);		
	}		
	
	public Swagger getSwagger(IWSService service) throws IOException, InterruptedException {
		
		//String path = String.format("%s/%s/META-INF/swagger.json", getWebServicesInstallPath(), service.getName());
		
		String path = new String("resources\\swagger.json");
		
		System.out.println(path);
		
		Swagger swagger = new SwaggerParser().read(path);
		
		return swagger;
		
	}		
	
	public int run(List<String> command) throws IOException, InterruptedException {
		
		ProcessBuilder builder = new ProcessBuilder(command);
		//builder.directory(new File(shellPath));
		
		Process process = builder.start();
		
		int exitCode = process.waitFor();
		
		builder.redirectOutput();
		
		if (exitCode != 0) {
			setOutput(process.getErrorStream());
		} else {
			setOutput(process.getInputStream());
		}
		
		return exitCode;
	}

	public InputStream getOutput() {
		return output;
	}
	
	public String getOutputAsString() throws IOException {
		return IOUtils.toString(output);
	}
	
	public void setOutput(InputStream output) {
		this.output = output;
	}	
	
	public String getInstancePath() throws IOException, InterruptedException {
		
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Instance path: ");
	}
	
	public String getApplicationServer() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Application server: ");
	}
	
	public String getApplicationServerPorts() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Application server ports: ");
	}	
	
	public String getSubsystem() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Subsystem: ");
	}	
	
	public String getJobname() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Job name: ");
	}		

	public String getRuntimeUserId() throws IOException, InterruptedException {
		
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Runtime user ID: ");
	}	
	
	public String getJVMVersion() throws IOException, InterruptedException {
		
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("JVM version: ");
	}		
	
	public String getJVMType() throws IOException, InterruptedException {
		
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("JVM type: ");
	}	
	
	public String getWebServicesRuntime() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Web services runtime: ");
	}	
	
	public String getWebServicesInstallPath() throws IOException, InterruptedException {

		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Web services install path: ");
	}	
	
	public String getContextRoot() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Context root: ");
	}	
	
	public String getJavaToolboxTracing() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Java toolbox tracing: ");
	}	
	
	public String getToolboxTraceOutputFileName() throws IOException, InterruptedException {
	
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("Toolbox trace output file name: ");
	}	
	
	public String getServerLogFileName() throws IOException, InterruptedException {
	
		return searchValue("Server log file name: ");
	}	
	
	public String getHTTPServerName() throws IOException, InterruptedException {
		
		if (this.propertiesLines == null) {
			getProperties();
		}
		return searchValue("HTTP server name: ");
	}	
	
	public String getHTTPServerPorts() throws IOException, InterruptedException {
	
		return searchValue("HTTP server ports: ");
	}	
	
	private String searchValue(String key) {
		
		if (this.propertiesLines == null) {
			return null;
		}
	    for (String element : this.propertiesLines){
	    	if (element.contains(key)){
	    		return element.substring(element.indexOf(key) + key.length());
	    	}
	    }
		return null;
	}
	
	private void getProperties() throws IOException, InterruptedException {

		if (getWebServicesServerProperties() != 0) {
			throw new UnknownError("xxx");
		}
		
		this.propertiesLines = IOUtils.readLines(getOutput(), "UTF-8");
		return;
	}
}
