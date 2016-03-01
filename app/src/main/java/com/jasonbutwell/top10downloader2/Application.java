package com.jasonbutwell.top10downloader2;

/**
 * Created by jason on 26/02/16.
 */

    // We use this class to read information from the tags of the XML
    // name, artist and releaseDate

public class Application {
    private String name;
    private String artist;
    private String releaseDate;

    // Return the name field
    public String getName() {
        return name;
    }

    // Set the name field
    public void setName(String name) {
        this.name = name;
    }

    // Get the artist name
    public String getArtist() {
        return artist;
    }

    // Set the artist name
    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Get the release date
    public String getReleaseDate() {
        return releaseDate;
    }

    // Set the release date
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Here we override the toString() method so that we can return the fields that we want
    // These will be displayed as the list entries within the ListView

    // here we override the default toString() method so we can return all of the fields as one concatenated string.

    @Override
    public String toString() {

        return  "Name: "+getName() + "\n" +
                "Artist: "+getArtist() + "\n" +
                "Release Date:"+getReleaseDate() + "\n";
    }
}
