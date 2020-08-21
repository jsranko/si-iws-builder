package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws UnknownError {
		//String location = new String("resources\\swagger.json");
		//Swagger swagger = new SwaggerParser().read(location);
		//Map<String, io.swagger.models.Path> paths = swagger.getPaths();

		String swaggerLocation;
		String iwssLocation = new String(args[0]);
		
		IWSS iwss = getIWSS(iwssLocation);
		iwss = getIWSS("resources\\SIIIA.iwss");
		
		IWS iws = new IWS(iwss.getServer());

//		if (existServer(iws)) {
//			
//			if (isServerRunning(iws)) {
//				System.out.println(String.format("Server %s wird gestoppt ...", iwss.getServer().getName()));
//				if (iws.stopWebServicesServer() != 0) {
//					//throw new UnknownError(iws.getOutputAsString());
//					System.out.println(String.format("Server %s bereits gestoppt.", iwss.getServer().getName()));
//				}				
//			}
//			
//			System.out.println(String.format("Server %s wird gelöscht ...", iwss.getServer().getName()));
//			if (iws.deleteWebServicesServer() != 0) {
//				throw new UnknownError(iws.getOutputAsString());
//			}	
//		}
//		
//		System.out.println(String.format("Server %s wird erstellt ...", iwss.getServer().getName()));
//		if (iws.createWebServicesServer() != 0) {
//			throw new UnknownError(iws.getOutputAsString());
//		}
//		
//		System.out.println(String.format("Server %s wird gestartet ...", iwss.getServer().getName()));
//		if (iws.startWebServicesServer() != 0) {
//			throw new UnknownError(iws.getOutputAsString());
//		}	
			
		System.out.println("Services werden installiert ...");
		for (IWSService service : iwss.getServices()) {
			System.out.println(String.format("Service %s wird installiert ...", service.getName()));
//			if (iws.installWebService(service) != 0) {
//				System.out.println(String.format("Service %s konnte nicht installiert werden.", service.getName()));
//			}	
			
			Swagger swagger;
			try {
				swagger = iws.getSwagger(service);
				swagger = iwss.updateSwagger(swagger);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			
			//Swagger swagger = new SwaggerParser().read(swaggerLocation);
		}

		//getServerProperties(iws);		

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
	
	private static boolean existServer (IWS iws) throws IOException, InterruptedException {
		
		if (iws.getHTTPServerName() == null) {
			return false;
		}
		return true;
		
	}
	
	private static boolean isServerRunning (IWS iws) {
		
		return true;
		
	}
	
	public static void getServerProperties (IWS iws) throws IOException, InterruptedException {
		
		System.out.println(iws.getInstancePath());
		System.out.println(iws.getApplicationServer());
		System.out.println(iws.getApplicationServerPorts());
		System.out.println(iws.getSubsystem());
		System.out.println(iws.getJobname());
		System.out.println(iws.getRuntimeUserId());
		System.out.println(iws.getJVMVersion());
		System.out.println(iws.getJVMType());
		System.out.println(iws.getWebServicesRuntime());
		System.out.println(iws.getWebServicesInstallPath());
		System.out.println(iws.getContextRoot());
		System.out.println(iws.getJavaToolboxTracing());
		System.out.println(iws.getToolboxTraceOutputFileName());
		System.out.println(iws.getServerLogFileName());
		System.out.println(iws.getHTTPServerName());
		System.out.println(iws.getHTTPServerPorts());
		
	}
}
