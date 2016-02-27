package com.jasonbutwell.top10downloader2;

/**
 * Created by jason on 26/02/16.
 */
public class Application {
    private String name;
    private String artist;
    private String releaseDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Here we override the toString() method so that we can return the fields that we want
    // These will be displayed as the list entries within the ListView

    @Override
    public String toString() {

        return  "Name: "+getName() + "\n" +
                "Artist: "+getArtist() + "\n" +
                "Release Date:"+getReleaseDate() + "\n";
    }
}
