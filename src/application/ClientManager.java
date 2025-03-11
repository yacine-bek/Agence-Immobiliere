package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ClientManager {
	private List<Client> clients = new ArrayList<Client>();//list of clients
	
	//contsructer that reads all previes clints
	public ClientManager() {
		readClientsFromFile();
	}
	
	//get client by index from the list
	public Client getClient(int n) {
		try {
			return clients.get(n);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	//add client to the list
	public void addClient(Client client) {
		
		clients.add(client);
		
	}
	
	//update a information of a client 
    public void updateClient(int id, Client updatedClient) {
    	for (int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getId() == id) {
				clients.remove(i);
				clients.add(updatedClient);
			}
		}
    }
    
    //cdelete a client from the list of clients
    public void deleteClient(int id) {
    	for (int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getId() == id) {
				clients.remove(i);
			}
		}
    }
    
    //returens a list of client that start with the same id
    public List<Client> searchClientById(int id) { 
    	List<Client> tmp = new ArrayList<>();
    	for (Client client : clients) {
    		if(String.valueOf(client.getId()).contains(String.valueOf(id))) {
				tmp.add(client);
			}
		}
    	return tmp;
    }
    //returens a list of client that start with the same Type
    public List<Client> searchClientByType(String type) { 
		List<Client> tmp = new ArrayList<Client>();
		for (Client client : clients) {
			if (client.getClientType().toLowerCase().contains(type.toLowerCase())) {
				tmp.add(client);
			}
		}
		return tmp;
	}
    //returens a list of client that start with the same Name
    public List<Client> searchClientByName(String name) { 
		List<Client> tmp = new ArrayList<Client>();
		for (Client client : clients) {
			if (client.getName().toLowerCase().contains(name.toLowerCase())) {
				tmp.add(client);
			}
		}
		return tmp;
	}
    
    //returns all client list
    public List<Client> getAllClients(){
    	return this.clients;
    }
    //delete a client from any list 
    private List<Client> anyDelete(List<Client> clients,int id){
		for (int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getId() == id) {
				clients.remove(i);
				break;
			}
		}
		return clients;
	}
    
    
    
 // Save data
    public void saveClientsToFile() {
        if (clients == null || clients.isEmpty()) {
            System.err.println("No clients to save.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clientsData.dat"))) {
            oos.writeObject(clients);
            System.out.println("Saved " + clients.size() + " clients to file: " + "clientsData.dat");
        } catch (IOException e) {
            System.err.println("Error saving clients to file: " + e.getMessage());
        }
    }

    // Read data
    public void readClientsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientsData.dat"))) {
            clients = (List<Client>) ois.readObject();
            System.out.println("Loaded " + clients.size() + " clients from file: " + "clientsData.dat");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading clients from file: " + e.getMessage());
        }
    }

    
   
    
    //GUI
    private BorderPane borderPane;
    public void setOnClientButtonClicked(BorderPane borderPane,Pane paneFilter,Pane infoPane) {
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
    	idInput.setPromptText("Enter Client ID");
    	idInput.setLayoutY(-40);
    	idInput.textProperty().addListener((observable, oldValue, newValue) -> {
    		try {
    			if(idInput.getText().isEmpty()) {
        			createScrollableListClient(this.getAllClients(),infoPane,editButton,borderPane);
        		}
        		else {
        			createScrollableListClient(this.searchClientById(Integer.parseInt(idInput.getText())),infoPane,editButton,borderPane);
        		}
			} catch (Exception e2) {
				idInput.setText(idInput.getText().substring(0, idInput.getText().length() - 1));
			}
        });
        paneFilter.getChildren().clear();
        TextField typeInput = new TextField();
        typeInput.setPromptText("Enter Client Type");
        typeInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListClient(this.searchClientByType(typeInput.getText()),infoPane,editButton,borderPane);
        });
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Client Name");
        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListClient(this.searchClientByName(nameInput.getText()),infoPane,editButton,borderPane);
        });
        HBox hBox = new HBox(10,nameInput,typeInput);
        paneFilter.getChildren().addAll(hBox,idInput,addButton,editButton);

        createScrollableListClient(this.getAllClients(),infoPane,editButton,borderPane);
    }
    
    
    private void createScrollableListClient(List<Client> items, Pane infoPane,Button mainEditButton,BorderPane borderPane) {
    	ContextMenu contextMenu = new ContextMenu();
    	MenuItem editButton = new MenuItem("Edit");
        MenuItem deleteButton = new MenuItem("Delete");
        editButton.setStyle("-fx-text-fill: white;");
        deleteButton.setStyle("-fx-text-fill: red;");
        contextMenu.getItems().addAll(editButton, deleteButton);
        ObservableList<Client> clients = FXCollections.observableArrayList(items);
        ListView<Client> listView = new ListView<>(clients);
        listView.setCellFactory(lv -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setText(null);
                } else {
                    setText("ID: " + client.getId() + "            Name: " + client.getName() + "            Type: " + client.getClientType());
                }
            }
        });
        listView.setOnMouseClicked(event -> {
        	mainEditButton.setDisable(false);
        	
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Client client = clients.get(selectedIndex);
                if (event.getButton() == MouseButton.SECONDARY) { 
            		deleteButton.setOnAction(e->{
            			deleteClient(client.getId());
            			ObservableList<Client> tmpClients = FXCollections.observableArrayList(anyDelete(items, client.getId()));
            			listView.getItems().clear();
            			listView.getItems().addAll(tmpClients);
            		});
            		editButton.setOnAction(e->{
                    	createAddForm(client, infoPane);
                    });
                    contextMenu.show(listView, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
                mainEditButton.setOnAction(e->{
            		createAddForm(client, infoPane);
            	});
                switchToClientInfo(client, infoPane);
                listView.refresh();
            }
        });
        borderPane.setCenter(listView);
    }    
    
    
    public void switchToClientInfo(Client client, Pane infoPane) {
        infoPane.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        Label titleLabel = new Label("Client Details");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4db8ff;");
        Label idPropertyLabel = new Label("ID:");
        idPropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
        Label idValueLabel = new Label(client != null ? String.valueOf(client.getId()) : "N/A");
        idValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
        Label namePropertyLabel = new Label("Name:");
        namePropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
        Label nameValueLabel = new Label(client != null ? client.getName() : "N/A");
        nameValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
        Label typePropertyLabel = new Label("Client Type:");
        typePropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
        Label typeValueLabel = new Label(client != null ? client.getClientType() : "N/A");
        typeValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
        Label contactPropertyLabel = new Label("Contact Info:");
        contactPropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
        Label contactValueLabel = new Label(client != null ? client.getContactInfo() : "N/A");
        contactValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(idPropertyLabel, 0, 1);
        grid.add(idValueLabel, 1, 1);
        grid.add(namePropertyLabel, 0, 2);
        grid.add(nameValueLabel, 1, 2);
        grid.add(typePropertyLabel, 0, 3);
        grid.add(typeValueLabel, 1, 3);
        grid.add(contactPropertyLabel, 0, 4);
        grid.add(contactValueLabel, 1, 4);
        Label prefsTitleLabel = new Label("Preferences:");
        prefsTitleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
        grid.add(prefsTitleLabel, 0, 5);
        VBox prefsBox = new VBox(5);
        if (client != null && client.getPreferences() != null) {
            for (String pref : client.getPreferences()) {
                Label prefLabel = new Label("• " + pref);
                prefLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
                prefsBox.getChildren().add(prefLabel);
            }
        } else {
            Label noPrefsLabel = new Label("No preferences available.");
            noPrefsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #999999;");
            prefsBox.getChildren().add(noPrefsLabel);
        }
        grid.add(prefsBox, 1, 5);
        VBox card = new VBox(grid);
        card.setPadding(new Insets(10));
        card.setStyle(
            "-fx-background-color: #2b2b2b;" + 
            "-fx-border-color: #3e3e3e;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.3, 0, 2);"
        );
        infoPane.getChildren().add(card);
    }

    
    public void createAddForm(Client client, Pane addPane) {
        addPane.getChildren().clear();
        VBox root = new VBox(8);
        root.setLayoutX(-17);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #2b2b2b; -fx-border-radius: 10; -fx-background-radius: 10;");
        root.setPrefWidth(350); 
        String textFieldStyle = "-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;";
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");
        idField.setStyle(textFieldStyle);
        idField.setPrefWidth(300);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.setStyle(textFieldStyle);
        nameField.setPrefWidth(300);
        TextField clientTypeField = new TextField();
        clientTypeField.setPromptText("Enter Client Type");
        clientTypeField.setStyle(textFieldStyle);
        clientTypeField.setPrefWidth(300);
        TextField contactField = new TextField();
        contactField.setPromptText("Enter Contact Info");
        contactField.setStyle(textFieldStyle);
        contactField.setPrefWidth(300);
        TextArea preferencesArea = new TextArea();
        preferencesArea.setPromptText("Enter Preferences (comma-separated)");
        preferencesArea.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;  -fx-border-radius: 5; -fx-background-radius: 5;");
        preferencesArea.setPrefWidth(300);
        preferencesArea.setPrefHeight(80);
        Button addButton = new Button("Add Client");
        addButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setPrefWidth(140);
        addButton.setPrefHeight(35);
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        clearButton.setPrefWidth(140);
        clearButton.setPrefHeight(35);
        if (client != null) {
            idField.setText(client.getId() + "");
            idField.setDisable(true);
            nameField.setText(client.getName());
            clientTypeField.setText(client.getClientType());
            contactField.setText(client.getContactInfo());
            preferencesArea.setText(client.getPreferences() != null ? String.join(", ", client.getPreferences()) : "");
            addButton.setText("SAVE");
            addButton.setOnAction(e -> {
                try {
                    updateClient(client.getId(), new Client(
                        Integer.parseInt(idField.getText()),
                        nameField.getText(),
                        clientTypeField.getText(),
                        contactField.getText(),
                        Arrays.stream(preferencesArea.getText().split(","))
                              .map(String::trim)
                              .filter(s -> !s.isEmpty())
                              .toList()
                    ));
                    createScrollableListClient(clients, addPane, addButton, borderPane);
                } catch (Exception e2) {
                }
            });
        } else {
            clearButton.setOnAction(e -> {
                idField.setDisable(false);
                idField.clear();
                nameField.clear();
                clientTypeField.clear();
                contactField.clear();
                preferencesArea.clear();
            });
            addButton.setOnAction(e -> {
                try {
                    if (searchClientById(Integer.parseInt(idField.getText())).size() == 0) {
                        addClient(new Client(
                            Integer.parseInt(idField.getText()),
                            nameField.getText(),
                            clientTypeField.getText(),
                            contactField.getText(),
                            Arrays.stream(preferencesArea.getText().split(","))
                                  .map(String::trim)
                                  .filter(s -> !s.isEmpty())
                                  .toList()
                        ));
                        createScrollableListClient(clients, addPane, addButton, borderPane);
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
        root.getChildren().addAll(idField, nameField, clientTypeField, contactField, preferencesArea, buttonContainer);
        addPane.getChildren().add(root);
    }


}
