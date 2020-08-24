package de.sranko_informatik.ibmi.iwsbuilder;

import java.util.ArrayList;
import java.util.List;

public class IWSSService {
	protected String name;
	protected String programObject;
	protected String userId;
	protected boolean detectFieldLengths;
	protected String serviceType;
	protected String host;
	protected String targetNamespace;
	protected String propertiesFile;
	protected String libraryList;
	protected String libraryListPosition;
	protected String transportMetadata;
	protected String transportHeaders;
	protected boolean useParamNameAsElementName;
	protected List<IWSSProperties> properties;
	
	
	public IWSSService() {
		super();
		this.properties = new ArrayList<IWSSProperties>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgramObject() {
		return programObject;
	}
	public void setProgramObject(String programObject) {
		this.programObject = programObject;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isDetectFieldLengths() {
		return detectFieldLengths;
	}
	public void setDetectFieldLengths(boolean detectFieldLengths) {
		this.detectFieldLengths = detectFieldLengths;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getTargetNamespace() {
		return targetNamespace;
	}
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}
	public String getPropertiesFile() {
		return propertiesFile;
	}
	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}
	public String getLibraryList() {
		return libraryList;
	}
	public List<IWSSProperties> getProperties() {
		return properties;
	}
	public void setProperties(List<IWSSProperties> properties) {
		this.properties = properties;
	}
	public void setLibraryList(String libraryList) {
		this.libraryList = libraryList;
	}
	public String getLibraryListPosition() {
		return libraryListPosition;
	}
	public void setLibraryListPosition(String libraryListPosition) {
		this.libraryListPosition = libraryListPosition;
	}
	public String getTransportMetadata() {
		return transportMetadata;
	}
	public void setTransportMetadata(String transportMetadata) {
		this.transportMetadata = transportMetadata;
	}
	public String getTransportHeaders() {
		return transportHeaders;
	}
	public void setTransportHeaders(String transportHeaders) {
		this.transportHeaders = transportHeaders;
	}
	public boolean isUseParamNameAsElementName() {
		return useParamNameAsElementName;
	}
	public void setUseParamNameAsElementName(boolean useParamNameAsElementName) {
		this.useParamNameAsElementName = useParamNameAsElementName;
	}
	public List<IWSSProperties> getMethods() {
		return properties;
	}
	public void setMethods(List<IWSSProperties> methods) {
		this.properties = methods;
	}
	public void addMethode(IWSSProperties methode) {
		this.properties.add(methode);
	}
}
