/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author James & Gergo...
 */
public class MediaItem {

    private String absolutePath;
    private String relativePath;
    public String title;
    public String album;
    public String artist;
    public String genre;
    //Wrapper class used so that it is null initialised instead of 0 initialised.
    public Integer lengthInSeconds;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.absolutePath.equals(((MediaItem) obj).getAbsolutePath());
    }

    @Override
    public int hashCode() {
        return this.absolutePath.hashCode();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public MediaItem setAbsolutePath(String absolutePath) {

        this.absolutePath = Paths.get(absolutePath).toAbsolutePath().normalize().toString();
        return this;
    }

    public MediaItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public MediaItem setAlbum(String album) {
        this.album = album;
        return this;
    }

    public MediaItem setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getFilename() {

        return new File(absolutePath).getName();

    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public void relativizeMyself(Path sourceFolder) {
        Path relativePath = sourceFolder.relativize(Paths.get(getAbsolutePath()));
        setRelativePath(relativePath.toString());
    }

    public Integer getLengthInSeconds() {
        return lengthInSeconds;
    }

    public void setLengthInSeconds(int lengthInSeconds) {
        this.lengthInSeconds = lengthInSeconds;
    }
    
}
