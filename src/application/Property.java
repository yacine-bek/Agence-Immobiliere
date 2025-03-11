package application;

import java.io.Serializable;

public class Property implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;//id of property
	private String type;//type of property
	private double size;//size of property
	private double price;//price of the property
	private String location;//the location of the property
	private String description;//description of the property
	private Agent assignedAgent;//the agent that assingned with this property
	//the constructer of the property
	public Property(int id, String type, double size, double price, String location, String description,Agent assignedAgent) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.price = price;
		this.location = location;
		this.description = description;
		this.assignedAgent = assignedAgent;
	}
	
	
	//Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	//Type
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	//Size
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	
	
	//Price
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	//Location
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	//description
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	//AssingAgent
	public Agent getAssignedAgent() {
		return assignedAgent;
	}
	public void setAssignedAgent(Agent assignedAgent) {
		this.assignedAgent = assignedAgent;
	}


	@Override
	public String toString() {
		return "Property [type=" + type + ", size=" + size + ", price=" + price + ", location=" + location
				+ ", assignedAgent=" + assignedAgent + "]";
	}
}
