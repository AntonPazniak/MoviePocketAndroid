package com.example.moviepocketandroid.api.models.tv;

import static com.example.moviepocketandroid.api.models.Movie.getGenresText;
import static com.example.moviepocketandroid.api.models.Movie.parseArray;
import static com.example.moviepocketandroid.api.models.Movie.parseArrayToInteger;

import com.example.moviepocketandroid.api.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TVSeries {

    private String backdropPath;
    private String firstAirDate;
    private List<String> genres;
    private int id;
    private String name;
    private List<String> productionCountries;
    private String originalLanguage;
    private String originalName;
    private String overview;
    private double popularity;
    private String posterPath;
    private double voteAverage;
    private int voteCount;
    private final String URL = "https://image.tmdb.org/t/p/w500";
    private String[] languages;
    private String status;
    private String tagline;
    private String type;
    private int numberOfSeasons;
    private int numberOfEpisodes;
    private List<TVSeason> seasons;
    private List<TVNetwork> networks;
    private List<TVCredit> createdby;


    public TVSeries(String backdropPath, String firstAirDate, int id, String name, String originalLanguage, String originalName, String overview,
                    double popularity, String posterPath, double voteAverage, int voteCount, List<String> genres) {
        this.backdropPath = URL + backdropPath;
        this.firstAirDate = firstAirDate;
        this.genres = genres;
        this.id = id;
        this.name = name;
        this.originalLanguage = originalLanguage;
        this.originalName = originalName;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = URL+posterPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public TVSeries(String backdropPath, String firstAirDate, int id, String name, String originalLanguage, String originalName, String overview,
                    double popularity, String posterPath, double voteAverage, int voteCount,
                    String[] languages, String status, String tagline, String type,
                    int numberOfSeasons, int numberOfEpisodes, List<TVSeason> seasons, List<TVCredit> createdby, List<String> genres, List<String> productionCountries) {
        this.backdropPath = URL + backdropPath;
        this.firstAirDate = firstAirDate;
        this.genres = genres;
        this.id = id;
        this.name = name;
        this.originalLanguage = originalLanguage;
        this.originalName = originalName;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = URL + posterPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.languages = languages;
        this.status = status;
        this.tagline = tagline;
        this.type = type;
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
        this.seasons = seasons;
        this.createdby = createdby;
        this.productionCountries = productionCountries;
    }

    public TVSeries(String name, int id, String posterPath, List<String> genres, double voteAverage) {
        this.name = name;
        this.id = id;
        this.posterPath = URL + posterPath;
        this.genres = genres;
        this.voteAverage = voteAverage;
    }

    //done
    public static TVSeries parseTVSeriesPopularInfo(String tvSeriesInfoJson) {
        TVSeries tvSeries = null;
        try {
            JSONObject jsonObject = new JSONObject(tvSeriesInfoJson);
            String backdropPath = jsonObject.getString("backdrop_path");
            String firstAirDate = jsonObject.getString("first_air_date");
            List<Integer> genreIds = parseArrayToInteger(jsonObject.getJSONArray("genre_ids"));
            List<String> genres = getGenresText(genreIds);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String originalLanguage = jsonObject.getString("original_language");
            String originalName = jsonObject.getString("original_name");
            String overview = jsonObject.getString("overview");
            double popularity = jsonObject.getDouble("popularity");
            String posterPath = jsonObject.getString("poster_path");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");
            tvSeries = new TVSeries(backdropPath, firstAirDate, id, name, originalLanguage, originalName, overview, popularity, posterPath, voteAverage, voteCount, genres);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tvSeries;
    }

    //done
    public static TVSeries pars(String tvSeriesInfoJson) {
        TVSeries tvSeries = null;
        try {
            JSONObject jsonObject = new JSONObject(tvSeriesInfoJson);
            int id = jsonObject.getInt("id");
            String posterPath = jsonObject.getString("poster_path");
            String name = jsonObject.getString("name");
            List<Integer> genreIds = parseArrayToInteger(jsonObject.getJSONArray("genre_ids"));
            List<String> genres = getGenresText(genreIds);
            double voteAverage = jsonObject.getDouble("vote_average");
            tvSeries = new TVSeries(name, id, posterPath, genres, voteAverage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tvSeries;
    }


    public static TVSeries parseTVSeriesInfo(String tvSeriesInfoJson) {
        TVSeries tvSeries = null;
        try {
            JSONObject jsonObject = new JSONObject(tvSeriesInfoJson);
            String backdropPath = jsonObject.getString("backdrop_path");
            String firstAirDate = jsonObject.getString("first_air_date");
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String originalLanguage = jsonObject.getString("original_language");
            String originalName = jsonObject.getString("original_name");
            String overview = jsonObject.getString("overview");
            double popularity = jsonObject.getDouble("popularity");
            String posterPath = jsonObject.getString("poster_path");
            double voteAverage = jsonObject.getDouble("vote_average");
            int voteCount = jsonObject.getInt("vote_count");
            String[] languages = parseStringArray(jsonObject.getJSONArray("languages"));
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

            List<TVCredit> createdby = new ArrayList<>();
            JSONArray createdbyArray = jsonObject.getJSONArray("created_by");
            List<String> genres = parseArray(jsonObject.getJSONArray("genres"), "name");
            tvSeries = new TVSeries(backdropPath, firstAirDate, id, name, originalLanguage, originalName, overview, popularity, posterPath, voteAverage, voteCount, languages, status, tagline, type, numberOfSeasons, numberOfEpisodes, seasons, createdby, genres, productionCountries);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return tvSeries;
    }

    private static String[] parseStringArray(JSONArray jsonArray) throws JSONException {
        String[] result = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            result[i] = jsonArray.getString(i);
        }
        return result;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
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

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getURL() {
        return URL;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getType() {
        return type;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public List<TVSeason> getSeasons() {
        return seasons;
    }

    public List<TVNetwork> getNetworks() {
        return networks;
    }

    public List<TVCredit> getCreatedby() {
        return createdby;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }
}
