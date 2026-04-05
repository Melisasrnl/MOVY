package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private ArrayList<Collection> collections;
    private ScrollPane scrollPane;

    public CollectionPage(){
        collections = new ArrayList<>();
        Collection a = new Collection("Girl Nights", true);
        a.addMovie("La La Land");
        a.addMovie("The NoteBook");
        a.addMovie("Hamnet");
        a.addMovie("Kill Bill: Volume 1");
        a.addMovie("About Time");
        a.addMovie("13 Going on 30");

        Collection b = new Collection("Empty", false);

        Collection c = new Collection("Rewatchables", true);
        c.addMovie("Batman: The Dark Knight");
        c.addMovie("Batman Begins");
        c.addMovie("Batman: The Dark Knight Rises");

        Collection d = new Collection("SpecialC", false);
        d.addMovie("Whiplash");
        d.addMovie("Requiem for A Dream");

        Collection e = new Collection("Collection1", true);
        e.addMovie("Whiplash");
        e.addMovie("Requiem for A Dream");
        e.addMovie("Batman: The Dark Knight Rises");

        collections.add(a);
        collections.add(b);
        collections.add(c);
        collections.add(d);
        collections.add(e);
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

        for (Collection col : collections) {
            cols.getChildren().add(createCollection(stage, col));
        }

        Scene page = new Scene(scrollPane, 800,600);
        return page;
    }

    public VBox createCollection(Stage stage, Collection c){
        VBox aCollection = new VBox();
        aCollection.setStyle("-fx-background-color: #b2b1ae;");

        HBox colNameBox = new HBox();
        colNameBox.setStyle("-fx-background-color: #30302d;");
        colNameBox.setAlignment(Pos.CENTER);
        colNameBox.setPadding(new Insets(5));

        Label colName = new Label(c.getName());
        colName.setFont(new Font(15));
        colName.setTextFill(Color.BEIGE);

        Label privacy;
        if(c.isPublic()){
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
        for(String movie : c.getMovies()){
            movies.getChildren().add(new Rectangle(120,160));
        }

        aCollection.getChildren().addAll(colNameBox, movies);

        return aCollection;
    }
    public void removeCollection(Collection c){
        collections.remove(c);
    }

    public ArrayList<Collection> getCollections(){
        return collections;
    }
}
