package com.movies;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler; 
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartingPage extends Application{

    private static Stage stage;
    
    @Override
    public void start(Stage startingStage){

        stage = startingStage;

        //Defining the layout
        BorderPane root = new BorderPane();
        VBox centerBox = new VBox(15);
        

        Label newHereLabel = new Label("Are you new here? ");
        Label alreadyLabel = new Label("Already have an account? ");
        Button signInButton = new Button("Sign in");
        Button logInButton = new Button("Log in");

        //Setting how the button and the background looks
        signInButton.setStyle("-fx-background-color: #232323; -fx-text-fill: white;");
        logInButton.setStyle("-fx-background-color: #232323; -fx-text-fill: white;");
        newHereLabel.setStyle("-fx-text-fill: white;");
        alreadyLabel.setStyle("-fx-text-fill: white;");
        root.setStyle("-fx-backgroung-color: #000000;");
        centerBox.setStyle("-fx-backgroung-color: #000000;");

        //Setting the button actions
        signInButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){

                SignIn signIn = new SignIn();

                //Setting the new scene
                Scene nextScene = new Scene(signIn.getContent(), 600, 400);
                
                changeScene(nextScene);
                
            }
        });

        logInButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

                LogIn logIn = new LogIn();

                //Setting the new scene
                Scene nextScene = new Scene(logIn.getContent(), 600,400);
                changeScene(nextScene);
            }
        });

        //Adding the button and the labels to the pane
        centerBox.getChildren().addAll(newHereLabel, signInButton, alreadyLabel, logInButton);
        root.setCenter(centerBox);


        //Setting the current Scene
        Scene currentScene = new Scene(root, 600, 400);
        currentScene.setFill(Color.web("#0a0a0a"));

        //Adding this scene to the stage
        stage.setScene(currentScene);
        stage.show();

    }

    //The method to change the scene
    public static void changeScene(Scene scn){
        stage.setScene(scn);
        scn.setFill(Color.web("#0a0a0a"));
    }
}
