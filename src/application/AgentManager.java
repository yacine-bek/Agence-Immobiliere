package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AgentManager {
	
	private List<Agent> agents = new ArrayList<Agent>();//list of agents
	
	//constructur that have to read the previes agents that are saved in file
	public AgentManager() {
		readAgentsFromFile();
	}
	
	
	//get agent by index from the list
	public Agent getAgent(int n) {
		try {
			return agents.get(n);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	//add agent to the list
	public void addAgent(Agent agent) {
		agents.add(agent);
	}
	//update information of an agent
	public void updateAgent(int id, Agent updatedAgent) { 
		for (int i = 0; i < agents.size(); i++) {
			if(agents.get(i).getId() == id) {
				agents.remove(i);
				agents.add(updatedAgent);
			}
		}
	}
	//delete an agent from the list using ID
	public void deleteAgent(int id) {
		for (int i = 0; i < agents.size(); i++) {
			if(agents.get(i).getId() == id) {
				agents.remove(i);
			}
		}
	}
	//return a filtered list that start with the same id or one agent the have the full exact ID
	public List<Agent> searchAgentById(int id) { 
		List<Agent> tmp = new ArrayList<Agent>();
		for (Agent agent : agents) {
			if (String.valueOf(agent.getId()).contains(String.valueOf(id))) {
				tmp.add(agent);
			}
		}
		return tmp;
	}
	//return a filtered list that start with the same name or one agent the have the full exact name
	public List<Agent> searchAgentByName(String name) { 
		List<Agent> tmp = new ArrayList<Agent>();
		for (Agent agent : agents) {
			if (agent.getName().toLowerCase().contains(name.toLowerCase())) {
				tmp.add(agent);
			}
		}
		return tmp;
	}
	//return all agents list
	public List<Agent> getAllAgents() { 
		return this.agents;
	}
	//delete element from a agent list by ID (not like the previes one)
	private List<Agent> anyDelete(List<Agent> agents,int id){
		for (int i = 0; i < agents.size(); i++) {
			if(agents.get(i).getId() == id) {
				agents.remove(i);
				break;
			}
		}
		return agents;
	}
	
	
	// Save data
	public void saveAgentsToFile() {
	    if (agents == null || agents.isEmpty()) {
	        System.err.println("No agents to save.");
	        return;
	    }
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("agentsData.dat"))) {
	        oos.writeObject(agents);
	        System.out.println("Saved " + agents.size() + " agents to file: " + "agentsData.dat");
	    } catch (IOException e) {
	        System.err.println("Error saving agents to file: " + e.getMessage());
	    }
	}

	// Read data
	public void readAgentsFromFile() {
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("agentsData.dat"))) {
	        agents = (List<Agent>) ois.readObject();
	        System.out.println("Loaded " + agents.size() + " agents from file: " + "agentsData.dat");
	    } catch (IOException | ClassNotFoundException e) {
	        System.err.println("Error reading agents from file: " + e.getMessage());
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//GUI
	private BorderPane borderPane;
	
	
	public void setOnAgentButtonCliked(BorderPane borderPane,Pane paneFilter,Pane infoPane) {
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
    	idInput.setPromptText("Enter Agent ID");
    	idInput.setLayoutY(-40);
    	idInput.textProperty().addListener((observable, oldValue, newValue) -> {
    		try {
    			if(idInput.getText().isEmpty()) {
        			createScrollableListAgent(this.getAllAgents(),infoPane,editButton,borderPane);
        		}
        		else {
        			createScrollableListAgent(this.searchAgentById(Integer.parseInt(idInput.getText())),infoPane,editButton,borderPane);
        		}
			} catch (Exception e2) {
				idInput.setText(idInput.getText().substring(0, idInput.getText().length() - 1));
			}
    		
        });
        paneFilter.getChildren().clear();
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Agent Name");
        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListAgent(this.searchAgentByName(nameInput.getText()),infoPane,editButton,borderPane);
        });
        
        
        paneFilter.getChildren().addAll(nameInput,idInput,addButton,editButton);
        
        createScrollableListAgent(this.getAllAgents(),infoPane,editButton,borderPane);
	}
	
	
	private void createScrollableListAgent(List<Agent> items,Pane infoPane, Button mainEditButton,BorderPane borderPane) {
    	ContextMenu contextMenu = new ContextMenu();
    	MenuItem editButton = new MenuItem("Edit");
        MenuItem deleteButton = new MenuItem("Delete");
        editButton.setStyle("-fx-text-fill: white;");
        deleteButton.setStyle("-fx-text-fill: red;");
        contextMenu.getItems().addAll(editButton, deleteButton);
        ObservableList<Agent> agents = FXCollections.observableArrayList(items);
        ListView<Agent> listView = new ListView<>(agents);
        
        listView.setCellFactory(lv -> new ListCell<Agent>() {
            @Override
            protected void updateItem(Agent agent, boolean empty) {
                super.updateItem(agent, empty);
                if (empty || agent == null) {
                    setText(null);
                } else {
                    setText("ID: " + agent.getId() + "            Name: " + agent.getName());
                }
            }
        });
        listView.setOnMouseClicked(event->{
        	mainEditButton.setDisable(false);
        	int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        	if (selectedIndex != -1) {
                Agent agent = agents.get(selectedIndex);
                if (event.getButton() == MouseButton.SECONDARY) { 
            		deleteButton.setOnAction(e->{
            			deleteAgent(agent.getId());
            			ObservableList<Agent> tmpAgents = FXCollections.observableArrayList(anyDelete(items, agent.getId()));
            			listView.getItems().clear();
            			listView.getItems().addAll(tmpAgents);
            		});
            		editButton.setOnAction(e->{
                    	createAddForm(agent, infoPane);
                    });
                    contextMenu.show(listView, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
                mainEditButton.setOnAction(e->{
            		createAddForm(agent, infoPane);
            	});
                switchToAgentInfo(agent, infoPane);
                listView.refresh();
            }
        });
        borderPane.setCenter(listView);
    }
	
	
	private void switchToAgentInfo(Agent agent, Pane infoPane) {
	    infoPane.getChildren().clear();
	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(20));
	    Label titleLabel = new Label("Agent Details");
	    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4db8ff;");
	    Label idPropertyLabel = new Label("ID:");
	    idPropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
	    Label idValueLabel = new Label(agent != null ? String.valueOf(agent.getId()) : "N/A");
	    idValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
	    Label namePropertyLabel = new Label("Name:");
	    namePropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
	    Label nameValueLabel = new Label(agent != null ? agent.getName() : "N/A");
	    nameValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
	    Label contactPropertyLabel = new Label("Contact Info:");
	    contactPropertyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #cccccc;");
	    Label contactValueLabel = new Label(agent != null ? agent.getContactInfo() : "N/A");
	    contactValueLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e6e6e6;");
	    grid.add(titleLabel, 0, 0, 2, 1);
	    grid.add(idPropertyLabel, 0, 1);
	    grid.add(idValueLabel, 1, 1);
	    grid.add(namePropertyLabel, 0, 2);
	    grid.add(nameValueLabel, 1, 2);
	    grid.add(contactPropertyLabel, 0, 3);
	    grid.add(contactValueLabel, 1, 3);
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

	
	public void createAddForm(Agent agent,Pane addPane) {
	    addPane.getChildren().clear();
	    VBox root = new VBox(8);
	    root.setLayoutX(-17);
	    root.setPadding(new Insets(15));
	    root.setStyle("-fx-background-color: #2b2b2b; -fx-border-radius: 10; -fx-background-radius: 10;");
	    root.setPrefWidth(350); 
	    TextField idField = new TextField();
	    idField.setPromptText("Enter ID");
	    idField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
	    idField.setPrefWidth(300);
	    TextField nameField = new TextField();
	    nameField.setPromptText("Enter Name");
	    nameField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
	    nameField.setPrefWidth(300); 
	    TextField contactField = new TextField();
	    contactField.setPromptText("Enter Contact Info");
	    contactField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
	    contactField.setPrefWidth(300); 
	    Button addButton = new Button("Add Agent");
	    addButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
	    addButton.setPrefWidth(150); 
	    addButton.setPrefHeight(40); 
	    Button clearButton = new Button("Clear");
	    clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
	    clearButton.setPrefWidth(150); 
	    clearButton.setPrefHeight(40);
	    if(agent!=null) {
	    	clearButton.setOnAction(e -> {
	    		nameField.clear();
		        contactField.clear();
	    	});
	    	idField.setText(agent.getId()+"");
	    	idField.setDisable(true);
	    	nameField.setText(agent.getName());
	    	contactField.setText(agent.getContactInfo());
	    	addButton.setText("SAVE");
	    	addButton.setOnAction(e->{
		    	try {
		    		updateAgent(Integer.parseInt(idField.getText()), new Agent(Integer.parseInt(idField.getText()),nameField.getText(),contactField.getText()));
		    		createScrollableListAgent(agents, addPane, addButton,borderPane);
		    		saveAgentsToFile();
				} catch (Exception e2) {
				}
		    });
	    }
	    else {
	    	clearButton.setOnAction(e -> {
	    		idField.setDisable(false);
		        idField.clear();
		        nameField.clear();
		        contactField.clear();
	    	});
	    	addButton.setOnAction(e->{
		    	try {
		    		if(searchAgentById(Integer.parseInt(idField.getText())).size() == 0) {
		    			addAgent(new Agent(Integer.parseInt(idField.getText()),nameField.getText(),contactField.getText()));
			    		createScrollableListAgent(agents, addPane, addButton,borderPane);
			    		saveAgentsToFile();
		    		}
		    		else {
		    			//Eroor afiichage
		    		}
				} catch (Exception e2) {
				}
		    });
	    }
	    HBox buttonContainer = new HBox(10);
	    buttonContainer.getChildren().addAll(addButton, clearButton);
	    buttonContainer.setAlignment(Pos.CENTER);
	    root.getChildren().addAll(idField, nameField, contactField, buttonContainer);
	    addPane.getChildren().add(root);
	}

}
