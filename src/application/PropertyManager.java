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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PropertyManager {
	private List<Property> properties = new ArrayList<Property>();//list of properties
	
	
	private AgentManager agentManager; //to get the agents assigned with the properties
	
	//constructer that take the agentsManager ftom the main to use it to get the agents needed
	//and read all prievies properties from the file
	public PropertyManager(AgentManager agentManager) {
		this.agentManager = agentManager;
		readPropertiesFromFile();
	}
	
	
	//get property by index from the list
	public Property getProperty(int n) {
		try {
			return properties.get(n);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
	//add property to the list
	public void addProperty(Property property) {
		properties.add(property);
		
	}
	//update a property by ID
    public void updateProperty(int id, Property updatedProperty) { 
    	for (int i = 0; i < properties.size(); i++) {
			if(properties.get(i).getId() == id) {
				properties.remove(i);
				properties.add(updatedProperty);
			}
		}
    }
    //delete property from the main list
    public void deleteProperty(int id) {
    	for (int i = 0; i < properties.size(); i++) {
			if(properties.get(i).getId() == id) {
				properties.remove(i);
			}
		}
    }
    
    //returs a list of properties with the same starting ID
    public List<Property> searchPropertyById(int id) { 
    	List<Property> tmp = new ArrayList<>();
    	for (Property property : properties) {
    		if(String.valueOf(property.getId()).contains(String.valueOf(id))) {
				tmp.add(property);
			}
		}
    	return tmp;
    }
    //search properties by ther Type, min-max price and location
    public List<Property> searchProperties(String type, String minPrice, String maxPrice, String location) {
        Double minPriceValue = null;
        Double maxPriceValue = null;
        
        try {
            if (minPrice != null && !minPrice.isEmpty()) {
                minPriceValue = Double.parseDouble(minPrice);
            }
        } catch (NumberFormatException e) {
            minPriceValue = null; // Default to no minimum price
        }
        
        try {
            if (maxPrice != null && !maxPrice.isEmpty()) {
                maxPriceValue = Double.parseDouble(maxPrice);
            }
        } catch (NumberFormatException e) {
            maxPriceValue = null; // Default to no maximum price
        }

        // Convert empty strings to null for other parameters
        if (type != null && type.isEmpty()) {
            type = null;
        }
        if (location != null && location.isEmpty()) {
            location = null;
        }

        // Filter properties
        List<Property> filteredProperties = new ArrayList<>();
        
        for (Property property : properties) {
            boolean matchesType = (type == null || property.getType().toLowerCase().contains(type.toLowerCase()));
            boolean matchesLocation = (location == null || property.getLocation().toLowerCase().contains(location.toLowerCase()));
            boolean matchesPrice = (minPriceValue == null || property.getPrice() >= minPriceValue)
                                && (maxPriceValue == null || property.getPrice() <= maxPriceValue);
            
            if (matchesType && matchesPrice && matchesLocation) {
                filteredProperties.add(property);
            }
        }
        
        return filteredProperties;
    }
    //return all properties list
    public List<Property> getAllPropreties(){
    	return this.properties;
    }
    
    private List<Property> anyDelete(List<Property> properties,int id){
		for (int i = 0; i < properties.size(); i++) {
			if(properties.get(i).getId() == id) {
				properties.remove(i);
				break;
			}
		}
		return properties;
	}
    
    
    // Save data
    public void savePropertiesToFile() {
        if (properties == null || properties.isEmpty()) {
            System.err.println("No properties to save.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("propretyData.dat"))) {
            oos.writeObject(properties);
            System.out.println("Saved " + properties.size() + " properties to file: " + "propretyData.dat");
        } catch (IOException e) {
            System.err.println("Error saving properties to file: " + e.getMessage());
        }
    }

    // Read data
    public void readPropertiesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("propretyData.dat"))) {
            properties = (List<Property>) ois.readObject();
            System.out.println("Loaded " + properties.size() + " properties from file: " + "propretyData.dat");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading properties from file: " + e.getMessage());
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //GUI
    private BorderPane borderPane;
    
    
    public void setOnPropertyButtonClicked(BorderPane borderPane,Pane paneFilter,Pane infoPane) {
    	this.borderPane = borderPane;
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
    	idInput.setPromptText("Enter Property ID");
    	idInput.setLayoutY(-40);
    	idInput.textProperty().addListener((observable, oldValue, newValue) -> {
    		try {
    			if(idInput.getText().isEmpty()) {
        			createScrollableListProperty(this.getAllPropreties(),infoPane,editButton,borderPane);
        		}
        		else {
        			createScrollableListProperty(this.searchPropertyById(Integer.parseInt(idInput.getText())),infoPane,editButton,borderPane);
        		}
			} catch (Exception e2) {
				idInput.setText(idInput.getText().substring(0, idInput.getText().length() - 1));
			}
        });
        paneFilter.getChildren().clear();
        TextField typeInput = new TextField();
        TextField minPriceInput = new TextField();
        TextField maxPriceInput = new TextField();
        TextField locationInput = new TextField();
        typeInput.setPromptText("Type");
        minPriceInput.setPromptText("Min Price");
        maxPriceInput.setPromptText("Max Price");
        locationInput.setPromptText("Location");
        typeInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListProperty(this.searchProperties(typeInput.getText(),minPriceInput.getText(),maxPriceInput.getText(),locationInput.getText()),infoPane,editButton,borderPane);
        });
        minPriceInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListProperty(this.searchProperties(typeInput.getText(),minPriceInput.getText(),maxPriceInput.getText(),locationInput.getText()),infoPane,editButton,borderPane);
        });
        maxPriceInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListProperty(this.searchProperties(typeInput.getText(),minPriceInput.getText(),maxPriceInput.getText(),locationInput.getText()),infoPane,editButton,borderPane);
        });
        locationInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	createScrollableListProperty(this.searchProperties(typeInput.getText(),minPriceInput.getText(),maxPriceInput.getText(),locationInput.getText()),infoPane,editButton,borderPane);
        });
        HBox filterBox = new HBox(10, typeInput, minPriceInput, maxPriceInput, locationInput);
        paneFilter.getChildren().addAll(filterBox,idInput,addButton,editButton);
        createScrollableListProperty(this.getAllPropreties(),infoPane,editButton,borderPane);
    }
    
    
    private void createScrollableListProperty(List<Property> items, Pane infoPane, Button mainEditButton,BorderPane borderPane) {
    	ContextMenu contextMenu = new ContextMenu();
    	MenuItem editButton = new MenuItem("Edit");
        MenuItem deleteButton = new MenuItem("Delete");
        editButton.setStyle("-fx-text-fill: white;");
        deleteButton.setStyle("-fx-text-fill: red;");
        contextMenu.getItems().addAll(editButton, deleteButton);
        ObservableList<Property> properties = FXCollections.observableArrayList(items);
        ListView<Property> listView = new ListView<>(properties);
        listView.setCellFactory(lv -> new ListCell<Property>() {
            @Override
            protected void updateItem(Property property, boolean empty) {
                super.updateItem(property, empty);
                if (empty || property == null) {
                    setText(null);
                } else {
                    setText("ID: " + property.getId() + "            Type: " + property.getType() +
                            "            Price: " + property.getPrice() + "            Location: " + property.getLocation());
                }
            }
        });

        listView.setOnMouseClicked(event -> {
        	mainEditButton.setDisable(false);
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Property property = properties.get(selectedIndex);
                if (event.getButton() == MouseButton.SECONDARY) { 
            		deleteButton.setOnAction(e->{
            			deleteProperty(property.getId());
            			ObservableList<Property> tmpProperty = FXCollections.observableArrayList(anyDelete(items, property.getId()));
            			listView.getItems().clear();
            			listView.getItems().addAll(tmpProperty);
            		});
            		editButton.setOnAction(e->{
                    	createAddForm(property, infoPane);
                    });
                    contextMenu.show(listView, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }
                mainEditButton.setOnAction(e->{
            		createAddForm(property, infoPane);
            	});
                switchToPropertyInfo(property, infoPane);
                listView.refresh();
            }
        });
        borderPane.setCenter(listView);	
    }

    
    private void switchToPropertyInfo(Property property, Pane infoPane) {
        infoPane.getChildren().clear();
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setMaxWidth(350); // Restrict width
        container.setStyle(
            "-fx-background-color: #2b2b2b;" +
            "-fx-border-color: #3e3e3e;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.3, 0, 2);"
        );
        Label titleLabel = new Label("Property Details");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4db8ff; -fx-text-transform: uppercase;");
        container.getChildren().addAll(
            titleLabel,
            createSingleLineProperty("ID", property != null ? String.valueOf(property.getId()) : "N/A"),
            createSingleLineProperty("Type", property != null ? property.getType() : "N/A"),
            createSingleLineProperty("Size (sq ft)", property != null ? String.valueOf(property.getSize()) : "N/A"),
            createSingleLineProperty("Price ($)", property != null ? String.format("$%,.2f", property.getPrice()) : "N/A"),
            createSingleLineProperty("Location", property != null ? property.getLocation() : "N/A"),
            createPropertyLine("Description", property != null ? property.getDescription() : "N/A"),
            createSingleLineProperty("Assigned Agent", property != null && property.getAssignedAgent() != null ? property.getAssignedAgent().getName() : "None")
        );
        container.setPrefWidth(350);
        container.setMinWidth(350);
        container.setMaxWidth(350);
        infoPane.getChildren().add(container);
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

    
    public void createAddForm(Property property, Pane addPane) {
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
        TextField typeField = new TextField();
        typeField.setPromptText("Enter Type");
        typeField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        typeField.setPrefWidth(300);
        TextField sizeField = new TextField();
        sizeField.setPromptText("Enter Size (sq.m)");
        sizeField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        sizeField.setPrefWidth(300);
        TextField priceField = new TextField();
        priceField.setPromptText("Enter Price");
        priceField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        priceField.setPrefWidth(300);
        TextField locationField = new TextField();
        locationField.setPromptText("Enter Location");
        locationField.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-prompt-text-fill: #777;");
        locationField.setPrefWidth(300);
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Enter Description");
        descriptionArea.setStyle("-fx-text-fill: white; -fx-prompt-text-fill: #777;");
        descriptionArea.setPrefWidth(300);
        descriptionArea.setPrefHeight(100);
        ComboBox<Agent> agentComboBox = new ComboBox<>();
        agentComboBox.setPromptText("Select Assigned Agent");
        agentComboBox.setStyle(
        	    "-fx-background-color: #1f1f1f;" +  
        	    "-fx-text-fill: white;" +        
        	    "-fx-border-color: #555;" +       
        	    "-fx-border-radius: 5;" +      
        	    "-fx-background-radius: 5;" +    
        	    "-fx-prompt-text-fill: #777;" +  
        	    "-fx-padding: 5 10 5 10;"       
        	);
        agentComboBox.setPrefWidth(300);
        agentComboBox.getItems().addAll(this.agentManager.getAllAgents()); 
        Button addButton = new Button("Add Property");
        addButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(40);
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        clearButton.setPrefWidth(150);
        clearButton.setPrefHeight(40);
        if (property != null) {
            clearButton.setOnAction(e -> {
                typeField.clear();
                sizeField.clear();
                priceField.clear();
                locationField.clear();
                descriptionArea.clear();
                agentComboBox.getSelectionModel().clearSelection();
            });
            idField.setText(property.getId() + "");
            idField.setDisable(true);
            typeField.setText(property.getType());
            sizeField.setText(String.valueOf(property.getSize()));
            priceField.setText(String.valueOf(property.getPrice()));
            locationField.setText(property.getLocation());
            descriptionArea.setText(property.getDescription());
            agentComboBox.setValue(property.getAssignedAgent());
            addButton.setText("SAVE");
            addButton.setOnAction(e -> {
                try {
                    updateProperty(Integer.parseInt(idField.getText()),new Property(
                            Integer.parseInt(idField.getText()),
                            typeField.getText(),
                            Double.parseDouble(sizeField.getText()),
                            Double.parseDouble(priceField.getText()),
                            locationField.getText(),
                            descriptionArea.getText(),
                            agentComboBox.getValue()
                    ));
                    createScrollableListProperty(properties, addPane, addButton, borderPane);
                } catch (Exception e2) {
                }
            });
        } else {
            clearButton.setOnAction(e -> {
                idField.setDisable(false);
                idField.clear();
                typeField.clear();
                sizeField.clear();
                priceField.clear();
                locationField.clear();
                descriptionArea.clear();
                agentComboBox.getSelectionModel().clearSelection();
            });
            addButton.setOnAction(e -> {
                try {
                    if (searchPropertyById(Integer.parseInt(idField.getText())).size() == 0) {
                        addProperty(new Property(
                                Integer.parseInt(idField.getText()),
                                typeField.getText(),
                                Double.parseDouble(sizeField.getText()),
                                Double.parseDouble(priceField.getText()),
                                locationField.getText(),
                                descriptionArea.getText(),
                                agentComboBox.getValue()
                        ));
                        createScrollableListProperty(properties, addPane, addButton, borderPane);
                    } else {
                    }
                } catch (Exception e2) {
                }
            });
        }
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(addButton, clearButton);
        buttonContainer.setAlignment(Pos.CENTER);
        root.getChildren().addAll(idField, typeField, sizeField, priceField, locationField, descriptionArea, agentComboBox, buttonContainer);
        addPane.getChildren().add(root);
    }

}
