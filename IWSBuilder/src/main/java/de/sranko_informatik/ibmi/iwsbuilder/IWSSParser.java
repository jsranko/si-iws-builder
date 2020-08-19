package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IWSSParser {
	
	public IWSS read(String location) throws IOException {
        IWSS output = new IWSSV1Parser().read(location);
		return output;
	}

}
