package com.movies;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CollectionPage {
    String userName;
    private ArrayList<String> collectionNames;
    private ScrollPane scrollPane;

    public CollectionPage(String userName) {
        this.userName = userName;
    }

    public Scene createCollectionsPage(Stage stage){
        HBox topMenu = new TopMenu().createTopMenu(stage);

        HBox title = new HBox();
        title.setSpacing(10);
        title.setPadding(new Insets(5));
        title.setStyle("-fx-background-color: #262523;");

        Button back = new Button("<");
        back.setShape(new Circle(10));
        back.setStyle("-fx-background-color: #edeae0;");
        back.setOnAction(e -> {
            ProfilePage profilePage = new ProfilePage();
            stage.setScene(profilePage.createProfilePageScene(stage));
        });


        Label sc = new Label("Special Collections");
        sc.setTextFill(Color.BEIGE);
        sc.setFont(new Font(15));
        sc.setAlignment(Pos.CENTER);

        title.getChildren().addAll(back, sc);

        VBox cols = new VBox();
        cols.setPadding(new Insets(10));
        cols.setSpacing(10);

        scrollPane = new ScrollPane(cols);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-control-inner-background: #292727;");
        
        cols.getChildren().addAll(topMenu, title);

        collectionNames = DatabaseHandler.getCollections(userName);
        for (String s : collectionNames) {
            boolean isPrivate = DatabaseHandler.isCollectionPrivate(userName, s);
            VBox col = createCollection(stage, s, isPrivate);
            cols.getChildren().add(col);
        }

        Scene page = new Scene(scrollPane, 800,600);
        return page;
    }

    public VBox createCollection(Stage stage, String collectionName, boolean isPrivate){
        Collection c = new Collection(userName, collectionName, isPrivate);
        VBox aCollection = new VBox();
        aCollection.setStyle("-fx-background-color: #b2b1ae;");

        HBox colNameBox = new HBox();
        colNameBox.setStyle("-fx-background-color: #30302d;");
        colNameBox.setAlignment(Pos.CENTER);
        colNameBox.setPadding(new Insets(5));

        Label colName = new Label(collectionName);
        colName.setFont(new Font(15));
        colName.setTextFill(Color.BEIGE);

        Label privacy;
        if(!isPrivate){
            privacy = new Label(" (Public)");
        }
        else{
            privacy = new Label(" (Private)");
        }

        privacy.setTextFill(Color.BEIGE);

        Button seeAllButton = new Button("See All>");
        seeAllButton.setAlignment(Pos.CENTER_RIGHT);

        seeAllButton.setOnAction(e -> {
            stage.setScene(c.showCDP(stage, this));
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        colNameBox.getChildren().addAll(colName, privacy, spacer, seeAllButton);
        
        HBox movies = new HBox();
        movies.setPadding(new Insets(10));
        movies.setSpacing(20);
        for (Integer movieId : c.getMovieIDs()) {

            String posterPath = TmdbService.getMoviePhotoUrl(movieId);
            Image image = new Image(posterPath, 120, 160, true, true);
            ImageView posterView = new ImageView(image);
            
            movies.getChildren().add(posterView);
        }

        aCollection.getChildren().addAll(colNameBox, movies);

        return aCollection;
    }

    public ArrayList<Collection> getCollections() {
        ArrayList<Collection> collections = new ArrayList<>();
        ArrayList<String> names = DatabaseHandler.getCollections(userName);

        for (String name : names) {
            boolean isPrivate = DatabaseHandler.isCollectionPrivate(userName, name);
            collections.add(new Collection(userName, name, isPrivate));
        }

        return collections;
    }
}
