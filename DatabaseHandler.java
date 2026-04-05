package com.movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/* If the user wants to create a new (only while users create their own table so this info is only for create table methods)
    table (Collection or new chat etc.) the name will be changed to small characters fully
    in order for the database to work case insensitive. Please make sure you have
    a case insensitivity call that method before
    calling the methods in the DatabaseHandler class. */

public class DatabaseHandler {

    //These are the information of our database to set the database url
    

    //Combining the information above to form the database url
    private static final String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=true";

    //This method connects to the database
    public static Connection connect(){
         Connection conn = null;

         //Building the try catch to see if our project connected to the
         //database succesfully.
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("CONNECTED TO THE DATABASE SUCCESFULLY! ");
        }catch(SQLException e){

            //Prints the error if the project fails to connect to the database
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return conn;

    }

    //INTERNETTEN VERI TABANI ÇEKME.

    //This method is used to create the users table if it doesnt already exists. (This code will be only used once.
    //after using this method it will be kept as command)
    /* 
        public static void createusersTable(){

            //These are the sql commands to create the table
            String sql = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTO_INCREMENT ," 
                        + " username VARCHAR(50) NOT NULL UNIQUE, "
                        + " password VARCHAR(50) NOT NULL, "
                        + " userbio VARCHAR(1000) DEFAULT 'Hello...', "
                        + " followercount INTEGER DEFAULT 0, "
                        + " followingcount INTEGER DEFAULT 0, "
                        + " profilepic VARCHAR(250) DEFAULT 'https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/graypf_msd6wr.jpg',"
                        + " email VARCHAR(50) NOT NULL UNIQUE );";

            //Building the try catch block to catch the error that might occur while creating a new table
            try( Connection conn = connect();
                Statement stmt = conn.createStatement();){

                //executing our sql command above
                stmt.execute(sql);

                //Printing that the table was created succesfully
                System.out.println("CREATED THE USERS TABLE SUCCESSFULLY! ");

            }catch(SQLException e){

                //Printing the error we got on the console
                System.out.println("USERS TABLE CREATING ERROR: " + e);
            }
        
        }
    */

    //We use this method to check if the user exists in the table
    private static boolean doesUserExist(String username){

        //Since the usernames are unique we will use usernames to find the user
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        //Setting the try catch
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Replacing the ? with the string we got from the method
            pstmt.setString(1, username);

            //Setting another try method and getting the result set and checking the result set
            try(ResultSet rs = pstmt.executeQuery()){

                //Putting an if else statement in porder to check if there are any other line in rs
                if(rs.next()){

                    //returning the result and checking if the result(which is the count of the users with this username)
                    //is greater than 1
                    return rs.getInt(1) > 0;
                }
            }

        }catch(SQLException e){

            //Printing the error we get on the console
            System.out.println("DOES USER EXISTS ERROR: " + e.getMessage());
        }

        //Returning false if it couldnt enter the try
        return false;
    }

    //This method checks while users try to login. while loging in this method checks if the user exists
    //and if the password matches
    public static boolean isValidateLogin(String username, String password){

        //Checking if the user with this username exists
        if(!doesUserExist(username)){
            return false;
        }

        //The method to check if the password matches with the username
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        //Setting the try chatch method
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Setting the ? in the username and password
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            //Setting the try to get the result set
            try(ResultSet rs = pstmt.executeQuery()){

                //Putting an if else statement in porder to check if there are any other line in rs
                if(rs.next()){

                    //returning the result and checking if the result(which is the count of the users with this username)
                    //is greater than 1
                    return rs.getInt(1) > 0;
                }
            }

        }catch(SQLException e){

            //Printing the error
            System.out.println("VALIDATE LOGIN ERROR: " + e);
        }

        //Returning false if the code doesnt do any of these
        return false;
    }

    //This method checks if the entered user exists. This method will be used only while
    //a new user is trying to create a user
    public static boolean createNewUser(String username, String password, String mail){

        //Writing the sql command
        String sql = "INSERT INTO users(username, password, email) VALUES(?,?,?)";

        //Building the try catch 
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Adding the values
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, mail);

            //Updating the table
            pstmt.executeUpdate();

            createAllCollectionsTable(username);
            createFavorites(username);
            createRecentWatches(username);
            createRecomendedByFriends(username);
            createUsersChatsTable(username);

            //Returning true because it was succesfull
            return true;

        }catch(SQLException e){

            //Checking if the cause of error was the name and email being unique or not
            if( e.getErrorCode() == 1062){
                System.out.println("THIS USER ALRADY ESISTS ERROR!");
            }
            else{
                //Printing the error
                System.out.println("ADDING USER TO TABLE ERROR: " + e.getMessage());
            } 
        }

        //Returning false if it didnt return true
        return false;
    }

    //User info getter method.This getter method is only for strings. 
    //This method gets 3 parameters. the first parameter will me the information
    //that you want to get. the key names are username,email,userbio,password,profilepic.
    //Please make sure you write the choises as same as the ones above or you will get an error. 
    //the second parameter will be the type of the info you know about the user. It will be either
    //the username, email. The third parameter will be the string containing the value of username
    //,email.
    public static String userStringGetter(String keyGet, String keyGive, String valueGive){
        
        //Writing the sql command string
        String sql = "SELECT " + keyGet + "FROM users WHERE " + keyGive + " = ?";
        
        //Setting the try catch block
        try( Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Setting the value we got to find the user
            pstmt.setString(1, valueGive);

            //Setting the result set
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            //Returning the result set
            return rs.getString(1);

        }catch(SQLException e){

            //Writing the error
            System.out.println("USERS TABLE STRING GETTER ERROR: " + e.getMessage());
        }

        //Returns the warning userstringnotfound string if it cant return the result set
        return "userstringnotfound";
    }

    //User info getter method.This getter method is only for Integers. 
    //This method gets 3 parameters. the first parameter will me the information
    //that you want to get. the key names are followercount, id, followingcount.
    //Please make sure you write the choises as same as the way above or you will get an error. 
    //the second parameter will be the type of the info you know about the user. It will be either
    //the username or email. The third parameter will be the string containing the value of username
    //,email.
    public static Integer userIntegerGetter(String keyGet, String keyGive, Integer valueGive){
        
        //Writing the sql command string
        String sql = "SELECT " + keyGet + "FROM users WHERE " + keyGive + " = ?";
        
        //Setting the try catch block
        try( Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Setting the value we got to find the user
            pstmt.setInt(1, valueGive);

            //Setting the result set
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            //Returning the result set
            return rs.getInt(1);

        }catch(SQLException e){

            //Writing the error
            System.out.println("USERS TABLE INTEGER GETTER ERROR: " + e.getMessage());
        }

        //Returns the warning -1 integer if it cant return the result set
        return -1;
    }

    //User String info setter methods. The first parameter is the key name of the type
    //you want to update. and the second parameter is the value of this type. 
    //The third parameter is the key name of the finders(username, email). the 4th parameter
    //is the value of this finder name
    public static void userStringSetter(String keyToSet, String valueToSet, String keyGive, String valueGive){

        //Writing the sql commands string
        String sql = "UPDATE users SET " + keyToSet + " = ? WHERE " + keyGive + " = ? ";

        //Building the trycatch method
        try( Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Setting the values to put
            pstmt.setString(1, valueToSet);
            pstmt.setString(2, keyToSet);
            
            //Printing that the update was succesfull on console
            System.out.println("UPDATED USERS SUCCESFULLY");
        }catch(SQLException e){

            //Printing the error if there is any
            System.out.println("UPDATING USER STRING ERROR: " + e.getMessage());
        }
    }

    //User Integer info setter methods. The first parameter is the key name of the type
    //you want to update. and the second parameter is the value of this type. 
    //The third parameter is the key name of the finders(username, email). the 4th parameter
    //is the value of this finder name
    public static void userIntegerSetter(String keyToSet, Integer valueToSet, String keyGive, String valueGive){

        //Writing the sql commands string
        String sql = "UPDATE users SET " + keyToSet + " = ? WHERE " + keyGive + " = ? ";

        //Building the trycatch method
        try( Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            //Setting the values to put
            pstmt.setInt(1, valueToSet);
            pstmt.setString(2, keyToSet);
            
            //Printing that the update was succesfull on console
            System.out.println("UPDATED USERS SUCCESFULLY");
        }catch(SQLException e){

            //Printing the error if there is any
            System.out.println("UPDATING USER STRING ERROR: " + e.getMessage());
        }
    }


    //After creating this table this method wont be used so keep it as comment block
    //This method creates the table that keeps the information about
    //which user is following who
    /*
    public static void createFollowingsTable(){

        String sql = "CREATE TABLE IF NOT EXISTS followingstable (id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + "user VARCHAR(50) NOT NULL, "
                    + "following VARCHAR(50) NOT NULL);";
                    

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

                stmt.execute(sql);
                System.out.println("CREATED THE FOLLOWINGS TABLE SUCCESSFULY");
        }catch(SQLException e){
            System.out.println("CREATING FOLLOWINGS TABLE ERROR: " + e.getMessage());
        }
    }
    */

    //Gettingthe followings of a user returning a folloing list
    public static ArrayList<String> getFollowings( String username ){
        
        String sql = "SELECT following FROM followingstable WHERE user = ?";

        ArrayList<String> list = new ArrayList<String>();

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(rs.getString(1));
            }

            System.out.println("SUCCESFULLY GOT THE FOLLOWINGS LIST");

        }catch(SQLException e){
            System.out.println("GETTING FOLLOWINGS ERROR: " + e.getMessage());
        }

        return list;
    }

    //getting the followers of a user. returning a followers list
    public static ArrayList<String> getFollowers(String username){

        String sql = "SELECT user FROM followingstable WHERE following = ?";

        ArrayList<String> list = new ArrayList<String>();

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(rs.getString(1));
            }

            System.out.println("SUCCESFULLY GOT THE FOLLOWERS LIST");

        }catch(SQLException e){
            System.out.println("GETTING FOLLOWERS ERROR: " + e.getMessage());
        }

        return list;
    }

    //Start following method first name is the user that follows the second name
    public static boolean followUser(String user, String following){

        String sql = "INSERT INTO followingstable (user, following) VALUES (?,?)";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user);
            pstmt.setString(2, following);

            pstmt.executeUpdate();

            
            System.out.println("STARTED FOLLOWING! ");
            return true;

        }catch(SQLException e){
            System.out.println("FAILED TO FOLLOW: " + e.getMessage());
        }

        return false;
    }

    //Stop following method
    public static boolean stopFollowing(String user, String following){

        String sql = "DELETE FROM followingstable WHERE user = ? AND following = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user);
            pstmt.setString(2, following);

            pstmt.executeUpdate();

            System.out.println("SUCCESSFULLY STOPPED FOLLOWING");
            return true;

        }catch(SQLException e){
            System.out.println("STOP FOLLOWING ERROR: " + e.getMessage());
        }

        return false;
    }

    //this method creates a table where all of the collections of the user is kept.this table is created only
    //once when a new user is created. this method is called inside the create user method and wont be called anywhere else.
    private static void createAllCollectionsTable(String username){

        String sql = "CREATE TABLE IF NOT EXISTS " + username + "scollections ( id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + "collectionname VARCHAR(50) NOT NULL UNIQUE,"
                    + "moviecount INTEGER NOT NULL DEFAULT 0, "
                    + "private BOOLEAN);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);
            System.out.println("CREATED " + username + "'S COLLECTIONS TABLE");
        }catch(SQLException e){
            System.out.println("COULD NOT CREATE " + username + "'S COLLECTIONS TABLE: " + e.getMessage());
        }
    }

    //This method returns the list of the existing collections of a user
    public static ArrayList<String> getCollections(String username){

        String sql = "SELECT collectionname FROM " + username + "scollections";

        ArrayList<String> list = new ArrayList<String>();

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                list.add(rs.getString(1));
            }

            System.out.println("GET COLLECTION SUCCESSFUL");
            return list;

        }catch(SQLException e){
            System.out.println("GET COLLECTION ERROR: " + e.getMessage());
        }

        return list;
    }

    //Create a new collection table
    public static boolean newCollection(String collectionName, String user, Boolean isPrivate){

        String sql = "CREATE TABLE IF NOT EXISTS " + user + "s" + collectionName + "(id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + "movieid INTEGER UNIQUE);";

        String sqltwo = "INSERT INTO " + user + "scollections (collectionname,private) VALUES (?,?)";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqltwo);
            Statement stmt = conn.createStatement()){

            pstmt.setString(1, collectionName);
            pstmt.setBoolean(2, isPrivate);
            
            pstmt.executeUpdate();

            stmt.execute(sql);

            System.out.println("ADDED NEW COLLECTION");

            return true;

        }catch(SQLException e){
            System.out.println("CREATING COLLECTION ERROR: " + e.getMessage());
        }

        return false;
    }

    //Deleting a collection
    public static boolean deleteCollection(String collectionName, String username){

        String sql = "DROP TABLE IF EXISTS " + username + "s" +collectionName;
        String sqltwo = "DELETE FROM " + username + "scollections WHERE collectionname = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqltwo);
            Statement stmt = conn.createStatement()){

            pstmt.setString(1, collectionName);
            pstmt.executeUpdate();

            stmt.executeUpdate(sql);

            System.out.println("DELETED COLLECTİON " + collectionName);

            return true;

        }catch(SQLException e){
            System.out.println("DELETING COLLECTION ERROR: " + e.getMessage());
        }

        return false;
    }

    //Creating favorites Table. only used once in create user method
    private static void createFavorites(String user){

        String sql = "CREATE TABLE IF NOT EXISTS " + user + "sfavorites (id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + "movieid INTEGER UNIQUE);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("ADDED NEW FAVORITES");

        }catch(SQLException e){
            System.out.println("CREATING FAVORITES ERROR: " + e.getMessage());
        }

    }

    //Creating recentWatches table. only used once in create user method
    private static void createRecentWatches(String user){

        String sql = "CREATE TABLE IF NOT EXISTS " + user + "srecentwatches (id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + "movieid INTEGER UNIQUE);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("ADDED RECENT WATCHES");

        }catch(SQLException e){
            System.out.println("CREATING RECENT WATCHES ERROR: " + e.getMessage());
        }

    }

    //Recomended by friends. Wont be used.only used once in create user method.
    private static void createRecomendedByFriends(String user){

        String sql = "CREATE TABLE IF NOT EXISTS " + user + "srecomendedbyfriends (id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + "movieid INTEGER UNIQUE, friend VARCHAR(50) NOT NULL);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("ADDED RECOMENDED BY FRIENDS WATCHES");

        }catch(SQLException e){
            System.out.println("CREATING RECOMENDED BY FRIENDS WATCHES ERROR: " + e.getMessage());
        }

    }

    //Collection Setter methods
    public static boolean setCollectionName( String collectionname, String username, String newcollectionname){
        
        String sql = "RENAME TABLE " + username + "s" + collectionname + " TO " 
                    + username + "s" + newcollectionname;
        String sqltwo = "UPDATE " + username + "scollections SET collectionname = ? WHERE collectionname = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqltwo);
            Statement stmt = conn.createStatement()){

            pstmt.setString(1, newcollectionname);
            pstmt.setString(2, collectionname);
            pstmt.executeUpdate();

            stmt.executeUpdate(sql);

            System.out.println("COLLECTION NAME HAS BEEN SET");
            return true;

        }catch(SQLException e){
            System.out.println("SETTING COLLECTION NAME ERROR: " + e.getMessage());

        }

        return false;
    }

    public static boolean setCollectionIsPrivate( String collectionname, String username, Boolean isPrivate){
        
        String sqltwo = "UPDATE " + username + "scollections SET private = ? WHERE collectionname = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqltwo)){

            pstmt.setBoolean(1, isPrivate);
            pstmt.setString(2, collectionname);
            pstmt.executeUpdate();

            System.out.println("COLLECTION PRIVACY HAS BEEN SET");
            return true;

        }catch(SQLException e){
            System.out.println("SETTING COLLECTION PRIVACY ERROR: " + e.getMessage());

        }

        return false;
    }

    //Creating usernames chats table. this method is only used once in create user method
    private static void createUsersChatsTable(String user){
        String sql = "CREATE TABLE IF NOT EXISTS " + user + "schats (id INTEGER PRIMARY KEY AUTO_INCREMENT,"
        + "chatid INTEGER NOT NULL UNIQUE);";
        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("USER ALL CHATS TABLE CREATED");

        }catch(SQLException e){
            System.out.println("CREATING USERS CHATS TABLE ERROR: " + e.getMessage());
        }
    }

    //Create chats table. this table contains all of the chats. after creating this table once this method
    //wont be use again and will be put in comment lines.
    public static void createChatsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS chats( id INTEGER PRIMARY KEY AUTO_INCREMENT,"
        + "chatname VARCHAR(50) NOT NULL,"
        + "chatphotourl VARCHAR(250) NOT NULL DEFAULT 'https://res.cloudinary.com/dsvq5k1xr/image/upload/v1775046448/graypf_msd6wr.jpg',"
        + "membercount INTEGER NOT NULL, "
        + "aboutchat VARCHAR(250) DEFAULT 'Hello...',"
        + "isprivate BOOLEAN);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("CREATED ALL CHATS TABLE SUCCESSFULLY");

        }catch(SQLException e){
            System.out.println("CREATING ALL CHATS TABLE: " + e.getMessage());
        }
    }

    //Create new chat. dont forget to get users ArrayList. the admin booleans are also kept as arraylists where the indexes match with the members lists indexes.
    //for example the 1st user in the members arrays role is the 1st boolean in memberroles list. 
    //if its true they are admin if false they are not.
    public static boolean createNewChat(String chatname, ArrayList<String> members, ArrayList<Boolean> memberRoles, Boolean isPrivate){

        String sqlone;
        String sqltwo = "INSERT INTO chats (chatname, membercount, isprivate) VALUES (?,?,?)";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sqltwo);
        Statement stmt = conn.createStatement()){

            pstmt.setString(1, chatname);
            pstmt.setInt(2, members.size());
            pstmt.setBoolean(3, isPrivate);

            pstmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID");

            rs.next();
            int id = rs.getInt(1);

            createChatsMessagesTable(id);
            createChatsUsersTable(id);

            for(int i = 0; i < members.size(); i++){

                sqlone = "INSERT INTO " + members.get(i) + "schats (chatid) VALUES (?)";

                try(PreparedStatement pstmtone = conn.prepareStatement(sqlone)){
                    pstmtone.setInt(1, id);

                    pstmtone.executeUpdate();
                }

                addUserToChat(id, members.get(i), memberRoles.get(i));

            }

            return true;

        }catch(SQLException e){
            System.out.println("CREATE CHAT ERROR: " + e.getMessage());
        }

        return false;
        
    }

    //add new user
    public static boolean addUserToChat(Integer chatID, String user, Boolean isAdmin){
        String sql = "INSERT INTO " + chatID + "susers (username,isadmin) VALUES (?,?)";
        String sqlTwo= "INSERT INTO " + user + "schats (chatid) VALUES (?)";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmttwo = conn.prepareStatement(sqlTwo)){

            pstmt.setString(1, user);
            pstmt.setBoolean(2, isAdmin);

            pstmttwo.setInt(1, chatID);

            pstmt.executeUpdate();
            pstmttwo.executeUpdate();

            System.out.println("ADDED USER TO CHAT SUCCESSFULLY");

            return true;

        }catch(SQLException e){
            System.out.println("ADDING USER TO CHAT ERROR: " + e.getMessage());
        }

        return false;
    }

    //Set about chat
    public static boolean setAboutChat( Integer chatId, String about){

        String sql = "UPDATE chats SET aboutchat = ? WHERE id = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, about);
            pstmt.setInt(2,chatId);

            pstmt.executeUpdate();

            System.out.println("ABOUT CHAT UPDATED SUCCESSFULLY");

            return true;

        }catch( SQLException e){
            System.out.println( "ABOUT CHAT UPDATE ERROR: " + e.getMessage());
        }

        return false;
    }

    //delete chat
    public static boolean deleteChat (Integer chatId){

        String sqlone = "SELECT username FROM " + chatId + "susers";
        String sqltwo = "DELETE FROM chats WHERE id = ?";
        String sqlthree = "DROP TABLE IF EXISTS " + chatId + "susers";
        String sqlfour = "DROP TABLE IF EXISTS " + chatId + "smessages";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sqltwo);
        Statement stmt = conn.createStatement()){

            pstmt.setInt(1, chatId);

            pstmt.executeUpdate();

            ResultSet rs = stmt.executeQuery(sqlone);

            while(rs.next()){

                String sqlfive = "DELETE FROM " + rs.getString(1) + "schats WHERE chatid = ?";
                try(PreparedStatement pstmttwo = conn.prepareStatement(sqlfive)){

                    pstmttwo.setInt(1, chatId);
                    pstmttwo.executeUpdate();
                    
                }
            }

            stmt.executeUpdate(sqlthree);
            stmt.executeUpdate(sqlfour);

            System.out.println("DELETED CHAT SUCCESSFULLY");

            return true;


        }catch(SQLException e){

            System.out.println("DELETING CHAT ERROR: " + e.getMessage());
        }

        return false;
    }

    //remove user from a chat
    public static boolean removeUserFromChat(Integer chatId, String username){

        String sql = "DELETE FROM " + chatId + "susers WHERE username = ?";
        String sqltwo = "DELETE FROM " + username + "schats WHERE chatid = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmttwo = conn.prepareStatement(sqltwo)){

            pstmt.setString(1, username);

            pstmt.executeUpdate();

            pstmttwo.setInt(1, chatId);
            pstmttwo.executeUpdate();

            System.out.println("REMOVED USER FROM CHAT SUCCESSFULLY");

            return true;

        }catch(SQLException e){

            System.out.println("REMOVİNG USER FROM CHAT ERROR: " + e.getMessage());
        }

        return false;
    }

    //Set chat photo
    public static boolean setChatPhoto(Integer chatId, String photoURL){

        String sql = "UPDATE chats SET chatphotourl = ? WHERE id = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, photoURL);
            pstmt.setInt(2, chatId);
            pstmt.executeUpdate();

            System.out.println("CHAT PHOTO CHANGED SUCCESSFULLY");
            
            return true;

        }catch(SQLException e){
            System.out.println("CHAT PHOTO EDITING ERROR: " + e.getMessage());
        }

        return false;
    }

    //creating chatusers table
    private static void createChatsUsersTable(Integer chatID){

        String sql = "CREATE TABLE IF NOT EXISTS " + chatID + "susers (id INTEGER PRIMARY KEY AUTO_INCREMENT,"
        + "username VARCHAR(50) NOT NULL UNIQUE, "
        + "isadmin BOOLEAN);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("CREATED CHATS USERS TABLE SUCCESSFULLY");

        }catch(SQLException e){
            System.out.println("CREATING CHATS USERS TABLE ERROR: " + e.getMessage());
        }
    }

    //Create chats messages table
    private static void createChatsMessagesTable(Integer chatID){

        String sql = "CREATE TABLE IF NOT EXISTS " + chatID + "'smessages (id INTEGER PRIMERY KEY AUTO_INCREMENT,"
        + "sendername VARCHAR(50) NOT NULL,"
        + "message VARCHAR(500) NOT NULL);";

        try(Connection conn = connect();
        Statement stmt = conn.createStatement()){

            stmt.execute(sql);

            System.out.println("CREATED CHATS MESSAGES TABLE SUCCESSFULLY");
        }catch(SQLException e){

            System.out.println("CREATING MESSAGES TABLE ERROR: " + e.getMessage());
        }

    }

    //get user role in chat(to check if the user is an admin or not while editing). returns true if 
    //the user is an admin
    public static boolean isAdmin(Integer chatId, String username){

        String sql = "SELECT isadmin FROM " + chatId + "susers WHERE username = ?";
        Boolean isAdminBoolean = false;

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            rs.next();
            isAdminBoolean = rs.getBoolean(1);

            System.out.println("GETTING IS CHAT ADMIN SUCCESSFULL");

        }catch(SQLException e){

            System.out.println("GETTING IS CHAT ADMIN ERROR: " + e.getMessage());
        }


        return isAdminBoolean;
    }

    //new message method
    public static boolean newMessage(Integer chatId, String sender, String message){

        String sql = "INSERT INTO " + chatId + "smessages (sendername, message) VALUES (?,?)";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, sender);
            pstmt.setString(2, message);

            pstmt.executeUpdate();

            System.out.println("MESSAGE SENT SUCCESSFULLY");

            return true;

        }catch(SQLException e){
            System.out.println("SENDING MESSAGE ERROR: "+ e.getMessage());
        }

        return false;
    }

    //getuser count in a chat. returns -1 if fails or cant find the user
    public static Integer getMemeberCount(Integer chatId){

        String sql = "SELECT membercount FROM chats WHERE id = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, chatId);
            
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            System.out.println("MEMBER COUNT FOUND SUCCESSFULLY");

            return rs.getInt(1);

        }catch(SQLException e){

            System.out.println("GET MEMBER COUNT ERROR: " + e.getMessage());
        }

        return -1;
    }

    //is user in this chat method. returns boolean. this method will be used to check if the user already
    //exists in a chat to prevent them to joın the same group twice at search chats
    public static boolean isUserInThisChat(Integer chatId, String username){

        String sql = "SELECT COUNT(*) FROM " + chatId + "susers WHERE username = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){

                return rs.getInt(1) > 0;
            }

            System.out.println("CHECKING IF USER EXISTS IN CHAT SUCCESSFULL");

        }catch(SQLException e){

            System.out.println("CHECKING IF USER EXISTS IN A CHAT ERROR: " + e.getMessage());

        }

        return false;
    }
}

