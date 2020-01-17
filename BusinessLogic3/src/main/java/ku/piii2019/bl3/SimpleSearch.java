/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author James
 */
public class SimpleSearch implements SearchService {

    @Override
    public Set<MediaItem> find(String thisSearchTerm, Set<MediaItem> inhere) {

        String justSpaces = (thisSearchTerm != null) ? thisSearchTerm.replaceAll(" ", "") : "";
        if (justSpaces.length() == 0) {
            return new HashSet<>();
        }
        String pattern = "(.*)" + thisSearchTerm.toLowerCase().trim() + "(.*)";
        return inhere.stream().filter(m -> {
            return m.getTitle().toLowerCase().matches(pattern) || m.getArtist().toLowerCase().matches(pattern) || m.getAlbum().toLowerCase().matches(pattern);
        }
        ).collect(Collectors.toSet());

    }

    public Set<MediaItem> findByArtists(String[] artists, Set<MediaItem> inhere) {
        Set<MediaItem> matchingItems = new HashSet<>();
        if (artists.length == 0 || inhere.size() == 0) {
            return matchingItems;
        }
        for (String thisSearchTerm : artists) {
            String justSpaces = (thisSearchTerm != null) ? thisSearchTerm.replaceAll(" ", "") : "";
            if (justSpaces.length() == 0) {
                continue;
            }
            String pattern = "(.*)" + thisSearchTerm.toLowerCase().trim() + "(.*)";
            inhere.stream().filter(m -> {
                String artist = m.getArtist()==null ? "undefined" : m.getArtist();
                m.setArtist(artist);
                
                return m.getArtist().toLowerCase().matches(pattern);
            }
            ).forEach(m->matchingItems.add(m));

        }

        return matchingItems;
    }

    @Override
    public Set<MediaItem> findByGenres(String[] genres, Set<MediaItem> inhere) {

     Set<MediaItem> matchingItems = new HashSet<>();
        if (genres.length == 0 || inhere.size() == 0) {
            return matchingItems;
        }
        for (String thisSearchTerm : genres) {
            String justSpaces = (thisSearchTerm != null) ? thisSearchTerm.replaceAll(" ", "") : "";
            if (justSpaces.length() == 0) {
                continue;
            }
            String pattern = "(.*)" + thisSearchTerm.toLowerCase().trim() + "(.*)";
            inhere.stream().filter(m -> {
                String genre = m.getGenre()==null ? "undefined" : m.getGenre();
                m.setGenre(genre);
                return m.getGenre().toLowerCase().matches(pattern);
            }
            ).forEach(m->matchingItems.add(m));

        }

        return matchingItems;

    }

}
