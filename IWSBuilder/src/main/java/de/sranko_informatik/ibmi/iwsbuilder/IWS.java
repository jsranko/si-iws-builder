package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class IWS {
	
	private IWSSServer server;
	private String shellPath = new String("/QIBM/ProdData/OS/WebServices/bin");
	private String createWebServicesServer = new String("createWebServicesServer.sh");
	private String startWebServicesServer = new String("startWebServicesServer.sh");
	private String installWebService = new String("installWebService.sh");
	private String stopWebServicesServer = new String("stopWebServicesServer.sh");
	private String deleteWebServicesServer = new String("deleteWebServicesServer.sh");
	private String getWebServicesServerProperties = new String("getWebServicesServerProperties.sh");
	private InputStream output;
	private List<String> propertiesLines;
	
	public IWS(IWSSServer server) {
		super();
		this.server = server;
	}
	
	public IWSSServer getServer() {
		return server;
	}

	public void setServer(IWSSServer server) {
		this.server = server;
	}

	
	public int createWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, createWebServicesServer));
		if (this.server.name != null) {
			command.add("-server");
			command.add(this.server.name);			
		}
		if (server.port != 0) {
			command.add("-startingPort");
			command.add(String.valueOf(server.port));			
		}
		if (this.server.userId != null) {
			command.add("-userid");
			command.add(this.server.userId);			
		}
		if (this.server.isNoHttp()) {
			command.add("-noHttp");
		}
		if (this.server.version != null) {
			command.add("-version");
			command.add(this.server.version);			
		}
		if (this.server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
		
		return run(command);
	}
	
	public int startWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, startWebServicesServer));
		if (this.server.name != null) {
			command.add("-server");
			command.add(this.server.name);			
		}
	
		return run(command);
	}
	
	public int installWebService(IWSSService service) throws IOException, InterruptedException {
		
		OutputStream propFileOS = new FileOutputStream(service.getPropertiesFile());
		OutputStreamWriter propFile = new OutputStreamWriter(propFileOS);
		
	    for (IWSSProperties werbservice : service.getProperties()){
	    	for (Entry<String, String> property : werbservice.getAttributes().entrySet()) {
	    		propFile.write (String.format("%s:%s\n", property.getKey(), property.getValue()));
			}
	    }	
		propFile.close();
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, installWebService));
		if (this.server.name != null) {
			command.add("-server");
			command.add(this.server.name);			
		}
		if (service.getProgramObject() != null) {
			command.add("-programObject");
			command.add(service.getProgramObject());			
		}
		if (service.getName() != null) {
			command.add("-service");
			command.add(service.getName());			
		}

		//command.add(String.format("-pcml"));
		//command.add(service.get);
		if (service.getUserId() != null) {
			command.add("-userid");
			command.add(service.getUserId());			
		}
		if (service.detectFieldLengths) {
			command.add("-detectFieldLengths");			
		}
		if (service.getServiceType() != null) {
			command.add("-serviceType");
			command.add(service.getServiceType());			
		}
		if (service.getServiceType() != null) {
			command.add("-host");
			command.add(service.getHost());			
		}
		if (service.getTargetNamespace() != null) {
			command.add("-targetNamespace");
			command.add(service.getTargetNamespace());			
		}
		if (service.getPropertiesFile() != null) {
			command.add("-propertiesFile");
			command.add(service.getPropertiesFile());			
		}
		if (service.getLibraryList() != null) {
			command.add("-libraryList");
			command.add(service.getLibraryList());			
		}
		if (service.getLibraryListPosition() != null) {
			command.add("-libraryListPosition");
			command.add(service.getLibraryListPosition());			
		}
		if (service.getTransportMetadata() != null) {
			command.add("-transportMetadata");
			command.add(service.getTransportMetadata());			
		}
		if (service.isUseParamNameAsElementName()) {
			command.add("-useParamNameAsElementName");
		}
		if (service.getTransportHeaders() != null) {
			command.add("-transportHeaders");
			command.add(String.valueOf(service.getTransportHeaders()));			
		}
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
		if (this.server.name != null) {
			command.add("-server");
			command.add(this.server.name);			
		}
		
		return run(command);
	}	
	
	public int deleteWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, deleteWebServicesServer));
		if (this.server.name != null) {
			command.add("-server");
			command.add(this.server.name);			
		}
		if (server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
	
		return run(command);
	}	
	
	public int getWebServicesServerProperties() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, getWebServicesServerProperties));
		if (this.server.name != null) {
			command.add("-server");
			command.add(this.server.name);			
		}
		if (this.server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
		
		return run(command);		
	}		

	public String getSwaggerLocation(IWSSService service) throws IOException, InterruptedException {
		
		String location = String.format("%s/%s/META-INF/swagger.json", getWebServicesInstallPath(), service.getName());
		
		//String location = new String("resources\\swagger.json");
		
		return location;
	}	
	
	public Swagger getSwagger(IWSSService service) throws IOException, InterruptedException {
		
		String path = getSwaggerLocation(service);
		
		Swagger swagger = new SwaggerParser().read(path);
		
		return swagger;
		
	}		
	
	public int run(List<String> command) throws IOException, InterruptedException {
		
		ProcessBuilder builder = new ProcessBuilder(command);
		
		System.out.println("Trace: " + command);
		
		Process process = builder.start();
		
		int exitCode = process.waitFor();
		
		//builder.redirectOutput();
		
		if (exitCode != 0) {
			//setOutput(process.getErrorStream());
			setOutput(process.getInputStream());
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
	
	public String getInstancePath() {
		
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Instance path: ");
	}
	
	public String getApplicationServer()  {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Application server: ");
	}
	
	public String getApplicationServerPorts() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Application server ports: ");
	}	
	
	public String getSubsystem() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Subsystem: ");
	}	
	
	public String getJobname() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Job name: ");
	}		

	public String getRuntimeUserId() {
		
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Runtime user ID: ");
	}	
	
	public String getJVMVersion() {
		
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("JVM version: ");
	}		
	
	public String getJVMType() {
		
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("JVM type: ");
	}	
	
	public String getWebServicesRuntime() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Web services runtime: ");
	}	
	
	public String getWebServicesInstallPath() {

		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Web services install path: ");
	}	
	
	public String getContextRoot() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Context root: ");
	}	
	
	public String getJavaToolboxTracing() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Java toolbox tracing: ");
	}	
	
	public String getToolboxTraceOutputFileName() {
	
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		return searchValue("Toolbox trace output file name: ");
	}	
	
	public String getServerLogFileName() throws IOException, InterruptedException {
	
		return searchValue("Server log file name: ");
	}	
	
	public String getHTTPServerName() {
		
		if (this.propertiesLines == null) {
			try {
				getProperties();
			} catch (IOException e) {
				return null;
			} catch (InterruptedException e) {
				return null;
			} catch (UnknownError e) {
				return null;
			}
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
			throw new UnknownError("IWS-Server does not exist");
		}
		
		this.propertiesLines = IOUtils.readLines(getOutput(), "UTF-8");
		return;
	}
}
