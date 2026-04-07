package com.movies;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class TmdbService {
    
    private static final String API_KEY = "8b7e46572422c564d98f4f02e7501f3d";
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    private static String connectAndGetJson(String endpoint){

        try{

            String fullUrl = BASE_URL + endpoint + "?api_key=" + API_KEY;
            URL url = new URL(fullUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if(conn.getResponseCode() != 200){

                return "CONNECTION FAILED ERROR: " + conn.getResponseMessage();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            return response.toString();

        }catch(Exception e){
            return "EXCEPTION: " + e.getMessage();
        }
    }

    //This method is used to get the movie by the movie Id json. wont be used
    private static String getMovieDetailsJson(int movieID){

        return connectAndGetJson("/movie/" + movieID);
    }

    //Search movie method json. wont be used
    private static String searchMovieJson(String query){

        return connectAndGetJson("/search/movie?query=" + query.replace(" ", "%20"));
    }

    //This method gets the movie name searched by its ID
    public static String getMovieName(Integer movieId){

        String jsonString = getMovieDetailsJson(movieId);
        JSONObject movie = new JSONObject(jsonString);

        return movie.getString("title");
    }

    //This method returns the movies photo URL by its ID
    public static String getMoviePhotoUrl(Integer movieId){

        String jsonString = getMovieDetailsJson(movieId);
        JSONObject movie = new JSONObject(jsonString);

        return "https://image.tmdb.org/t/p/w500" + movie.getString("poster_path");
    }

    //This method returns the director of the movie
    public static String getMovieDirector(Integer movieId){

        String jsonString = connectAndGetJson("/movie/" + movieId + "/credits");
        JSONObject response = new JSONObject(jsonString);
        JSONArray crew =response.getJSONArray("crew");

        for(int i = 0; i < crew.length(); i++){

            JSONObject member = crew.getJSONObject(i);

            if(member.getString("job").equals("Director")){

                return member.getString("name");
            }
        }

        return "?";
    }

    //This method gets the ID list of the movies that contains the words we searched
    public static ArrayList<Integer> searchMovie(String query){

        String jsonString = searchMovieJson(query);
        JSONObject response = new JSONObject(jsonString);
        JSONArray results = response.getJSONArray("results");

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i = 0; i < results.length(); i++){

            list.add(results.getJSONObject(i).getInt("id"));
        }

        return list;
    }

    //This method gets the ID list of the popular movies
    public static ArrayList<Integer> getPopularMovies(){

        String jsonString = connectAndGetJson("/movie/popular");
        JSONObject response = new JSONObject(jsonString);
        JSONArray results = response.getJSONArray("results");

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i = 0; i < results.length(); i++){

            list.add(results.getJSONObject(i).getInt("id"));
        }

        return list;
    }

    //This method gets the ID list of the latest releases movies
    public static ArrayList<Integer> getLatestReleasesMovies(){

        String jsonString = connectAndGetJson("/movie/now_playing");
        JSONObject response = new JSONObject(jsonString);
        JSONArray results = response.getJSONArray("results");

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i = 0; i < results.length(); i++){

            list.add(results.getJSONObject(i).getInt("id"));
        }

        return list;
    }

    //Get movie year
    public static String getYear(Integer movieId){

        String jsonString = getMovieDetailsJson(movieId);
        JSONObject movie = new JSONObject(jsonString);
        String releaseDate = movie.getString("release_date");

        return releaseDate.split("-")[0];

    }

    //Get movie duration. returns minutes as Integer
    public static Integer getDuration(Integer movieId){

        String jsonString = getMovieDetailsJson(movieId);
        JSONObject movie = new JSONObject(jsonString);

        return movie.getInt("runtime");
    }

    //get movie genres method. returns the genre of the movie as string arraylist
    public static ArrayList<String> getMovieGenres(Integer movieId){

        String jsonString = getMovieDetailsJson(movieId);
        JSONObject movie = new JSONObject(jsonString);
        JSONArray genresArray = movie.getJSONArray("genres");

        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < genresArray.length(); i++){

            list.add(genresArray.getJSONObject(i).getString("name"));

        }

        return list;
    }

    //This method returns the summary of the movie
    public static String getSummary(Integer movieId){

        String jsonString = getMovieDetailsJson(movieId);
        JSONObject movie = new JSONObject(jsonString);

        return movie.getString("overview");
    }

    //This method gets the youtube trailer link
    public static String getTrailer(Integer movieID){

        String jsonString = connectAndGetJson("/movie/" + movieID + "/videos");
        JSONObject response = new JSONObject(jsonString);
        JSONArray results = response.getJSONArray("results");

        for(int i = 0; i < results.length(); i++){

            JSONObject video = results.getJSONObject(i);

            if(video.getString("site").equalsIgnoreCase("YouTube") 
            && video.getString("type").equalsIgnoreCase("Trailer")){

                String videoKey = video.getString("key");
                return "https://www.youtube.com/watch?v=" + videoKey;
            }
        }

        return " ";
    }

}
