package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IWSSV1Parser implements IWSParserExtension {
	
	@Override
	public IWSS read(String location) throws IOException {
		String data = null;
		Path path;
		final String fileScheme = "file:";
		if (location.toLowerCase().startsWith(fileScheme)) {
            path = Paths.get(URI.create(location));
        } else {
            path = Paths.get(location);
        }
		if(Files.exists(path)) {
            data = FileUtils.readFileToString(path.toFile(), "UTF-8");
        }
		
		return convertToIWSS(data);
	}
	
	private IWSS convertToIWSS(String data) throws IOException {
		
        if (data != null) {
            JsonNode rootNode = null;
            if (data.trim().startsWith("{")) {
                ObjectMapper mapper = new ObjectMapper();
                rootNode = mapper.readTree(data);
            } 

            if (System.getProperty("debugParser") != null) {
                //LOGGER.info("\n\nSwagger Tree: \n"
                //    + ReflectionToStringBuilder.toString(rootNode, ToStringStyle.MULTI_LINE_STYLE) + "\n\n");
            }
            if(rootNode == null) {
                return null;
            }
            // must have swagger node set
            JsonNode serverNode = rootNode.get("server");
            if (serverNode == null) {
                return null;
            } else {
                IWSDeserializationResult result = new IWSDeserializer().deserialize(rootNode);

                IWSS convertValue = result.getIwsserver();
                if (System.getProperty("debugParser") != null) {
//                    LOGGER.info("\n\nSwagger Tree convertValue : \n"
//                        + ReflectionToStringBuilder.toString(convertValue, ToStringStyle.MULTI_LINE_STYLE) + "\n\n");
                }
                return convertValue;
            }
        } else {
            return null;
        }
		
	}
}
