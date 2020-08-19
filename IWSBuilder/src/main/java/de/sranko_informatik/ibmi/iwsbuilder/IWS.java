package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.File;
import java.io.IOException;

public class IWS {
	
	private String shellPath = new String("/QIBM/ProdData/OS/WebServices/bin");
	private String createWebServicesServer = new String("createWebServicesServer.sh");
	private String startWebServicesServer = new String("startWebServicesServer.sh");
	private String installWebService = new String("installWebService.sh");
	private String stopWebServicesServer = new String("stopWebServicesServer.sh");
	private String deleteWebServicesServer = new String("deleteWebServicesServer.sh");
	
	public String createWebServicesServer(IWSServer server) {
		
		String command = String.format("%s/%s -server %s -startingPort %n -userid %s -version %s", 
                shellPath, 
                createWebServicesServer,
                server.name, 
                server.port, 
                server.userId, 
                server.version);
		
		try {
			int exitCode = runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String startWebServicesServer(IWSServer server) {
		
		String command = String.format("%s/%s -server %s", 
                shellPath, 
                startWebServicesServer);
		
		try {
			int exitCode = runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String installWebService(IWSServer server) {
		
		String command = String.format("%s/%s -server %s -programObject %s -service %s", 
                shellPath, 
                installWebService);
		
		try {
			int exitCode = runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String stopWebServicesServer(IWSServer server) {
		
		String command = String.format("%s/%s -server %s", 
                shellPath, 
                stopWebServicesServer);
		
		try {
			int exitCode = runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
		
	}	
	
	public String deleteWebServicesServer(IWSServer server) {
		
		String command = String.format("%s/%s -server %s", 
                shellPath, 
                deleteWebServicesServer);
		
		try {
			int exitCode = runCommand(command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
		
	}	
	
	public int runCommand(String command) throws IOException, InterruptedException {
		
		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(new File(shellPath));
		builder.command("bash", "-c", command);
		Process process = builder.start();
		
		return process.waitFor();
		
	}		
}
