package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.HashMap;

public class IWSMethode {

	protected HashMap<String, String> attributes;

	
	public IWSMethode() {
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
}
