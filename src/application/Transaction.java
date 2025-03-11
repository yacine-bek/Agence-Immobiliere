package application;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L;//for saving in binary form
	
	private int id;//id of transaction
	private Property property;//its proprety
	private Client client;//the client of the property
	private String transactionType; // "Sale" "Rental"
	private Date date;
	private double amount;
	private String paymentStatus; // "Completed", "Pending", "In Progress"
	
	//Transaction constructer
	public Transaction(int id, Property property, Client client, String transactionType, Date date, double amount,String paymentStatus) {
		this.id = id;
		this.property = property;
		this.client = client;
		this.transactionType = transactionType;
		this.date = date;
		this.amount = amount;
		this.paymentStatus = paymentStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	@Override
	public String toString() {
		return "Transaction [property=" + property + ", client=" + client + ", transactionType=" + transactionType
				+ ", date=" + date + ", amount=" + amount + ", paymentStatus=" + paymentStatus + "]";
	}
	
	
	
}
