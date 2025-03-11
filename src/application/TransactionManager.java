package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


//same Comments as the other classes
public class TransactionManager {
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	private ClientManager clientManager;
	private PropertyManager propertyManager;
	public TransactionManager(ClientManager clientManager,PropertyManager propertyManager) {
		this.clientManager = clientManager;
		this.propertyManager = propertyManager;
		readTransactionsFromFile();
		
	}
	
	public void addTransaction(Transaction transaction) { 
		transactions.add(transaction);
	}
	
	
    public void updateTransaction(int id, Transaction updatedTransaction) {
    	for (int i = 0; i < transactions.size(); i++) {
			if(transactions.get(i).getId() == id) {
				transactions.remove(i);
				transactions.add(updatedTransaction);
			}
		}
    }
    
    
    public void deleteTransaction(int id) {
    	for (int i = 0; i < transactions.size(); i++) {
			if(transactions.get(i).getId() == id) {
				transactions.remove(i);
			}
		}
    }
    
    
    public List<Transaction> searchTransactionById(int id) { 
    	List<Transaction> tmp = new ArrayList<Transaction>();
    	for (Transaction transaction : transactions) {
			if(String.valueOf(transaction.getId()).contains(String.valueOf(id))) {
				tmp.add(transaction);
			}
		}
    	return tmp;
    }
    
    
    public List<Transaction> getTransactionsByStatusAndByType(String paymentStatus , String type) { 
    	List<Transaction> tmp = new ArrayList<>();
    	
    	for (Transaction transaction : transactions) {
			if(transaction.getPaymentStatus().contains(paymentStatus)) {
				tmp.add(transaction);
			}
		}
        return tmp;
    }
    
    public List<Transaction> getAllTransaction(){
    	return this.transactions;
    }
    
    
    private List<Transaction> anyDelete(List<Transaction> transactions,int id){
		for (int i = 0; i < transactions.size(); i++) {
			if(transactions.get(i).getId() == id) {
				transactions.remove(i);
				break;
			}
		}
		return transactions;
	}
    
    
    // Save data
    public void saveTransactionsToFile() {
        if (transactions == null || transactions.isEmpty()) {
            System.err.println("No transactions to save.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transactionsData.dat"))) {
            oos.writeObject(transactions);
            System.out.println("Saved " + transactions.size() + " transactions to file: " + "transactionsData.dat");
        } catch (IOException e) {
            System.err.println("Error saving transactions to file: " + e.getMessage());
        }
    }

    // Read data
    public void readTransactionsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transactionsData.dat"))) {
            transactions = (List<Transaction>) ois.readObject();
            System.out.println("Loaded " + transactions.size() + " transactions from file: " + "transactionsData.dat");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading transactions from file: " + e.getMessage());
        }
    }

    
    
    
    
    
    //GUI
    boolean completedButton_Clicked = false;
    boolean pendingButton_Clicked = false;
    boolean inProgressButton_Clicked = false;
    
    
    private BorderPane borderPane;
    public void setOnTrasactionButtonClicked(BorderPane borderPane,Pane paneFilter,Pane infoPane) {
    	this.borderPane = borderPane;
    	Button addButton = new Button("ADD");
        addButton.setLayoutX(745);
        addButton.setLayoutY(-20);
        addButton.setStyle(
        			"-fx-background-color: #2196F3;" +
        				"-fx-text-fill: white;" +       
        				"-fx-font-size: 16px;" +      
        				"-fx-font-weight: bold;" +      
        				"-fx-border-radius: 10;" +      
        				"-fx-background-radius: 10;"    
	            );
        addButton.setPrefWidth(150); 
        addButton.setPrefHeight(40); 
        addButton.setOnAction(event->{
        	createAddForm(null,infoPane);
        });
        Button editButton = new Button("EDIT");
        editButton.setLayoutX(915);
        editButton.setLayoutY(-20);
        editButton.setStyle(
        			"-fx-background-color: #2196F3;" + 
        				"-fx-text-fill: white;" +       
        				"-fx-font-size: 16px;" +      
        				"-fx-font-weight: bold;" +     
        				"-fx-border-radius: 10;" +     
        				"-fx-background-radius: 10;"     
	            );
        editButton.setPrefWidth(150); 
        editButton.setPrefHeight(40); 
        editButton.setDisable(true);
    	TextField idInput = new TextField();
    	idInput.setPromptText("Enter Transaction ID");
    	idInput.setLayoutY(-40);
    	idInput.textProperty().addListener((observable, oldValue, newValue) -> {
    		try {
    			if(idInput.getText().isEmpty()) {
        			createScrollableListTransaction(this.getAllTransaction(),infoPane,editButton,borderPane);
        		}
        		else {
        			createScrollableListTransaction(this.searchTransactionById(Integer.parseInt(idInput.getText())),infoPane,editButton,borderPane);
        		}
			} catch (Exception e2) {
				idInput.setText(idInput.getText().substring(0, idInput.getText().length() - 1));
			}
        });
        paneFilter.getChildren().clear();

        Button completedButton = new Button("Completed");
        setButtonToNormal(completedButton);
        Button pendingButton = new Button("Pending");
        setButtonToNormal(pendingButton);
        Button inProgressButton = new Button("In Progress");
        setButtonToNormal(inProgressButton);
        completedButton.setOnAction(e -> {
        	if(completedButton_Clicked) {
        		setButtonToNormal(completedButton);
        		createScrollableListTransaction(transactions, infoPane,editButton,borderPane);
        		completedButton_Clicked = false;
        	}
        	else {
        		setButtonToClicked(completedButton);
        		setButtonToNormal(pendingButton);
        		setButtonToNormal(inProgressButton);
        		createScrollableListTransaction(getTransactionsByStatusAndByType("Completed", null), infoPane,editButton,borderPane);
        		completedButton_Clicked = true;
        		pendingButton_Clicked = false;
        		inProgressButton_Clicked = false;
        	}
        	});
        pendingButton.setOnMouseClicked(e -> {
        	if(pendingButton_Clicked) {
        		setButtonToNormal(pendingButton);
        		createScrollableListTransaction(transactions, infoPane,editButton,borderPane);
        		pendingButton_Clicked = false;
        	}
        	else {
        		setButtonToClicked(pendingButton);
        		setButtonToNormal(completedButton);
        		setButtonToNormal(inProgressButton);
        		createScrollableListTransaction(getTransactionsByStatusAndByType("Pending", null), infoPane,editButton,borderPane);
        		pendingButton_Clicked = true;
        		completedButton_Clicked = false;
        		inProgressButton_Clicked = false;
        	}
        	});
        inProgressButton.setOnMousePressed(e -> {
        	if(inProgressButton_Clicked) {
        		setButtonToNormal(inProgressButton);
        		createScrollableListTransaction(transactions, infoPane,editButton,borderPane);
        		inProgressButton_Clicked = false;
        	}
        	else {
        		setButtonToClicked(inProgressButton);
        		setButtonToNormal(completedButton);
        		setButtonToNormal(pendingButton);
        		createScrollableListTransaction(getTransactionsByStatusAndByType("In Progress", null), infoPane,editButton,borderPane);
        		inProgressButton_Clicked = true;
        		pendingButton_Clicked = false;
        		completedButton_Clicked = false;
        	}
        	});
        HBox filterBox = new HBox(10,pendingButton,inProgressButton,completedButton);
        paneFilter.getChildren().addAll(filterBox,idInput,addButton,editButton);
        createScrollableListTransaction(this.getAllTransaction(),infoPane,editButton,borderPane);
    }
    
    
    private void createScrollableListTransaction(List<Transaction> items, Pane infoPane, Button mainEditButton,BorderPane borderPane) {
    	ContextMenu contextMenu = new ContextMenu();
    	MenuItem editButton = new MenuItem("Edit");
        MenuItem deleteButton = new MenuItem("Delete");
        editButton.setStyle("-fx-text-fill: white;");
        deleteButton.setStyle("-fx-text-fill: red;");
        contextMenu.getItems().addAll(editButton, deleteButton);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(items);
        ListView<Transaction> listView = new ListView<>(transactions);
        listView.setCellFactory(lv -> new ListCell<Transaction>() {
            @Override
            protected void updateItem(Transaction transaction, boolean empty) {
                super.updateItem(transaction, empty);
                if (empty || transaction == null) {
                    setText(null);
                } else {
                    // Assuming Transaction has getId(), getType(), and getPaymentState() methods
                    setText("ID: " + transaction.getId() + "            Type: " + transaction.getTransactionType() +
                            "            Payment State: " + transaction.getPaymentStatus());
                }
            }
        });
        listView.setOnMouseClicked(event -> {
        	mainEditButton.setDisable(false);
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
            	Transaction transaction = transactions.get(selectedIndex);
            	if (event.getButton() == MouseButton.SECONDARY) { 
            		deleteButton.setOnAction(e->{
            			this.deleteTransaction(transaction.getId());
            			ObservableList<Transaction> tmpTransaction = FXCollections.observableArrayList(anyDelete(items, transaction.getId()));
            			listView.getItems().clear();
            			listView.getItems().addAll(tmpTransaction);
            		});
            		editButton.setOnAction(e->{
                    	createAddForm(transaction, infoPane);
                    });
                    contextMenu.show(listView, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
            	mainEditButton.setOnAction(e->{
            		createAddForm(transaction, infoPane);
            	});
                switchToTransactionInfo(transaction, infoPane);
                listView.refresh();
            }
        });
        borderPane.setCenter(listView);
    }

    
    private void switchToTransactionInfo(Transaction transaction, Pane infoPane) {
        infoPane.getChildren().clear();
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setMaxWidth(350);
        container.setStyle(
            "-fx-background-color: #2b2b2b;" +
            "-fx-border-color: #3e3e3e;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.3, 0, 2);"
        );
        Label titleLabel = new Label("Transaction Details");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4db8ff; -fx-text-transform: uppercase;");
        container.getChildren().addAll(
            titleLabel,
            createSingleLineProperty("Transaction", transaction != null ? String.valueOf(transaction.getId()) : "N/A"),
            createPropertyLine("Property", transaction != null && transaction.getProperty() != null ? getPropertySummary(transaction.getProperty()) : "N/A"),
            createPropertyLine("Client", transaction != null && transaction.getClient() != null ? getClientSummary(transaction.getClient()) : "N/A"),
            createPropertyLine("Type", transaction != null ? transaction.getTransactionType() : "N/A"),
            createPropertyLine("Date", transaction != null ? new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()) : "N/A"),
            createPropertyLine("Amount", transaction != null ? String.format("$%,.2f", transaction.getAmount()) : "N/A"),
            createPropertyLine("Payment Status", transaction != null ? transaction.getPaymentStatus() : "N/A")
        );
        infoPane.getChildren().add(container);
    }

    
    private String getPropertySummary(Property property) {
        String agentName = property.getAssignedAgent() != null ? property.getAssignedAgent().getName() : "N/A";
        return String.format("%d -  %s -  %s - %.2f m² - $%,.2f - %s",
                property.getId(),
                property.getType(),
                property.getLocation(),
                property.getSize(),
                property.getPrice(),
                agentName);
    }

    
    private String getClientSummary(Client client) {
        return String.format("%d  -  %s  -  %s",
                client.getId(),
                client.getName(),
                client.getContactInfo());
    }

    
    private HBox createSingleLineProperty(String property, String value) {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setMaxWidth(350);
        Label propertyLabel = new Label(property + ": ");
        propertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #aaaaaa; -fx-text-transform: uppercase;");
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: normal; -fx-text-fill: #ffffff;");
        valueLabel.setWrapText(true);
        valueLabel.setMaxWidth(250);
        row.getChildren().addAll(propertyLabel, valueLabel);
        return row;
    }

    
    private VBox createPropertyLine(String property, String value) {
        VBox row = new VBox(3);
        row.setMaxWidth(350);
        Label propertyLabel = new Label(property + ":");
        propertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #aaaaaa; -fx-text-transform: uppercase;");
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: normal; -fx-text-fill: #ffffff;");
        valueLabel.setWrapText(true);
        valueLabel.setMaxWidth(320);
        row.getChildren().addAll(propertyLabel, valueLabel);
        return row;
    }


    private void setButtonToNormal(Button button) {
    	button.setStyle(
        	    "-fx-background-color: #3c3f41; " + 
        	    "-fx-text-fill: #ffffff; " +        
        	    "-fx-font-size: 20px; " +         
        	    "-fx-font-weight: bold; " +        
        	    "-fx-background-radius: 5; " +     
        	    "-fx-border-color: #5a5d5f; " +     
        	    "-fx-border-width: 1px; " +         
        	    "-fx-border-radius: 5; " +          
        	    "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 8, 0, 0, 2); " + 
        	    "-fx-pressed-color: #292c2e; "      
        	);
    }
    
    
    private void setButtonToClicked(Button button) {
    	button.setStyle(
    	        "-fx-background-color: #292c2e; " + 
    	        "-fx-text-fill: #ffffff; " +
    	        "-fx-font-size: 20px; " +
    	        "-fx-font-weight: bold; " +
    	        "-fx-background-radius: 5; " +
    	        "-fx-border-color: #5a5d5f; " +
    	        "-fx-border-width: 1px; " +
    	        "-fx-border-radius: 5; " +
    	        "-fx-effect: innershadow(three-pass-box, rgba(0, 0, 0, 0.6), 6, 0, 0, 0);"
    	    );
    }
    
    
    
    
    
    public void createAddForm(Transaction transaction, Pane addPane) {
        addPane.getChildren().clear();
        VBox root = new VBox(8);
        root.setLayoutX(-17);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #2b2b2b; -fx-border-radius: 10; -fx-background-radius: 10;");
        root.setPrefWidth(350); 
        TextField idField = new TextField();
        idField.setPromptText("Enter Transaction ID");
        idField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        idField.setPrefWidth(300);
        ComboBox<String> propertyField = new ComboBox<>();
        propertyField.setPromptText("Select Property");
        propertyField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        propertyField.setPrefWidth(300);
        ComboBox<String> clientField = new ComboBox<>();
        clientField.setPromptText("Select Client");
        clientField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        clientField.setPrefWidth(300);
        ComboBox<String> transactionTypeField = new ComboBox<>();
        transactionTypeField.getItems().addAll("Sale", "Rental");
        transactionTypeField.setPromptText("Select Transaction Type");
        transactionTypeField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white;");
        transactionTypeField.setPrefWidth(300);
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");
        datePicker.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white;");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");
        amountField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        amountField.setPrefWidth(300);
        ComboBox<String> paymentStatusField = new ComboBox<>();
        paymentStatusField.getItems().addAll("Paid", "Pending");
        paymentStatusField.setPromptText("Select Payment Status");
        paymentStatusField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white;");
        paymentStatusField.setPrefWidth(300);
        Button addButton = new Button("Add Transaction");
        addButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(40);
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        clearButton.setPrefWidth(150);
        clearButton.setPrefHeight(40);
        List<Property> properties = propertyManager.getAllPropreties();
        for (Property property : properties) {
            propertyField.getItems().add(property.getId() + " - " + property.getType()+ " - "+ property.getLocation()+ " - "+ property.getSize()+ " m² ");
        }
        List<Client> clients = clientManager.getAllClients();
        for (Client client : clients) {
            clientField.getItems().add(client.getId() + " - " + client.getName()+ " - " + client.getContactInfo());
        }
        if (transaction != null) {
            clearButton.setOnAction(e -> {
                propertyField.setValue(null);
                clientField.setValue(null);
                transactionTypeField.setValue(null);
                datePicker.setValue(null);
                amountField.clear();
                paymentStatusField.setValue(null);
            });
            idField.setText(String.valueOf(transaction.getId()));
            idField.setDisable(true);
            propertyField.setValue(transaction.getProperty().getId() + " - " + transaction.getProperty().getType());
            clientField.setValue(transaction.getClient().getId() + " - " + transaction.getClient().getName());
            transactionTypeField.setValue(transaction.getTransactionType());
            datePicker.setValue(transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            amountField.setText(String.valueOf(transaction.getAmount()));
            paymentStatusField.setValue(transaction.getPaymentStatus());
            addButton.setText("SAVE");
            addButton.setOnAction(e -> {
                try {
                    updateTransaction(transaction.getId(), new Transaction(
                            transaction.getId(),
                            propertyManager.searchPropertyById(Integer.parseInt(propertyField.getValue().split(" - ")[0])).get(0),
                            clientManager.searchClientById(Integer.parseInt(clientField.getValue().split(" - ")[0])).get(0),
                            transactionTypeField.getValue(),
                            Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Double.parseDouble(amountField.getText()),
                            paymentStatusField.getValue()
                    ));
                    createScrollableListTransaction(transactions, addPane, addButton, borderPane);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });
        } else {
            clearButton.setOnAction(e -> {
                idField.setDisable(false);
                idField.clear();
                propertyField.setValue(null);
                clientField.setValue(null);
                transactionTypeField.setValue(null);
                datePicker.setValue(null);
                amountField.clear();
                paymentStatusField.setValue(null);
            });
            addButton.setOnAction(e -> {
                try {
                    if (searchTransactionById(Integer.parseInt(idField.getText())).isEmpty()) {
                        addTransaction(new Transaction(
                                Integer.parseInt(idField.getText()),
                                propertyManager.searchPropertyById(Integer.parseInt(propertyField.getValue().split(" - ")[0])).get(0),
                                clientManager.searchClientById(Integer.parseInt(clientField.getValue().split(" - ")[0])).get(0),
                                transactionTypeField.getValue(),
                                Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                Double.parseDouble(amountField.getText()),
                                paymentStatusField.getValue()
                        ));
                        createScrollableListTransaction(transactions, addPane, addButton, borderPane);
                    } else {
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });
        }
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(addButton, clearButton);
        buttonContainer.setAlignment(Pos.CENTER);
        root.getChildren().addAll(idField, amountField, propertyField, clientField, transactionTypeField, paymentStatusField, datePicker, buttonContainer);
        addPane.getChildren().add(root);
    }

}
