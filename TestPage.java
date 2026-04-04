package com.movies;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TestPage {
    public String selectedAge;
    public String selectedAppropriate;
    public ArrayList<String> selectedGenres;


    public static void choices(ArrayList<String> chosen) {
    }

    public static Scene choose(Stage primaryStage) {

        HBox topMenu = TopMenu.createTopMenu(primaryStage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);
        // Layout for the go back button
        VBox back = new VBox();
        Button goBack = new Button("←");
        back.setScaleX(3);
        goBack.setStyle("-fx-background-color: #0B0F1A;" + "-fx-text-fill: #EAEAEA;");
        goBack.setScaleY(2);
        back.getChildren().add(goBack);
        // Layout for questions & answers
        // header
        Label header = new Label("Movie Picker");
        header.setStyle("-fx-text-fill: #EAEAEA;");
        header.setFont(Font.font("Times New Roman", 30));
        // subheader
        Label subheader = new Label("Answer the questions, get your movie!");
        subheader.setStyle("-fx-text-fill: #EAEAEA;");
        subheader.setFont(Font.font("Times New Roman", 24));
        // Question 1
        Label q1 = new Label("Do you have any age restrictions?");
        q1.setFont(Font.font("Times New Roman", 18));
        q1.setStyle("-fx-text-fill: #EAEAEA;");
        RadioButton q1a1 = new RadioButton("No.");
        q1a1.setFont(Font.font("Times New Roman", 15));
        q1a1.setStyle("-fx-text-fill: #EAEAEA;");
        RadioButton q1a2 = new RadioButton("Yes, PG-13.");
        q1a2.setFont(Font.font("Times New Roman", 15));
        q1a2.setStyle("-fx-text-fill: #EAEAEA;");
        ToggleGroup q1answers = new ToggleGroup();
        q1a1.setToggleGroup(q1answers);
        q1a2.setToggleGroup(q1answers);
        q1a1.setSelected(true);
        VBox quest1 = new VBox(3, q1, q1a1, q1a2);
        quest1.setStyle("-fx-background-color: #282B35;");
        // Question 2
        Label q2 = new Label("How old would you want your movie to be?");
        q2.setFont(Font.font("Times New Roman", 18));
        q2.setStyle("-fx-text-fill: #EAEAEA;");
        RadioButton q2a1 = new RadioButton("Does not matter.");
        q2a1.setFont(Font.font("Times New Roman", 15));
        q2a1.setStyle("-fx-text-fill: #EAEAEA;");
        RadioButton q2a2 = new RadioButton("Released in the last 5 years.");
        q2a2.setFont(Font.font("Times New Roman", 15));
        q2a2.setStyle("-fx-text-fill: #EAEAEA;");
        RadioButton q2a3 = new RadioButton("Released in the last 10 years.");
        q2a3.setFont(Font.font("Times New Roman", 15));
        q2a3.setStyle("-fx-text-fill: #EAEAEA;");
        RadioButton q2a4 = new RadioButton("Released at least 20 years ago.");
        q2a4.setFont(Font.font("Times New Roman", 15));
        q2a4.setStyle("-fx-text-fill: #EAEAEA;");
        ToggleGroup q2answers = new ToggleGroup();
        q2a1.setToggleGroup(q2answers);
        q2a2.setToggleGroup(q2answers);
        q2a3.setToggleGroup(q2answers);
        q2a4.setToggleGroup(q2answers);
        q2a1.setSelected(true);
        VBox quest2 = new VBox(3, q2, q2a1, q2a2, q2a3, q2a4);
        quest2.setStyle("-fx-background-color: #282B35;");
        // Question 3
        Label q3 = new Label("Pick the genres you are interested in:");
        q3.setFont(Font.font("Times New Roman", 18));
        q3.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a1 = new CheckBox("Sci-fi");
        q3a1.setFont(Font.font("Times New Roman", 15));
        q3a1.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a2 = new CheckBox("Action");
        q3a2.setFont(Font.font("Times New Roman", 15));
        q3a2.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a3 = new CheckBox("Thriller");
        q3a3.setFont(Font.font("Times New Roman", 15));
        q3a3.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a4 = new CheckBox("Horror");
        q3a4.setFont(Font.font("Times New Roman", 15));
        q3a4.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a5 = new CheckBox("Drama");
        q3a5.setFont(Font.font("Times New Roman", 15));
        q3a5.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a6 = new CheckBox("Comedy");
        q3a6.setFont(Font.font("Times New Roman", 15));
        q3a6.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a7 = new CheckBox("Fantasy");
        q3a7.setFont(Font.font("Times New Roman", 15));
        q3a7.setStyle("-fx-text-fill: #EAEAEA;");
        CheckBox q3a8 = new CheckBox("Mystery");
        q3a8.setFont(Font.font("Times New Roman", 15));
        q3a8.setStyle("-fx-text-fill: #EAEAEA;");
        VBox quest3 = new VBox(3, q3, q3a1, q3a2, q3a3, q3a4, q3a5, q3a6, q3a7, q3a8);
        quest3.setStyle("-fx-background-color: #282B35;");
        // Adding elements to their vertical box
        VBox quiz = new VBox(10, header, subheader, quest1, quest2, quest3);
        // Implementing the button that will finish the quiz
        Button finish = new Button("I am ready!");
        finish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.setScene(RecommendedPage.choose(primaryStage));
            }
        });
        finish.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
        finish.setFont(Font.font("Times New Roman", 18));
        VBox finishBox = new VBox(finish);
        finishBox.setAlignment(Pos.CENTER_RIGHT);
        // Putting everything together
        HBox contains = new HBox(40, back, quiz, finishBox);
        VBox wholePage = new VBox(10, topMenu, contains);
        wholePage.setStyle("-fx-background-color: #0B0F1A;");
        Scene testScene = new Scene(wholePage);
        return testScene;

    }
}
