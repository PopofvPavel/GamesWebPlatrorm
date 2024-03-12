package com.example.webprojectgames.model.entities;

import java.util.Date;
import java.util.List;

public class SteamGame {

    private long steamId;
    private String title;
    private String description;
    private Date releaseDate;
    private List<String> platform;

    private List<String> genres;
    private String developer;

    private String image_url;

    public SteamGame(String title, String description, Date release_date, List<String> platforms,
                     String developer, String image_url, List<String> genres) {
        this.title = title;
        this.description = description;
        this.releaseDate = release_date;
        this.platform = platforms;
        this.developer = developer;
        this.genres = genres;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public long getSteamId() {
        return steamId;
    }

    public void setSteamId(long steamId) {
        this.steamId = steamId;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
