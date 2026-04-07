package com.movies;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartingPage extends Application{
    
    private static Stage stage;
    
    @Override
    public void start(Stage startingStage){

        stage = startingStage;

        //Defining the layout
        VBox centerBox = new VBox(15);
        

        Label newHereLabel = new Label("Are you new here? ");
        Label alreadyLabel = new Label("Already have an account? ");
        Button signInButton = new Button("Sign in");
        Button logInButton = new Button("Log in");

        //Setting how the button and the background looks
        signInButton.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
        logInButton.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
        newHereLabel.setStyle("-fx-text-fill: #EAEAEA;");
        alreadyLabel.setStyle("-fx-text-fill: #EAEAEA;");

        //Setting the button actions
        signInButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){

                SignIn signIn = new SignIn();

                //Setting the new scene
                Scene nextScene =signIn.choose(startingStage);
                
                changeScene(nextScene);
                
            }
        });

        logInButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

                LogIn logIn = new LogIn();

                //Setting the new scene
                Scene nextScene =logIn.choose(startingStage);
                changeScene(nextScene);
            }
        });

        //Adding the button and the labels to the pane
        centerBox.getChildren().addAll(newHereLabel, signInButton, alreadyLabel, logInButton);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setStyle("-fx-background-color: #0B0F1A;");


        //Setting the current Scene
        Scene currentScene = new Scene(centerBox, 600, 400);
        currentScene.setFill(Color.web("#282B35"));

        //Adding this scene to the stage
        stage.setScene(currentScene);
        stage.setFullScreen(true);
        stage.show();

    }

    //The method to change the scene
    public static void changeScene(Scene scn){
        stage.setScene(scn);
        stage.setFullScreen(true);
        scn.setFill(Color.web("#0a0a0a"));
    }
}
