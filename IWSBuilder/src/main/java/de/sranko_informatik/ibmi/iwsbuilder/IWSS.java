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
	protected IWSSServer server;
	protected List<IWSSService> services;
	protected Swagger swagger;
	
	public IWSSServer getServer() {
		return server;
	}
	public void setServer(IWSSServer server) {
		this.server = server;
	}
	public List<IWSSService> getServices() {
		return services;
	}
	public void addServices(IWSSService services) {
		this.services.add(services);
	}
	public void setServices(List<IWSSService> services) {
		this.services = services;
	}
	public Swagger updateSwagger(Swagger swagger) {
		
		Map<String, Path> paths = updatePaths(swagger.getPaths());
		swagger.setPaths(paths);
		
		return swagger;
	}
	
	private Map<String, Path> updatePaths(Map<String, Path> paths) {
		Path pathURI;
		
		for (Map.Entry<String, Path> path : paths.entrySet()) {
			pathURI = path.getValue();
			
			Operation operation = null;
			
			if (pathURI.getGet() != null) {
				operation = updateOperation(path.getKey().substring(1), pathURI.getGet(), "GET");
				pathURI.setGet(operation);	
			}
			
			if (pathURI.getPut() != null) {
				operation = updateOperation(path.getKey().substring(1), pathURI.getPut(), "PUT");
				pathURI.setPut(operation);	
			}
			
			if (pathURI.getPost() != null) {
				operation = updateOperation(path.getKey().substring(1), pathURI.getPost(), "POST");
				pathURI.setPost(operation);	
			}
			
			if (pathURI.getDelete() != null) {
				operation = updateOperation(path.getKey().substring(1), pathURI.getDelete(), "DELETE");
				pathURI.setDelete(operation);	
			}
					
			paths.put(path.getKey(), pathURI);
		}	
		
		return paths;
		
	}
	
	private Operation updateOperation(String uri, Operation operation, String methode) {
		Map<String, Response> responses = operation.getResponses();
		
		for (Map.Entry<String, Response> entry : responses.entrySet()) {
			Response response = entry.getValue();
			response = updateResponse(operation, uri, entry.getKey(), methode, response);
			responses.put(entry.getKey(), response);
		}
		
		for (IWSSService service : this.services){
			if (service.getName().equals(operation.getOperationId())) {
				for (IWSSProperties property : service.getProperties()){
					if (property.getAttributes().containsValue(uri) && 
					    property.getAttributes().containsValue(methode)) {
						for (IWSSResponse entry : property.getResponses()){
							Response resp = new Response();
							resp.setDescription(entry.getDescription());
							responses.put(entry.getHttpCode(), resp);
						}					
					}
				}	
			}
	    }
		
		operation.setResponses(responses);
		return operation;
	}
	
	private Response updateResponse(Operation operation, String uri, String httpCode, String methode, Response response) {
		
		IWSSResponse iwssResponse = getIWSSResponseByHTTPCode(httpCode, operation.getOperationId(), uri, methode);
		if (iwssResponse != null) {
			response.setDescription(iwssResponse.getDescription());
		}		
		
		return response;
	}
		
	private IWSSResponse getIWSSResponseByHTTPCode(String httpcode, String operationId, String uri, String methode) {
		
		for (IWSSService service : this.services){
			if (service.getName().equals(operationId)) {
				for (IWSSProperties property : service.getProperties()){
					if (property.getAttributes().containsValue(uri) && 
					    property.getAttributes().containsValue(methode)) {
						for (IWSSResponse response : property.getResponses()){
							if (httpcode == response.getHttpCode()) {
								return response;
							}
						}					
					}
				}	
			}
	    }
		
		return null;
	}
}
