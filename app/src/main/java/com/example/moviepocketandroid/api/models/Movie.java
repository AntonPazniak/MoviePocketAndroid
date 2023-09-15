package com.example.moviepocketandroid.api.models;
import com.example.moviepocketandroid.api.models.tv.TVNetwork;
import com.example.moviepocketandroid.api.models.tv.TVSeason;

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
    private String type;
    private int numberOfSeasons;
    private int numberOfEpisodes;
    private List<TVSeason> seasons;
    private List<TVNetwork> networks;
    private final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";

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

    public Movie(boolean adult, String backdropPath, List<java.lang.Integer> genreIds, int id, String originalLanguage, String originalTitle, String overview, double popularity, String posterPath, String releaseDate, String title, boolean video, double voteAverage, int voteCount, List<String> genres) {
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
        this.genres = genres;
    }

    public Movie(int id, String posterPath, String title, String releaseDate, List<String>genres,double voteAverage) {
        this.id = id;
        this.posterPath = POSTER_BASE_URL+posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.voteAverage = voteAverage;
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
            double voteAverage = jsonObject.getDouble("vote_average");

            movie = new Movie(id,posterPath,title,releaseDate,genres,voteAverage);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
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

    public static Movie parseMoviePopular(String movieInfoJson) {
        Movie movie = null;
        try {
            JSONObject jsonObject = new JSONObject(movieInfoJson);
            boolean adult = jsonObject.getBoolean("adult");
            String backdropPath = jsonObject.getString("backdrop_path");
            List<Integer> genreIds = parseArrayToInteger(jsonObject.getJSONArray("genre_ids"));
            List<String> genres = getGenresText(genreIds);
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

            movie = new Movie(adult, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount,genres);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
    public static Movie parsTVSeries(String tvSeriesInfoJson) {
        Movie movie = null;
        try {
            JSONObject jsonObject = new JSONObject(tvSeriesInfoJson);
            int id = jsonObject.getInt("id");
            id = id * -1;

            String posterPath = jsonObject.getString("poster_path");
            String name = jsonObject.getString("name");
            List<Integer> genreIds = parseArrayToInteger(jsonObject.getJSONArray("genre_ids"));
            List<String> genres = getGenresText(genreIds);
            String releaseDate = jsonObject.getString("first_air_date");
            double voteAverage = jsonObject.getDouble("vote_average");
            movie = new Movie(id,posterPath,name,releaseDate,genres,voteAverage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public static Movie parseTVSeriesPopularInfo(String tvSeriesInfoJson) {
        Movie tvSeries = null;
        try {
            JSONObject jsonObject = new JSONObject(tvSeriesInfoJson);
            int id = jsonObject.getInt("id");
            id = id * -1;

            String backdropPath = jsonObject.getString("backdrop_path");
            String releaseDate = jsonObject.getString("first_air_date");
            List<Integer> genreIds = parseArrayToInteger(jsonObject.getJSONArray("genre_ids"));
            List<String> genres = getGenresText(genreIds);
            String title = jsonObject.getString("name");
            String originalLanguage = jsonObject.getString("original_language");
            String originalTitle = jsonObject.getString("original_name");
            String overview = jsonObject.getString("overview");
            double popularity = jsonObject.getDouble("popularity");
            String posterPath = jsonObject.getString("poster_path");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");
            tvSeries = new Movie(true, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title,
                    false, voteAverage, voteCount,genres);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tvSeries;
    }

    public static Movie parseTVSeriesInfo(String tvSeriesInfoJson) {
        Movie tvSeries = null;
        try {
            JSONObject jsonObject = new JSONObject(tvSeriesInfoJson);
            int id = jsonObject.getInt("id");
            id = id * -1;

            String backdropPath = jsonObject.getString("backdrop_path");
            String releaseDate = jsonObject.getString("first_air_date");
            String title = jsonObject.getString("name");
            String originalLanguage = jsonObject.getString("original_language");
            String originalTitle = jsonObject.getString("original_name");
            String overview = jsonObject.getString("overview");
            double popularity = jsonObject.getDouble("popularity");
            String posterPath = jsonObject.getString("poster_path");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");
            //List<String> languages = parseStringArray(jsonObject.getJSONArray("languages"));
            String status = jsonObject.getString("status");
            String tagline = jsonObject.getString("tagline");
            String type = jsonObject.getString("type");
            int numberOfSeasons = jsonObject.getInt("number_of_seasons");
            int numberOfEpisodes = jsonObject.getInt("number_of_episodes");

            List<TVSeason> seasons = new ArrayList<>();
            JSONArray seasonsArray = jsonObject.getJSONArray("seasons");
            for (int i = 0; i < seasonsArray.length(); i++) {
                JSONObject seasonObject = seasonsArray.getJSONObject(i);
                TVSeason season = new TVSeason(seasonObject.getString("air_date"),
                        seasonObject.getInt("episode_count"),
                        seasonObject.getInt("id"),
                        seasonObject.getString("name"),
                        seasonObject.getString("overview"),
                        seasonObject.getString("poster_path"),
                        seasonObject.getInt("season_number"),
                        seasonObject.getDouble("vote_average"));
                seasons.add(season);
            }
            List<String> productionCountries = parseArray(jsonObject.getJSONArray("production_countries"), "name");

            JSONArray productionCompanies = jsonObject.getJSONArray("created_by");
            List<String> genres = parseArray(jsonObject.getJSONArray("genres"), "name");
            tvSeries  = new Movie(true, backdropPath, "", 0, genres, "", id, "imdbId", originalLanguage, originalTitle,
                    overview, popularity, posterPath, null, productionCountries, releaseDate, 0, 0,
                    null, status, tagline, title, false, voteAverage, voteCount);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return tvSeries;
    }

    public static List<Integer> parseArrayToInteger(JSONArray jsonArray) throws JSONException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            int value = jsonArray.getInt(i);
            list.add(value);
        }
        return list;
    }
    public static List<String> parseArray(JSONArray jsonArray, String key) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String value = jsonObject.getString(key);
            list.add(value);
        }
        return list;
    }
    public static List<String> getGenresText(List<Integer> genreIds) {
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

    public List<Integer> getGenreIds() {
        return genreIds;
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
}
