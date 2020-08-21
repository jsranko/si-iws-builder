package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.HashMap;
import java.util.List;

public class IWSProperties {

	protected HashMap<String, String> attributes;
	protected List<IWSResponse> responses;

	
	public IWSProperties() {
		super();
		this.attributes = new HashMap<String, String>();
	}	
	
	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttributes(String attribute, String attributeValue) {
		this.attributes.put(attribute, attributeValue);
	}

	public List<IWSResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<IWSResponse> responses) {
		this.responses = responses;
	}	
	
	public void addResponses(IWSResponse response) {
		this.responses.add(response);
	}		
}
