package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IWSDeserializer {
	
    public IWSDeserializationResult deserialize(JsonNode rootNode) {
    	IWSDeserializationResult result = new IWSDeserializationResult();
        ParseResult rootParse = new ParseResult();
        
        JsonNode nodeServer = rootNode.get("server");
        
        IWSS iwss = new IWSS();
        IWSServer server = parseServer(nodeServer, rootParse);
        iwss.setServer(server);        		
        
        List<IWSService> services = parseServices(rootNode, rootParse);
        iwss.setServices(services);
        
        result.setIwsserver(iwss);
        result.setMessages(rootParse.getMessages());
        return result;
    }
    
	public IWSServer parseServer(JsonNode node, ParseResult result) {
    	String location = "server";
    	IWSServer iwsserver = new IWSServer();
    	if (node.getNodeType().equals(JsonNodeType.OBJECT)) {
    		ObjectNode on = (ObjectNode) node;
    		
    		String value = getString("name", node, true, location, result);
    		iwsserver.setName(value);
    		
    		Integer number = getInteger("port", node, true, location, result);
    		iwsserver.setPort(number);
    		
    		value = getString("userId", node, true, location, result);
    		iwsserver.setUserId(value);
    		
    		value = getString("version", node, false, location, result);
    		iwsserver.setVersion(value);
    		
    		boolean printErrorDetails = getBoolean("printErrorDetails", node, false, location, result);
    		iwsserver.setPrintErrorDetails(printErrorDetails);
    	}
		return iwsserver;
    }
	
	private List<IWSService> parseServices(JsonNode node, ParseResult result) {
	   String location = "services";
	   List<IWSService> services = new ArrayList<>();
	   JsonNode nodeServices = node.get("services");
	   ArrayNode arrayNode = getArray("services", nodeServices, true, location, result);
       if (arrayNode != null) {
           for (JsonNode n : arrayNode) {
               if (!n.isContainerNode()) {
                   result.invalidType(location, "services", "value", n);
               }
               IWSService service = parseService(n, result);
               services.add(service);
           }
       }
       return services;
   }   

   private IWSService parseService(JsonNode node, ParseResult result) {
	String location = "services";
   	IWSService service = new IWSService();
   	if (node.getNodeType().equals(JsonNodeType.OBJECT)) {
   		ObjectNode on = (ObjectNode) node;
   		
   		String value = getString("name", node, true, location, result);
   		service.setName(value);

   		value = getString("programObject", node, true, location, result);
   		service.setProgramObject(value);
   		
   		value = getString("userId", node, true, location, result);
   		service.setUserId(value);   
   		
   		boolean bool = getBoolean("detectFieldLengths", node, true, location, result);
   		service.setDetectFieldLengths(bool);    	
   		
   		value = getString("serviceType", node, true, location, result);
   		service.setServiceType(value);    
   		
   		value = getString("host", node, true, location, result);
   		service.setHost(value);  
   		
   		value = getString("targetNamespace", node, true, location, result);
   		service.setTargetNamespace(value); 
   		
   		value = getString("propertiesFile", node, true, location, result);
   		service.setPropertiesFile(value);  
   		
   		value = getString("libraryList", node, true, location, result);
   		service.setLibraryList(value);  
   		
   		value = getString("libraryListPosition", node, true, location, result);
   		service.setLibraryListPosition(value);  
   		
   		value = getString("transportMetadata", node, true, location, result);
   		service.setTransportMetadata(value);
   		
   		value = getString("transportHeaders", node, true, location, result);
   		service.setTransportHeaders(value);
   		
   		bool = getBoolean("useParamNameAsElementName", node, true, location, result);
   		service.setUseParamNameAsElementName(bool);  
   	}
   	
   	JsonNode nodeProperties = node.get("properties");
	ArrayNode arrayNode = getArray("properties", nodeProperties, true, location, result);
    if (arrayNode != null) {
        for (JsonNode n : arrayNode) {
            if (!n.isContainerNode()) {
                result.invalidType(location, "methods", "value", n);
            }
            IWSProperties methode = parseMethode(n, result);
            service.addMethode(methode);
        }
    }
	return service;
   }
   
	public IWSProperties parseMethode(JsonNode node, ParseResult result) {
    	String location = "methode";
    	IWSProperties methode = new IWSProperties();
    	if (node.getNodeType().equals(JsonNodeType.OBJECT)) {
    		ObjectNode on = (ObjectNode) node;
    		
    		Iterator<Entry<String, JsonNode>> nodes = on.fields();
    		  Map.Entry<String, JsonNode> nodeMap = null;
    		  while (nodes.hasNext()) {
    			  if (nodes.next().getKey() == "responses") {
    				  break;
    			  }
    			  nodeMap = nodes.next();
    			  String nodeMapKey = nodeMap.getKey();
    			  String nodeMapValue = nodeMap.getValue().asText();
    			  if (!nodeMap.getValue().getNodeType().equals(JsonNodeType.NULL)) {
    				  methode.addAttributes(nodeMapKey, nodeMapValue);
    			  } 
    		  }
    		  
    		methode.setResponses(parseResponses(node, result));
    	}
		return methode;
    }
	
	private List<IWSResponse> parseResponses(JsonNode node, ParseResult result) {
		   String location = "responses";
		   List<IWSResponse> responses = new ArrayList<>();
		   JsonNode nodeServices = node.get("responses");
		   ArrayNode arrayNode = getArray("responses", nodeServices, true, location, result);
	       if (arrayNode != null) {
	           for (JsonNode n : arrayNode) {
	               if (!n.isContainerNode()) {
	                   result.invalidType(location, "responses", "value", n);
	               }
	               IWSResponse service = parseResponse(n, result);
	               responses.add(service);
	           }
	       }
	       return responses;
	}   
	   
	public IWSResponse parseResponse(JsonNode node, ParseResult result) {
	   	String location = "response";
	   	IWSResponse response = new IWSResponse();
	   	if (node.getNodeType().equals(JsonNodeType.OBJECT)) {
	   		ObjectNode on = (ObjectNode) node;
	    		
	   		Iterator<Entry<String, JsonNode>> nodes = on.fields();
	   		Map.Entry<String, JsonNode> nodeMap = null;
	   		while (nodes.hasNext()) {

	   			nodeMap = nodes.next();
	   			System.out.println(nodeMap.getKey().concat(nodeMap.getValue().asText()));
	   			if (!nodeMap.getValue().getNodeType().equals(JsonNodeType.NULL)) {
	   				response.setHttpCode(nodeMap.getKey());
		   			response.setDescription(getString("description", nodeMap.getValue(), true, location, result));
		   			System.out.println(response.getDescription());
	   			} 
	   		}
	   	}
		return response;
	}	
    
	public String getString(String key, JsonNode node, boolean required, String location, ParseResult result) {
        String value = null;
        JsonNode v = node.get(key);
        if (node == null || v == null) {
            if (required) {
                result.missing(location, key);
                result.invalid();
            }
        } else if (!v.isValueNode()) {
            result.invalidType(location, key, "string", node);
        } else {
            value = v.asText();
        }
        return value;
    }
    
    public Integer getInteger(String key, JsonNode node, boolean required, String location, ParseResult result) {
        Integer value = null;
        JsonNode v = node.get(key);
        if (node == null || v == null) {
            if (required) {
                result.missing(location, key);
                result.invalid();
            }
        } else if (v.getNodeType().equals(JsonNodeType.NUMBER)) {
            value = v.intValue();
        } else if (!v.isValueNode()) {
            result.invalidType(location, key, "integer", node);
        }
        return value;
    } 
    
    public Boolean getBoolean(String key, JsonNode node, boolean required, String location, ParseResult result) {
        Boolean value = null;
        JsonNode v = node.get(key);
        if (node == null || v == null) {
            if (required) {
                result.missing(location, key);
                result.invalid();
            }
        } else {
            if (v.getNodeType().equals(JsonNodeType.BOOLEAN)) {
                value = v.asBoolean();
            } else if (v.getNodeType().equals(JsonNodeType.STRING)) {
                String stringValue = v.textValue();
                return Boolean.parseBoolean(stringValue);
            }
        }
        return value;
    }  
    
    public ArrayNode getArray(String key, JsonNode node, boolean required, String location, ParseResult result) {
        ArrayNode an = null;
        if (node == null) {
            if (required) {
                result.missing(location, key);
                result.invalid();
            }
        } else if (!node.getNodeType().equals(JsonNodeType.ARRAY)) {
            result.invalidType(location, key, "array", node);
        } else {
            an = (ArrayNode) node;
        }
        return an;
    }    

	protected static class ParseResult {
        private boolean valid = true;
        private Map<String, JsonNode> extra = new LinkedHashMap<String, JsonNode>();
        private Map<String, JsonNode> unsupported = new LinkedHashMap<String, JsonNode>();
        private Map<String, String> invalidType = new LinkedHashMap<String, String>();
        private List<String> warnings = new ArrayList<>();
        private List<String> missing = new ArrayList<String>();
        private List<String> unique = new ArrayList<>();

        public ParseResult() {
        }

        public void unsupported(String location, String key, JsonNode value) {
            unsupported.put(location, value);
        }

        public void extra(String location, String key, JsonNode value) {
            extra.put(location, value);
        }

        public void unique(String location, String key) {
            unique.add(location);
        }

        public void missing(String location, String key) {
            missing.add(location);
        }

        public void warning(String location, String key) {
            warnings.add(location);
        }

        public void invalidType(String location, String key, String expectedType, JsonNode value) {
            invalidType.put(location, expectedType);
        }

        public void invalid() {
            this.valid = false;
        }

        public Map<String, JsonNode> getUnsupported() {
            return unsupported;
        }

        public void setUnsupported(Map<String, JsonNode> unsupported) {
            this.unsupported = unsupported;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public Map<String, JsonNode> getExtra() {
            return extra;
        }

        public void setExtra(Map<String, JsonNode> extra) {
            this.extra = extra;
        }

        public Map<String, String> getInvalidType() {
            return invalidType;
        }

        public void setInvalidType(Map<String, String> invalidType) {
            this.invalidType = invalidType;
        }

        public List<String> getMissing() {
            return missing;
        }

        public void setMissing(List<String> missing) {
            this.missing = missing;
        }

        public List<String> getMessages() {
            List<String> messages = new ArrayList<String>();
//            for (Location l : extra.keySet()) {
//                String location = l.location.equals("") ? "" : l.location + ".";
//                String message = "attribute " + location + l.key + " is unexpected";
//                messages.add(message);
//            }
//            for (Location l : invalidType.keySet()) {
//                String location = l.location.equals("") ? "" : l.location + ".";
//                String message = "attribute " + location + l.key + " is not of type `" + invalidType.get(l) + "`";
//                messages.add(message);
//            }
//            for (Location l : missing) {
//                String location = l.location.equals("") ? "" : l.location + ".";
//                String message = "attribute " + location + l.key + " is missing";
//                messages.add(message);
//            }
//            for (Location l : warnings) {
//                String location = l.location.equals("") ? "" : l.location + ".";
//                String message = "attribute " + location + l.key;
//                messages.add(message);
//            }
//            for (Location l : unique) {
//                String location = l.location.equals("") ? "" : l.location + ".";
//                String message = "attribute " + location + l.key + " is repeated";
//                messages.add(message);
//            }
//            for (Location l : unsupported.keySet()) {
//                String location = l.location.equals("") ? "" : l.location + ".";
//                String message = "attribute " + location + l.key + " is unsupported";
//                messages.add(message);
//            }
            return messages;
        }
    }

}
