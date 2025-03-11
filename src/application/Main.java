package application;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


//   2.271 lines


public class Main extends Application {
	public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
        	
            Pane mainRoot = new Pane();
            //declaration of all Managers we need
            AgentManager agentManager = new AgentManager();
            PropertyManager propertyManager = new PropertyManager(agentManager);
            ClientManager clientManager = new ClientManager();
            TransactionManager transactionManager = new TransactionManager(clientManager,propertyManager);
            
            
            Pane paneMenu = new Pane();
            paneMenu.setMinWidth(250);
            paneMenu.setMinHeight(768);
            
            
            //Buttons---------------------------------------------------
            Button agentButton = new Button("Agent");
            agentButton.getStyleClass().add("button");
            agentButton.setPrefWidth(250);
            agentButton.setPrefHeight(50); 
            Button clientButton = new Button("Client");
            clientButton.getStyleClass().add("button");
            clientButton.setPrefWidth(250);
            clientButton.setPrefHeight(50);
            Button propertyButton = new Button("Property");
            propertyButton.getStyleClass().add("button");
            propertyButton.setPrefWidth(250);
            propertyButton.setPrefHeight(50);
            Button transactionButton = new Button("Transaction");
            transactionButton.getStyleClass().add("button");
            transactionButton.setPrefWidth(250);
            transactionButton.setPrefHeight(50);
            Label labelAgencyManagement = new Label("Agency\nManagement");
            labelAgencyManagement.setLayoutX(22);
            labelAgencyManagement.getStyleClass().add("label");
            
            
            paneMenu.getStyleClass().add("sidebar");

            

            agentButton.setLayoutY(120);
            clientButton.setLayoutY(180);
            propertyButton.setLayoutY(240);
            transactionButton.setLayoutY(300);

            paneMenu.getChildren().addAll(agentButton, clientButton, propertyButton, transactionButton, labelAgencyManagement);

            Pane paneFilter = new Pane();

            BorderPane borderPane = new BorderPane();
            borderPane.setLayoutX(252);
            borderPane.setLayoutY(100);
            borderPane.setMinWidth(729);
            borderPane.setMinHeight(650);

            paneFilter.setLayoutX(252);
            paneFilter.setLayoutY(50);
            paneFilter.setMinWidth(1000);
            paneFilter.setMinHeight(200);

            Pane infoPane = new Pane();
            infoPane.setLayoutY(100);
            infoPane.setLayoutX(1000);

            infoPane.setMinWidth(500);
            infoPane.setMinHeight(568);
            
            agentManager.setOnAgentButtonCliked(borderPane, paneFilter, infoPane);
            
            
            agentButton.setOnAction(e -> {
            	agentManager.setOnAgentButtonCliked(borderPane, paneFilter, infoPane);
            });
            
            clientButton.setOnAction(ev -> {
            	clientManager.setOnClientButtonClicked(borderPane, paneFilter, infoPane);
            });
            
            propertyButton.setOnAction(evn -> {
            	propertyManager.setOnPropertyButtonClicked(borderPane, paneFilter, infoPane);
            });
            
            transactionButton.setOnAction(evnt -> {
            	transactionManager.setOnTrasactionButtonClicked(borderPane, paneFilter, infoPane);
            });

            
            
            
            mainRoot.getChildren().addAll(paneMenu, paneFilter, borderPane , infoPane);
            Scene scene = new Scene(mainRoot, 1366, 768);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            mainRoot.setOnMouseClicked(e->{
            	mainRoot.requestFocus();
            });
            
            
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F11) {
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                }
            });
            primaryStage.setScene(scene);
            
            primaryStage.show();
            
            
            agentManager.saveAgentsToFile();
            clientManager.saveClientsToFile();
            propertyManager.savePropertiesToFile();
            transactionManager.saveTransactionsToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
	
    
}