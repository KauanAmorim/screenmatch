package br.com.kauanamorim.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {

    private int season;
    private String title;
    private int number;
    private double rating;
    private LocalDate releaseDate;

    public Episode(int season, EpisodeData episodeData) {
        this.season = season;
        this.title = episodeData.title();
        this.number = episodeData.number();
        this.constructorRating(episodeData.rating());
        this.constructorReleaseDate(episodeData.releaseDate());
    }

    private void constructorRating(String rating) {
        try {
            this.rating = Double.valueOf(rating);
        } catch (NumberFormatException e) {
            this.rating = 0;
        }
    }

    private void constructorReleaseDate(String releaseDate) {
        try {
            this.releaseDate = LocalDate.parse(releaseDate);
        } catch (DateTimeParseException e) {
            this.releaseDate = null;
        }
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getSeason() {
        return season;
    }

    public String getTitle() {
        return title;
    }

    public int getNumber() {
        return number;
    }

    public double getRating() {
        return rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Episode [" +
                "number=" + number +
                ", season=" + season +
                ", title=" + title +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate +
                "]";
    }
}
