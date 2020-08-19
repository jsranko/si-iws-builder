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
	public static void main(String[] args) throws IOException {
		//String location = new String("resources\\swagger.json");
		//Swagger swagger = new SwaggerParser().read(location);
		//Map<String, io.swagger.models.Path> paths = swagger.getPaths();

		//System.out.println(swagger.toString());
		
		String location = new String("resources\\SIIIA.iwss");
		IWSS iwss = new IWSS();
		try {
			iwss = new IWSSParser().read(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IWS iws = new IWS();
		System.out.println(iws.createWebServicesServer(iwss.getServer()));
		
	}
}
