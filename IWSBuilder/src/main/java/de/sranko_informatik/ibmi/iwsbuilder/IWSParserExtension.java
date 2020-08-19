package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

public interface IWSParserExtension {
	
    IWSS read(String location) throws IOException;
    
}
