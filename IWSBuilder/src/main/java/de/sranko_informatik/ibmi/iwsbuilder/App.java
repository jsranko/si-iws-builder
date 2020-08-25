package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.swagger.models.Swagger;
import io.swagger.util.Json;

/**
 * Hello world!
 *
 */
public class App {
	private String iwssLocation = null;

	public App() {
	}

	public static void main(String[] args) {
		new App().parseArguments(args).build();
	}

	private App parseArguments(String[] args) {

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if ("-h".equals(arg) || "--help".equals(arg)) {
				printUsage();
			} else {
				this.iwssLocation = arg;
				if (this.iwssLocation == null) {
					printError("Missing iwss location.");
				}
			}
		}

		return this;

	}

	private void build() {

		String iwssLocation = new String(this.iwssLocation);

		IWSS iwss = getIWSS(iwssLocation);
		if (iwss == null) {
			printError("iwss file not found.");
		}

		IWS iws = new IWS(iwss.getServer());

		try {
			if (existServer(iws)) {

				if (isServerRunning(iws)) {
					System.out.println(String.format("Server %s wird gestoppt ...", iwss.getServer().getName()));
					if (iws.stopWebServicesServer() != 0) {
						// throw new UnknownError(iws.getOutputAsString());
						System.out.println(String.format("Server %s bereits gestoppt.", iwss.getServer().getName()));
					}
				}

				System.out.println(String.format("Server %s wird gelöscht ...", iwss.getServer().getName()));
				if (iws.deleteWebServicesServer() != 0) {
					throw new UnknownError(iws.getOutputAsString());
				}
			}

			System.out.println(String.format("Server %s wird erstellt ...", iwss.getServer().getName()));
			if (iws.createWebServicesServer() != 0) {
				throw new UnknownError(iws.getOutputAsString());
			}

			System.out.println(String.format("Server %s wird gestartet ...", iwss.getServer().getName()));
			if (iws.startWebServicesServer() != 0) {
				throw new UnknownError(iws.getOutputAsString());
			}

			System.out.println("Services werden installiert ...");
			for (IWSSService service : iwss.getServices()) {
				System.out.println(String.format("Service %s wird installiert ...", service.getName()));
				if (iws.installWebService(service) != 0) {
					System.out.println(String.format("Service %s konnte nicht installiert werden, weil %s",
							service.getName(), iws.getOutputAsString()));
				}

				Swagger swagger;
				try {
					swagger = iws.getSwagger(service);
					swagger = iwss.updateSwagger(swagger);
					if (!writeToJson(swagger, iws.getSwaggerLocation(service))) {
						log(String.format("Swagger %s canot be writed", iws.getSwaggerLocation(service)));
					}
				} catch (IOException | InterruptedException e) {
					log("Unable extend swagger file", e);
				}

			}
			
		} catch (UnknownError | IOException | InterruptedException e1) {
			log("Unable to build iws server", e1);
		}

		// getServerProperties(iws);
		return;
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

	private static boolean existServer(IWS iws) throws IOException, InterruptedException {

		if (iws.getHTTPServerName() == null) {
			return false;
		}
		return true;

	}

	private static boolean isServerRunning(IWS iws) {

		return true;

	}

	public static void getServerProperties(IWS iws) throws IOException, InterruptedException {

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

	private static boolean writeToJson(Swagger swagger, String location) {

		String jsonOutput = Json.pretty(swagger);
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(location);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			outputStream.write(jsonOutput.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static void printUsage() {
		log("");
		log("Usage: [options] iwss");
		log("");
		log("Convert source into destination.");
		log("  iwss           relative or absolute path to the directory containing iwss file");
		log("");
		log("Options:");
		log("  -h, --help     display help information and exit");
		System.exit(0);
	}

	private static void log(String message) {
		System.out.println(message);
	}

	private static void log(String message, Throwable e) {
		System.out.println(message);
		e.printStackTrace();
	}

	private static void printError(String message) {
		log("");
		log(message);
		printUsage();
	}
}
