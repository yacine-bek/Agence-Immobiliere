package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable{
	private static final long serialVersionUID = 1L;//for saving file in a binary file
	
	private int id;//client id
	private String name;//client name
	private String clientType;//client Type
	private String contactInfo;//client contact
	private List<String> preferences = new ArrayList<String>();//a list of a client prefrences
	
	//client constructer
	public Client(int id, String name, String clientType, String contactInfo, List<String> preferences) {
		this.id = id;
		this.name = name;
		this.clientType = clientType;
		this.contactInfo = contactInfo;
		this.preferences = preferences;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}


	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}


	public List<String> getPreferences() {
		return preferences;
	}
	
	public void setPreferences(List<String> preferences) {
		this.preferences = preferences;
	}


	@Override
	public String toString() {
		return "Client [name=" + name + ", clientType=" + clientType + ", contactInfo=" + contactInfo + ", preferences="
				+ preferences + "]";
	}
	
	
	
	
}
