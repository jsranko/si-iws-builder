package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;
import java.util.Map;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException, InterruptedException, IWSSExceptions {
		//String location = new String("resources\\swagger.json");
		//Swagger swagger = new SwaggerParser().read(location);
		//Map<String, io.swagger.models.Path> paths = swagger.getPaths();

		//System.out.println(swagger.toString());
		int exitcode;
		String location = new String(args[0]);
		
		IWSS iwss = getIWSS(location);
		
		IWS iws = new IWS(iwss.getServer());
		PaseCommand paseCmd;
		
		if (existServer(iws)) {
			
			if (isServerRunning(iws)) {
				System.out.println(String.format("Server %s wird gestoppt ...", iwss.getServer().getName()));
				paseCmd = iws.stopWebServicesServer();
				if (paseCmd.getExitCode() != 0) {
					throw new UnknownError();
				}				
			}
			
		}
		
		System.out.println(String.format("Server %s wird erstellt ...", iwss.getServer().getName()));
		if (iws.createWebServicesServer() != 0) {
			throw new UnknownError("Server könnte nicht erstellt werden.");
		}
		
		System.out.println(String.format("Server %s wird gestartet ...", iwss.getServer().getName()));
		if (iws.startWebServicesServer() != 0) {
			throw new UnknownError("Server könnte nicht gestartet werden.");
		}	
			
		System.out.println("Services werden installiert ...");
		for (IWSService service : iwss.getServices()) {
			System.out.println(String.format("Service %s wird installiert ...", service.getName()));
			if (iws.installWebService(service) != 0) {
				throw new UnknownError("Server könnte nicht installiert werden.");
			}	
		}

		getServerProperties(iws);
	}
	
	public static void getServerProperties (IWS iws) throws IOException, InterruptedException, IWSSExceptions {
		
		IWSWebServicesServerProperties serverProp = new IWSWebServicesServerProperties(iws);
		System.out.println(serverProp.getInstancePath());
		System.out.println(serverProp.getApplicationServer());
		System.out.println(serverProp.getApplicationServerPorts());
		System.out.println(serverProp.getSubsystem());
		System.out.println(serverProp.getJobname());
		System.out.println(serverProp.getRuntimeUserId());
		System.out.println(serverProp.getJVMVersion());
		System.out.println(serverProp.getJVMType());
		System.out.println(serverProp.getWebServicesRuntime());
		System.out.println(serverProp.getWebServicesInstallPath());
		System.out.println(serverProp.getContextRoot());
		System.out.println(serverProp.getJavaToolboxTracing());
		System.out.println(serverProp.getToolboxTraceOutputFileName());
		System.out.println(serverProp.getServerLogFileName());
		System.out.println(serverProp.getHTTPServerName());
		System.out.println(serverProp.getHTTPServerPorts());
		
	}
	
	private static IWSS getIWSS(String location) {
		
		IWSS iwss = new IWSS();
		
		try {
			iwss = new IWSSParser().read(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iwss;
		
	}
	
	private static boolean existServer (IWS iws) throws IOException, InterruptedException, IWSSExceptions {
		
		IWSWebServicesServerProperties serverProp = new IWSWebServicesServerProperties(iws);
		if (serverProp.getHTTPServerName() == null) {
			return false;
		}
		return true;
		
	}
	
	private static boolean isServerRunning (IWS iws) {
		
		return true;
		
	}
}
