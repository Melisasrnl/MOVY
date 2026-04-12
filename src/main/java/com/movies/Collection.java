package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Collection {

    private  String name;
    private String user;
    private  boolean isColPrivate;
    private  ArrayList<Integer> movieIDs;

    public Collection(String user, String name, boolean isPrivate){
        this.user = user;
        this.name = name;
        this.isColPrivate = isPrivate;
        this.movieIDs = DatabaseHandler.getMoviesFromCollection(this.getName(), this.getUser());
    }

    public Scene showCDP(Stage stage, CollectionPage cp){
        HBox topMenu = new TopMenu().createTopMenu(stage);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #b2b1ae;");
        BorderPane mainLayout = new BorderPane();

        HBox topBar = new HBox();
        topBar.setSpacing(10);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #262523;");

        Button back = new Button("< Back");
        back.setOnAction(e -> {
            stage.setScene(cp.createCollectionsPage(stage));
        });

        Label title = new Label(this.name);
        title.setTextFill(Color.BEIGE);
        title.setFont(new Font(18));

        Button info = new Button("Info");
        info.setOnAction(e -> {
            StackPane overlay = new StackPane();
            VBox popup = new VBox(15);
            popup.setPadding(new Insets(20));
            popup.setAlignment(Pos.CENTER);
            popup.setStyle("-fx-background-color: #2c2727; -fx-background-radius: 10;");

            popup.setMaxWidth(300);
            popup.setMaxHeight(200);

            Label popupTitle = new Label("Change Collection Info");
            popupTitle.setTextFill(Color.WHITE);
            popupTitle.setFont(new Font(16));

            TextField input = new TextField();
            input.setPromptText("Enter new name");

            HBox pButtons = new HBox(10);
            pButtons.setPadding(new Insets(10));
            ToggleButton publicBtn = new ToggleButton("Public");
            ToggleButton privateBtn = new ToggleButton("Private");
            pButtons.getChildren().addAll(publicBtn, privateBtn);
            pButtons.setAlignment(Pos.CENTER);

            ToggleGroup group = new ToggleGroup();
            publicBtn.setToggleGroup(group);
            privateBtn.setToggleGroup(group);

            publicBtn.setSelected(true);

            HBox buttons = new HBox(5);
            buttons.setPadding(new Insets(10,70,10,70));
            Button submit = new Button("Submit");
            submit.setOnAction(ev -> {
                boolean isPrivate = privateBtn.isSelected();
                String newName = input.getText();
                if (newName != null && !newName.isBlank()){
                    DatabaseHandler.setCollectionIsPrivate(this.getName(), this.getUser(), isPrivate);
                    DatabaseHandler.setCollectionName(this.getName(), this.getUser(), newName);
                    this.setName(newName);
                    this.isColPrivate = isPrivate;
                    root.getChildren().remove(overlay);
                    stage.setScene(showCDP(stage, cp));
                }
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(ev -> {
                root.getChildren().remove(overlay);
            });

            buttons.getChildren().addAll(submit, cancelButton);

            popup.getChildren().addAll(popupTitle, input, pButtons, buttons);
            overlay.getChildren().add(popup);
            root.getChildren().add(overlay);

        });

        Button edit = new Button("Edit");
        edit.setOnAction(e -> {
            StackPane overlay = new StackPane();
            VBox popup = new VBox(15);
            popup.setPadding(new Insets(20));
            popup.setAlignment(Pos.CENTER);
            popup.setStyle("-fx-background-color: #2c2727; -fx-background-radius: 10;");

            popup.setMaxWidth(300);
            popup.setMaxHeight(200);

            Label prompt = new Label("Choose movie to remove:");
            prompt.setTextFill(Color.WHITE);      

            ChoiceBox<Integer> movieList = new ChoiceBox<>();
            movieList.getItems().addAll(this.getMovieIDs());

            movieList.setConverter(new javafx.util.StringConverter<Integer>() {
                @Override
                public String toString(Integer movieId) {
                    return movieId == null ? "" : TmdbService.getMovieName(movieId);
                }

                @Override
                public Integer fromString(String string) {
                    return null;
                }
            });

            HBox buttons = new HBox();
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(10);

            Button removeButton = new Button("Remove");
            removeButton.setOnAction(ev -> {
                Integer selectedMovieId = movieList.getValue();
                if (selectedMovieId != null) {
                    boolean deleted = DatabaseHandler.deleteMovieFromCollection(this.getUser(),this.getName(),selectedMovieId);
                    if (deleted) {
                        movieIDs.remove(selectedMovieId);
                        root.getChildren().remove(overlay);
                        stage.setScene(showCDP(stage, cp));
                    }
                }
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(ev -> {
                root.getChildren().remove(overlay);
            });
            buttons.getChildren().addAll(removeButton, cancelButton);

            popup.getChildren().addAll(prompt, movieList, buttons);
            overlay.getChildren().add(popup);
            root.getChildren().add(overlay);

        });

        Button deleteButton = new Button("Delete Collection");
        deleteButton.setOnAction(e -> {
            DatabaseHandler.deleteCollection(this.getName(), this.getUser());
            stage.setScene(cp.createCollectionsPage(stage));
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(back, title,info, spacer, edit, deleteButton);
        VBox headerBox = new VBox();
        headerBox.getChildren().addAll(topMenu, topBar);

        FlowPane moviePane = new FlowPane();
        moviePane.setPadding(new Insets(15));
        moviePane.setStyle("-fx-background-color: #b2b1ae;");
        moviePane.setHgap(20);
        moviePane.setVgap(20);

        for (Integer i: movieIDs) {
            VBox card = new VBox();
            card.setSpacing(5);
            card.setAlignment(Pos.CENTER);

            String posterPath = TmdbService.getMoviePhotoUrl(i);
            Image image = new Image(posterPath, 120, 160, true, true);
            ImageView posterView = new ImageView(image);

            Label name = new Label(TmdbService.getMovieName(i));

            card.getChildren().addAll(posterView, name);
            moviePane.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(moviePane);
        scroll.setStyle("-fx-background: #b2b1ae; -fx-background-color: #b2b1ae;");
        scroll.setFitToWidth(true);

        mainLayout.setTop(headerBox);
        mainLayout.setCenter(scroll);

        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 800, 600);
        return scene;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getUser(){
        return user;
    }

    public boolean isPrivate(){
        return isColPrivate;
    }

    public ArrayList<Integer> getMovieIDs(){
        return movieIDs;
    }
}