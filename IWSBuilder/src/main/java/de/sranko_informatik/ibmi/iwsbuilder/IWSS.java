/**
 * 
 */
package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.List;
import java.util.Map;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;

/**
 * @author juraj
 *
 */
public class IWSS {
	protected IWSServer server;
	protected List<IWSService> services;
	protected Swagger swagger;
	
	public IWSServer getServer() {
		return server;
	}
	public void setServer(IWSServer server) {
		this.server = server;
	}
	public List<IWSService> getServices() {
		return services;
	}
	public void addServices(IWSService services) {
		this.services.add(services);
	}
	public void setServices(List<IWSService> services) {
		this.services = services;
	}
	public Swagger updateSwagger(Swagger swagger) {
		
		Map<String, Path> paths = updatePaths(swagger.getPaths());
		swagger.setPaths(paths);
		
		return null;
	}
	private Map<String, Path> updatePaths(Map<String, Path> paths) {
		Path pathURI;
		
		for (Map.Entry<String, Path> path : paths.entrySet()) {
			pathURI = path.getValue();
			
			for(Operation operation : pathURI.getOperations()){

				IWSResponse iwssResponse = getResponse(path.getKey(), operation.getOperationId());
				
				if (iwssResponse != null) {
					
					Map<String, Response> responses = updateResponses(operation.getResponses(), iwssResponse);
					operation.setResponses(responses);
					pathURI.setGet(operation);			
					paths.put(path.getKey(), pathURI);
				}
			}
			

		}	
		
		return paths;
		
	}
	private Map<String, Response> updateResponses(Map<String, Response> responses, IWSResponse iwssResponse) {
		
		for (Map.Entry<String, Response> entry : responses.entrySet()) {
			if (entry.getKey() == "200") {
				Response resp = entry.getValue();
				resp.setDescription("x");
				responses.put("200", resp);
			}
		    System.out.println(entry.getKey() + "/" + entry.getValue().getDescription());
		}
		return responses;
		
	}
	private IWSResponse getResponse(String path, String operation) {
		return null;
		
	}
}
