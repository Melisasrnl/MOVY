package com.movies;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProfilePage {
    private CollectionPage cp = new CollectionPage();

    public  Scene createProfilePageScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #0B0F1A;");
        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        HBox contentBox = new HBox();
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 55.0);
        AnchorPane.setLeftAnchor(contentBox, 15.0);
        AnchorPane.setRightAnchor(contentBox, 15.0);
        AnchorPane.setBottomAnchor(contentBox, 15.0);
        contentBox.setFillHeight(true);
        contentBox.setPrefWidth(Double.MAX_VALUE);


        Button returnToHomePage = new Button("<");
        returnToHomePage.setStyle("-fx-text-fill: #EAEAEA;");

        Circle userPPCircle = new Circle(27, Color.DODGERBLUE);
        Button edit = new Button("Edit");
        edit.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: #EAEAEA");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stage.setScene(new EditProfile().createEditProfileScene(stage));
            }
        });
        Label username = new Label("ceren");
        username.setStyle("-fx-text-fill: #EAEAEA");
        Label bio = new Label("your average romcom enjoyer");
        bio.setStyle("-fx-text-fill: #EAEAEA");
        VBox userInfo = new VBox(5, username, bio, edit);
        Button followers = new Button("15 \n Followers");
        followers.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: #EAEAEA");

        followers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // a new scene will be added
            }
        });
        Button followings = new Button("13 \n Followings");
        followings.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: #EAEAEA");

        followers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // a new scene will be added
            }
        });
        HBox aboutFollows = new HBox(20, followers, followings);
        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: #EAEAEA");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage popupStage = new Stage();
                popupStage.setTitle("LOGOUT");
                VBox newBox = new VBox(10);
                newBox.setPadding(new Insets(20));
                newBox.setAlignment(Pos.CENTER);
                Label label = new Label("Do you want to log out?");
                label.setWrapText(true);
                label.setMaxWidth(250);
                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                    }
                });
                Button logoutBtn = new Button("Logout");
                logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                        // a new scene will be added
                    }
                });
            }
        });
        VBox rightSideBio = new VBox(20, aboutFollows, logout);
        rightSideBio.setAlignment(Pos.CENTER_LEFT);
        HBox topRow = new HBox(20, userPPCircle, userInfo);
        topRow.setPadding(new Insets(20, 20, 20, 15));
        topRow.setAlignment(Pos.TOP_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.getChildren().addAll(spacer, rightSideBio);

        Label favorites = new Label("Favorites");
        favorites.setStyle("-fx-text-fill: #EAEAEA");
        Button seeAllFavs = new Button("See All");
        seeAllFavs.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: #EAEAEA");

        Region spacerFav = new Region();
        HBox.setHgrow(spacerFav, Priority.ALWAYS);
        HBox favsLine = new HBox(10, favorites, spacerFav, seeAllFavs);
        favsLine.setAlignment(Pos.CENTER_LEFT);
        favsLine.setMaxWidth(Double.MAX_VALUE);

        Label recentWatches = new Label("Recent Watches");
        recentWatches.setStyle("-fx-text-fill: #EAEAEA;");
        Button seeAllRWs = new Button("See All");
        seeAllRWs.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: #EAEAEA");
        Region spacerRWs = new Region();
        HBox.setHgrow(spacerRWs, Priority.ALWAYS);
        HBox RWsLine = new HBox(10, recentWatches,  spacerRWs,seeAllRWs);
        RWsLine.setAlignment(Pos.CENTER_LEFT);
        RWsLine.setMaxWidth(Double.MAX_VALUE);

        VBox bottomRow = new VBox(120, favsLine, RWsLine);
        bottomRow.setPadding(new Insets(20, 20, 20, 15));
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        VBox leftSide = new VBox(20, topRow, bottomRow);
        leftSide.setPadding(new Insets(20, 20, 20, 15));
        leftSide.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(leftSide, Priority.ALWAYS);


        Label dvdDrawer = new Label("DVD DRAWER");
        dvdDrawer.setStyle("-fx-text-fill: #EAEAEA;-fx-font-weight: bold;");
        dvdDrawer.setFont(new Font(30));

        Label watchList = new Label("Watchlist");
        watchList.setStyle("-fx-text-fill: #EAEAEA; -fx-font-weight: bold;");
        Button seeAllWL = new Button("See All");
        seeAllWL.setStyle("-fx-background-color: #1f1f21;; -fx-text-fill: #EAEAEA");
        Region spacerWL = new Region();
        HBox.setHgrow(spacerWL, Priority.ALWAYS);
        HBox WLLine = new HBox(10,  spacerWL,watchList, seeAllWL);
        WLLine.setMaxWidth(Double.MAX_VALUE);
        WLLine.setStyle("-fx-background-color: #1f1f21;");

        Label recombyFriends = new Label("Recommended by Friends");
        recombyFriends.setStyle("-fx-text-fill: #EAEAEA; -fx-font-weight: bold;");
        Button seeAllRBF = new Button("See All");
        seeAllRBF.setStyle("-fx-background-color: #1f1f21;; -fx-text-fill: #EAEAEA");
        Region spacerRBF = new Region();
        HBox.setHgrow(spacerRBF, Priority.ALWAYS);
        HBox RBFLine = new HBox(10, spacerRBF,recombyFriends, seeAllRBF);
        RBFLine.setMaxWidth(Double.MAX_VALUE);
        RBFLine.setStyle("-fx-background-color: #1f1f21;");

        Label specialColl = new Label("Special Collections");
        specialColl.setStyle("-fx-text-fill: #EAEAEA; -fx-font-weight: bold;");
        Button seeAllSC = new Button("See All");
        seeAllSC.setStyle("-fx-background-color: #1f1f21; -fx-text-fill: #EAEAEA");
        seeAllSC.setOnAction(e -> {
            stage.setScene(cp.createCollectionsPage(stage));
        });

        Region spacerSC = new Region();
        HBox.setHgrow(spacerSC, Priority.ALWAYS);
        HBox SCLine = new HBox(10,spacerSC,specialColl, seeAllSC);
        SCLine.setMaxWidth(Double.MAX_VALUE);
        SCLine.setStyle("-fx-background-color: #1f1f21;");

        Button addSC = new Button("+ Add New Collection");
        addSC.setOnAction(e -> { 
            StackPane popup = new CreateCollection().createNewCol(stage, root, cp);
            root.getChildren().add(popup);
        });

        addSC.setStyle("-fx-text-fill: #000000;");

        // the most recent three collections will be displayed here as buttons, a for loop may be used//DID IT!
        VBox top3collections= new VBox();
        top3collections.setSpacing(10);
        top3collections.setAlignment(Pos.CENTER);
        top3collections.getChildren().addAll(SCLine,addSC);
        for(int i=0; i<3; i++){
            Collection c = cp.getCollections().get(i);
            Button col = new Button(c.getName());
            col.setStyle("-fx-background-color: #1f1f21; -fx-text-fill: #EAEAEA");
            col.setOnAction(e -> {
                stage.setScene(c.showCDP(stage, cp));
            });
            top3collections.getChildren().add(col);
        }

        Label activites = new Label("Activities");
        activites.setStyle("-fx-text-fill: #EAEAEA;");
        Button seeAllAct = new Button("See All");
        seeAllAct.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA");
        Region spacerAct = new Region();
        HBox.setHgrow(spacerAct, Priority.ALWAYS);
        HBox actLine = new HBox(10, spacerAct, activites, seeAllAct);
        actLine.setMaxWidth(Double.MAX_VALUE);
        //label will be changed
        Label lastAct = new Label("ekin rated Whiplash");
        lastAct.setStyle("-fx-text-fill: #EAEAEA;");

        VBox rightSide = new VBox(40,dvdDrawer,WLLine,RBFLine,top3collections,actLine,lastAct);
        rightSide.setPadding(new Insets(10));
        rightSide.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA");
        rightSide.setAlignment(Pos.TOP_CENTER);
        rightSide.setMinWidth(200);
        rightSide.setPrefWidth(250);
        rightSide.setMaxWidth(300);
        leftSide.setMinWidth(0);
        leftSide.setPrefWidth(600);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        VBox leftWrapper = new VBox(10, returnToHomePage, leftSide);
        HBox.setHgrow(leftWrapper, Priority.ALWAYS);
        rightSide.setMaxHeight(Double.MAX_VALUE);
        contentBox.setAlignment(Pos.TOP_LEFT);
        Region spacerMain = new Region();
        HBox.setHgrow(spacerMain, Priority.ALWAYS);

        contentBox.getChildren().addAll(leftWrapper, spacerMain, rightSide);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);
    }
}
