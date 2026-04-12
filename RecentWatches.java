package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RecentWatches {
    private String username;
    private ArrayList<Integer> movieIDs;

    public RecentWatches(String username){
        this.username = username;
        this.movieIDs = DatabaseHandler.getMoviesFromRecentWatches(username);
    }

    //ui for the favorites
    public Scene showRecentWatches(Stage stage){
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
            ProfilePage page = new ProfilePage();
            stage.setScene(page.createProfilePageScene(stage));
        });

        Label title = new Label("Recent Watches");
        title.setTextFill(Color.BEIGE);
        title.setFont(new Font(18));

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
            
            HBox buttons = new HBox();
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(10);

            ChoiceBox<Integer> movieList = new ChoiceBox<>();
            movieList.getItems().addAll(this.movieIDs);

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

            Button removeButton = new Button("Remove");
            removeButton.setOnAction(ev -> {
                Integer selectedMovieId = movieList.getValue();
                if (selectedMovieId != null) {
                    boolean deleted = DatabaseHandler.deleteMovieFromRecentWatches(username, selectedMovieId);
                    if (deleted) {
                        movieIDs.remove(selectedMovieId);
                        root.getChildren().remove(overlay);
                        stage.setScene(showRecentWatches(stage));
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


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(back, title, spacer, edit);
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
}
