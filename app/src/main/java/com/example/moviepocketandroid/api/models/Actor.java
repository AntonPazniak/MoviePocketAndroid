package com.example.moviepocketandroid.api.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Actor {

    private boolean adult;
    private List<String> alsoKnownAs;
    private String biography;
    private String birthday;
    private String deathDay;
    private int gender;
    private String homepage;
    private int id;
    private String imdbId;
    private String knownForDepartment;
    private String name;
    private String placeOfBirth;
    private double popularity;
    private String profilePath;
    private String originalName;
    private int castId;
    private String character;
    private String creditId;
    private int order;

    private final String URL = "https://image.tmdb.org/t/p/w500/";
    private final String STANDARD_URL = "https://github.com/prymakD/MoviePocket/raw/16f04a6063f407cec8ee8eab29a4bd25c4ae111b/src/main/frontend/src/images/person.png";

    public Actor(boolean adult, List<String> alsoKnownAs, String biography, String birthday, String deathDay,
                 int gender, String homepage, int id, String imdbId, String knownForDepartment, String name,
                 String placeOfBirth, double popularity, String profilePath) {
        this.adult = adult;
        this.alsoKnownAs = alsoKnownAs;
        this.biography = biography;
        this.birthday = birthday;
        this.deathDay = deathDay;
        this.gender = gender;
        this.homepage = homepage;
        this.id = id;
        this.imdbId = imdbId;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.popularity = popularity;
        if (!profilePath.equals("null"))
            this.profilePath = URL + profilePath;
        else
            this.profilePath = STANDARD_URL;
    }

    public Actor(boolean adult, int gender, int id, String knownForDepartment, String name, String originalName, double popularity, String profilePath, String character, String creditId, int order) {
        this.adult = adult;
        this.gender = gender;
        this.id = id;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.originalName = originalName;
        this.popularity = popularity;
        this.character = character;
        this.creditId = creditId;
        this.order = order;
        if (!profilePath.equals("null"))
            this.profilePath = URL + profilePath;
        else
            this.profilePath = STANDARD_URL;
    }

    public static Actor parseActor(String actorInfoJson) {
        Actor actor = null;
        try {
            JSONObject jsonObject = new JSONObject(actorInfoJson);
            boolean adult = jsonObject.getBoolean("adult");
            int gender = jsonObject.getInt("gender");
            int id = jsonObject.getInt("id");
            String knownForDepartment = jsonObject.getString("known_for_department");
            String name = jsonObject.getString("name");
            String originalName = jsonObject.getString("original_name");
            double popularity = jsonObject.getDouble("popularity");
            String profilePath = jsonObject.getString("profile_path");
            String character = jsonObject.getString("character");
            String creditId = jsonObject.getString("credit_id");
            int order = jsonObject.getInt("order");
            actor = new Actor(adult,gender,id,knownForDepartment,name,originalName,popularity,profilePath,character,creditId,order);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actor;
    }
    public static Actor parsePopularActor(String actorInfoJson) {
        Actor actor = null;
        try {
            JSONObject jsonObject = new JSONObject(actorInfoJson);
            int id = jsonObject.getInt("id");
            String knownForDepartment = jsonObject.getString("known_for_department");
            String name = jsonObject.getString("name");
            double popularity = jsonObject.getDouble("popularity");
            String profilePath = jsonObject.getString("profile_path");
            actor = new Actor(id,knownForDepartment,name,popularity,profilePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actor;
    }

    public Actor(int id, String knownForDepartment, String name, double popularity, String profilePath) {
        this.id = id;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.popularity = popularity;
        if (!profilePath.equals("null"))
            this.profilePath = URL + profilePath;
        else
            this.profilePath = STANDARD_URL;
    }

    public static Actor parsPerson(String actorInfoJson) {
        Actor actor = null;
        try {
            JSONObject jsonObject = new JSONObject(actorInfoJson);
            boolean adult = jsonObject.getBoolean("adult");
            JSONArray alsoKnownAsArray = jsonObject.getJSONArray("also_known_as");
            List<String> alsoKnownAs = parseAlsoKnownAs(alsoKnownAsArray);
            String biography = jsonObject.getString("biography");
            String birthday = jsonObject.getString("birthday");
            String deathDay = jsonObject.getString("deathday");
            int gender = jsonObject.getInt("gender");
            String homepage = jsonObject.getString("homepage");
            int id = jsonObject.getInt("id");
            String imdbId = jsonObject.getString("imdb_id");
            String knownForDepartment = jsonObject.getString("known_for_department");
            String name = jsonObject.getString("name");
            String placeOfBirth = jsonObject.getString("place_of_birth");
            double popularity = jsonObject.getDouble("popularity");
            String profilePath = jsonObject.getString("profile_path");

            actor = new Actor(adult, alsoKnownAs, biography, birthday, deathDay, gender, homepage, id,
                    imdbId, knownForDepartment, name, placeOfBirth, popularity, profilePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actor;
    }

    private static List<String> parseAlsoKnownAs(JSONArray alsoKnownAsArray) throws JSONException {
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsArray.length(); i++) {
            String alias = alsoKnownAsArray.getString(i);
            alsoKnownAs.add(alias);
        }
        return alsoKnownAs;
    }


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(String deathDay) {
        this.deathDay = deathDay;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
}
