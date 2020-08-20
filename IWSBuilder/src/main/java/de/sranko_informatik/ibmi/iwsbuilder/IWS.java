package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class IWS {
	
	private IWSServer server;
	private String shellPath = new String("/QIBM/ProdData/OS/WebServices/bin");
	private String createWebServicesServer = new String("createWebServicesServer.sh");
	private String startWebServicesServer = new String("startWebServicesServer.sh");
	private String installWebService = new String("installWebService.sh");
	private String stopWebServicesServer = new String("stopWebServicesServer.sh");
	private String deleteWebServicesServer = new String("deleteWebServicesServer.sh");
	private String getWebServicesServerProperties = new String("getWebServicesServerProperties.sh");

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

	
	public PaseCommand createWebServicesServer() throws IOException, InterruptedException {
		
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
		
		PaseCommand pase = new PaseCommand();
		pase.run(command);
		
		return pase;	
	}
	
	public PaseCommand startWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, startWebServicesServer));
		command.add("-server");
		command.add(this.server.name);

		PaseCommand pase = new PaseCommand();
		pase.run(command);
		
		return pase;	
	}
	
	public PaseCommand installWebService(IWSService service) throws IOException, InterruptedException {
		
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
		
		PaseCommand pase = new PaseCommand();
		pase.run(command);
		
		return pase;	
	}
	
	public PaseCommand stopWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, stopWebServicesServer));
		command.add("-server");
		command.add(this.server.name);

		PaseCommand pase = new PaseCommand();
		pase.run(command);
		
		return pase;	
	}	
	
	public PaseCommand deleteWebServicesServer() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, deleteWebServicesServer));
		command.add("-server");
		command.add(this.server.name);
		if (server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}

		PaseCommand pase = new PaseCommand();
		pase.run(command);
		
		return pase;	
	}	
	
	public PaseCommand getWebServicesServerProperties() throws IOException, InterruptedException {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, getWebServicesServerProperties));
		command.add("-server");
		command.add(this.server.name);
		if (this.server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}

		PaseCommand pase = new PaseCommand();
		pase.run(command);
		
		return pase;		
	}		
	
	
}
