package com.example.moviepocketandroid.api.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private boolean adult;
    private String backdropPath;
    private String belongsToCollection;
    private int budget;
    private List<String> genres;
    private List<java.lang.Integer> genreIds;
    private String homepage;
    private int id;
    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private List<String> productionCompanies;
    private List<String> productionCountries;
    private String releaseDate;
    private int revenue;
    private int runtime;
    private List<String> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;

    public Movie(boolean adult, String backdropPath, String belongsToCollection, int budget, List<String> genres, String homepage, int id, String imdbId, String originalLanguage, String originalTitle, String overview, double popularity, String posterPath, List<String> productionCompanies, List<String> productionCountries, String releaseDate, int revenue, int runtime, List<String> spokenLanguages, String status, String tagline, String title, boolean video, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdropPath = POSTER_BASE_URL+backdropPath;
        this.belongsToCollection = belongsToCollection;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = POSTER_BASE_URL+posterPath;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public Movie(int id, String posterPath, String title, String releaseDate, List<String>genres) {
        this.id = id;
        this.posterPath = POSTER_BASE_URL+posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }

    public static Movie parsMovie(String movieInfoJson) {
        Movie movie = null;
        try {
            JSONObject jsonObject = new JSONObject(movieInfoJson);
            int id = jsonObject.getInt("id");
            String posterPath = jsonObject.getString("poster_path");
            String title = jsonObject.getString("title");
            String releaseDate = jsonObject.getString("release_date");
            List<Integer> genreIds = parseArrayToInteger(jsonObject.getJSONArray("genre_ids"));
            List<String> genres = getGenresText(genreIds);


            movie = new Movie(id,posterPath,title,releaseDate,genres);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;

    }

    private static List<Integer> parseArrayToInteger(JSONArray jsonArray) throws JSONException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            int value = jsonArray.getInt(i);
            list.add(value);
        }
        return list;
    }


    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getBelongsToCollection() {
        return belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<String> getProductionCompanies() {
        return productionCompanies;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    private final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public static Movie parseMovieInfo(String movieInfoJson) {
        Movie movie = null;
        try {
            JSONObject jsonObject = new JSONObject(movieInfoJson);
            boolean adult = jsonObject.getBoolean("adult");
            String backdropPath = jsonObject.getString("backdrop_path");
            String belongsToCollection = jsonObject.isNull("belongs_to_collection") ? null : jsonObject.getString("belongs_to_collection");
            int budget = jsonObject.getInt("budget");
            List<String> genres = parseArray(jsonObject.getJSONArray("genres"), "name");
            String homepage = jsonObject.getString("homepage");
            int id = jsonObject.getInt("id");
            String imdbId = jsonObject.getString("imdb_id");
            String originalLanguage = jsonObject.getString("original_language");
            String originalTitle = jsonObject.getString("original_title");
            String overview = jsonObject.getString("overview");
            double popularity = jsonObject.getDouble("popularity");
            String posterPath = jsonObject.getString("poster_path");
            List<String> productionCompanies = parseArray(jsonObject.getJSONArray("production_companies"), "name");
            List<String> productionCountries = parseArray(jsonObject.getJSONArray("production_countries"), "name");
            String releaseDate = jsonObject.getString("release_date");
            int revenue = jsonObject.getInt("revenue");
            int runtime = jsonObject.getInt("runtime");
            List<String> spokenLanguages = parseArray(jsonObject.getJSONArray("spoken_languages"), "name");
            String status = jsonObject.getString("status");
            String tagline = jsonObject.getString("tagline");
            String title = jsonObject.getString("title");
            boolean video = jsonObject.getBoolean("video");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");
            movie = new Movie(adult, backdropPath, belongsToCollection, budget, genres, homepage, id, imdbId, originalLanguage, originalTitle, overview, popularity, posterPath, productionCompanies, productionCountries, releaseDate, revenue, runtime, spokenLanguages, status, tagline, title, video, voteAverage, voteCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public Movie(boolean adult, String backdropPath, List<java.lang.Integer> genreIds, int id, String originalLanguage, String originalTitle, String overview, double popularity, String posterPath, String releaseDate, String title, boolean video, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdropPath = POSTER_BASE_URL+backdropPath;
        this.genreIds = genreIds;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = POSTER_BASE_URL+posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public static Movie parseMoviePopular(String movieInfoJson) {
        Movie movie = null;
        try {
            JSONObject jsonObject = new JSONObject(movieInfoJson);
            boolean adult = jsonObject.getBoolean("adult");
            String backdropPath = jsonObject.getString("backdrop_path");
            JSONArray genreIdsArray = jsonObject.getJSONArray("genre_ids");
            List<Integer> genreIds = new ArrayList<>();
            for (int i = 0; i < genreIdsArray.length(); i++) {
                genreIds.add(genreIdsArray.getInt(i));
            }
            int id = jsonObject.getInt("id");
            String originalLanguage = jsonObject.getString("original_language");
            String originalTitle = jsonObject.getString("original_title");
            String overview = jsonObject.getString("overview");
            double popularity = jsonObject.getDouble("popularity");
            String posterPath = jsonObject.getString("poster_path");
            String releaseDate = jsonObject.getString("release_date");
            String title = jsonObject.getString("title");
            boolean video = jsonObject.getBoolean("video");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");

            movie = new Movie(adult, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }


    private static List<String> parseArray(JSONArray jsonArray, String key) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String value = jsonObject.getString(key);
            list.add(value);
        }
        return list;
    }

    private static List<String> getGenresText(List<Integer> genreIds) {
        List<String> genres = new ArrayList<>();

        for (Integer genreId : genreIds) {
            String genreText = getGenreText(genreId);
            if (!genreText.equals("Unknown Genre")) {
                genres.add(genreText);
            }
        }

        return genres;
    }

    private static String getGenreText(int genreId) {
        String genreText;

        switch (genreId) {
            case 28:
                genreText = "Action";
                break;
            case 12:
                genreText = "Adventure";
                break;
            case 16:
                genreText = "Animation";
                break;
            case 35:
                genreText = "Comedy";
                break;
            case 80:
                genreText = "Crime";
                break;
            case 99:
                genreText = "Documentary";
                break;
            case 18:
                genreText = "Drama";
                break;
            case 10751:
                genreText = "Family";
                break;
            case 14:
                genreText = "Fantasy";
                break;
            case 36:
                genreText = "History";
                break;
            case 27:
                genreText = "Horror";
                break;
            case 10402:
                genreText = "Music";
                break;
            case 9648:
                genreText = "Mystery";
                break;
            case 10749:
                genreText = "Romance";
                break;
            case 878:
                genreText = "Science Fiction";
                break;
            case 10770:
                genreText = "TV Movie";
                break;
            case 53:
                genreText = "Thriller";
                break;
            case 10752:
                genreText = "War";
                break;
            case 37:
                genreText = "Western";
                break;
            default:
                genreText = "Unknown Genre";
                break;
        }

        return genreText;
    }

}
