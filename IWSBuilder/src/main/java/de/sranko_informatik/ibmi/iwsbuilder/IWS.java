package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class IWS {
	
	private String shellPath = new String("/QIBM/ProdData/OS/WebServices/bin");
	private String createWebServicesServer = new String("createWebServicesServer.sh");
	private String startWebServicesServer = new String("startWebServicesServer.sh");
	private String installWebService = new String("installWebService.sh");
	private String stopWebServicesServer = new String("stopWebServicesServer.sh");
	private String deleteWebServicesServer = new String("deleteWebServicesServer.sh");
	
	public int createWebServicesServer(IWSServer server) {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, createWebServicesServer));
		command.add("-server");
		command.add(server.name);
		command.add("-startingPort");
		command.add(String.valueOf(server.port));
		command.add("-userid");
		command.add(server.userId);
		command.add("-version");
		command.add(server.version);
		
		System.out.println(command);
		
		try {
			return runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int startWebServicesServer(IWSServer server) {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, startWebServicesServer));
		command.add("-server");
		command.add(server.name);

		System.out.println(command);
		
		try {
			return runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int installWebService(IWSServer server, IWSService service) {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, installWebService));
		command.add("-server");
		command.add(server.name);
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
		if (server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}
		
		System.out.println(command);
		
		try {
			return runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int stopWebServicesServer(IWSServer server) {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, stopWebServicesServer));
		command.add("-server");
		command.add(server.name);

		System.out.println(command);
		
		try {
			return runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
		
	}	
	
	public int deleteWebServicesServer(IWSServer server) {
		
		List<String> command = new ArrayList<>();
		command.add(String.format("%s/%s", shellPath, deleteWebServicesServer));
		command.add("-server");
		command.add(server.name);
		if (server.isPrintErrorDetails()) {
			command.add("-printErrorDetails");
		}


		System.out.println(command);
		
		try {
			return runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
		
	}	
	
	@SuppressWarnings("deprecation")
	public int runCommand(List<String> command) throws IOException, InterruptedException {
		
		ProcessBuilder builder = new ProcessBuilder(command);
		//builder.directory(new File(shellPath));
		
		Process process = builder.start();
		
		int exit = process.waitFor();
		
		builder.redirectOutput();
		
		System.out.println("------------------- Error -------------------------");
		System.out.println(IOUtils.toString(process.getErrorStream()));
		System.out.println("------------------- Output -------------------------");
		System.out.println(IOUtils.toString(process.getInputStream()));
		
		return exit;
		
	}		
}
