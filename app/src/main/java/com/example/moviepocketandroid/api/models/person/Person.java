package com.example.moviepocketandroid.api.models.person;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {

    private final String BASE_URL = "https://image.tmdb.org/t/p/w500";
    private final String STANDARD_POSTER_URL = "https://github.com/prymakD/MoviePocket/raw/16f04a6063f407cec8ee8eab29a4bd25c4ae111b/src/main/frontend/src/images/person.png";
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("also_known_as")
    private List<String> alsoKnownAs;
    @SerializedName("biography")
    private String biography;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("deathday")
    private String deathday;
    @SerializedName("gender")
    private int gender;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private int id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    @SerializedName("name")
    private String name;
    @SerializedName("place_of_birth")
    private String placeOfBirth;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("character")
    private String character;
    @SerializedName("order")
    private int order;
    @SerializedName("credit_id")
    private String idCredit;
    @SerializedName("original_name")
    private String originalName;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
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

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
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

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        if (profilePath != null)
            return BASE_URL + profilePath;
        else
            return STANDARD_POSTER_URL;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(String idCredit) {
        this.idCredit = idCredit;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
