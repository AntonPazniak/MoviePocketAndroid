package com.example.moviepocketandroid.api.models.tv;

class TVCredit {
    private int id;
    private String creditId;
    private String name;
    private int gender;
    private String profilePath;

    public TVCredit(int id, String creditId, String name, int gender, String profilePath) {
        this.id = id;
        this.creditId = creditId;
        this.name = name;
        this.gender = gender;
        this.profilePath = profilePath;
    }

    public int getId() {
        return id;
    }

    public String getCreditId() {
        return creditId;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getProfilePath() {
        return profilePath;
    }
}