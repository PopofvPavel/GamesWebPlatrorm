package com.example.webprojectgames.model.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "release_date")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date releaseDate;
    @ManyToMany
    @JoinTable(
            name = "game_platforms",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private List<Platform> platforms;

    @Column(name = "developer")
    private String developer;
    @Column(name = "editor_id")
    private int editorId;
    @Column(name = "image_url")
    private String imageUrl;

    public Game(String title, String description, Date releaseDate, List<Platform> platform, String developer) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.platforms = platform;
        this.developer = developer;
    }

    public Game() {

    }
    public int getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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

    public List<Platform> getPlatform() {
        return platforms;
    }

    public void setPlatform(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
