package application;

import java.io.Serializable;

public class Agent implements Serializable {
	private static final long serialVersionUID = 1L;//for saving
	private int id;//agent id
    private String name;//agent name
    private String contactInfo;//agent contact information
    
    //agent constructer
	public Agent(int id, String name, String contactInfo) {
		super();
		this.id = id;
		this.name = name;
		this.contactInfo = contactInfo;
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


	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}


	@Override
	public String toString() {
		return "Agent [name=" + name + ", contactInfo=" + contactInfo + "]";
	}
}
