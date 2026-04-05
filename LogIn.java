package com.movies;

import javafx.event.ActionEvent;
import javafx.event.EventHandler; 
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class LogIn{
    
    public Scene choose(Stage primaryStage){

        //Defining the layout
        VBox centerBox = new VBox(15);
        HBox buttonBox = new HBox(15);
        
        Label usernameLbl = new Label("USERNAME");
        TextField usernameTxt = new TextField();
        Label passwordLbl = new Label("PASSWORD");
        TextField passwordTxt = new TextField();
        Button continueBtn = new Button("Continue");
        Button goBackBtn = new Button("SignIn");
        Alert warning = new Alert(AlertType.WARNING);

        //Setting the warning
        warning.setTitle("WARNING!");
        warning.setHeaderText("LogIn Failed");
        warning.setContentText("Username or password is wrong.");

        //Setting the colors
        usernameLbl.setStyle("-fx-text-fill: #EAEAEA;");
        passwordLbl.setStyle("-fx-text-fill: #EAEAEA;");
        usernameTxt.setStyle("-fx-background-color: rgb(19, 19, 19); -fx-border-color: #EAEAEA;" 
                            + "-fx-border-width: 2px; -fx-text-fill: #EAEAEA;");
        passwordTxt.setStyle("-fx-background-color: rgb(19, 19, 19); -fx-border-color: #EAEAEA;"
                            + "-fx-border-width: 2px; -fx-text-fill: #EAEAEA;");
        buttonBox.setStyle("-fx-backgroung-color: #0B0F1A;");
        continueBtn.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
        goBackBtn.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");

        //Adding the components to the panes
        buttonBox.getChildren().addAll(goBackBtn,continueBtn);
        centerBox.getChildren().addAll(usernameLbl, usernameTxt, passwordLbl, passwordTxt, buttonBox);
        centerBox.setStyle("-fx-background-color: #0B0F1A;");

        //Setting the buttons handle event
        goBackBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    SignIn signIn = new SignIn();
                    Scene nextScene = signIn.choose(primaryStage);
                    StartingPage.changeScene(nextScene);
                }
        });

        //Setting the button action hansler that will go to the home page
        continueBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                
                String name = usernameTxt.getText();
                String password = passwordTxt.getText();

                if(DatabaseHandler.isValidateLogin(name, password)){

                    //Open this lines after creating the home scene
                    /*
                    HomePage home = new HomePage();
                    Scene nextScene = new Scene(home.getContent());
                    StartingPage.changeScene(nextScene);
                    */
                   //System.out.println("worked!");
                }
                else{
                    warning.showAndWait();
                }
            }
        });

        Scene root2= new Scene(centerBox);
        primaryStage.setFullScreen(true);
        return root2;
    }
}
