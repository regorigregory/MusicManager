/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 *
 * @author James
 */
public class ApproxSearch implements SearchService {

    @Override
    public Set<MediaItem> find(String thisSearchTerm, Set<MediaItem> inhere) {

        String justSpaces = (thisSearchTerm != null) ? thisSearchTerm.replaceAll(" ", "") : "";
        if (justSpaces.length() == 0) {
            return new HashSet<>();
        }
        LevenshteinDistance instance = new LevenshteinDistance();
        String pattern = thisSearchTerm.toLowerCase().trim();

        return inhere.stream().filter(m -> {

            return instance.apply(pattern, m.getTitle().toLowerCase().trim()) <= 1
                    || instance.apply(pattern, m.getArtist().toLowerCase().trim()) <= 1
                    || instance.apply(pattern, m.getAlbum().toLowerCase().trim()) <= 1;
        }
        ).collect(Collectors.toSet());

    }

    @Override
    public Set<MediaItem> findByArtists(String[] artists, Set<MediaItem> inhere) {
        Set<MediaItem> matchingItems = new HashSet<>();
        if (artists.length == 0 || inhere.size() == 0) {
            return matchingItems;
        }

        LevenshteinDistance instance = new LevenshteinDistance();

        for (String artist : artists) {
            
            String justSpaces = (artist != null) ? artist.replaceAll(" ", "") : "";
            
            if (justSpaces.length() == 0) {
                continue;
            }
            
            String pattern = artist.toLowerCase().trim();
            inhere.stream()
            .filter( m -> (instance.apply(pattern, m.getArtist().toLowerCase().trim()) <= 1))
            .forEach(m->matchingItems.add(m));
        }
        return matchingItems;
    }

    @Override
    public Set<MediaItem> findByGenres(String[] genres, Set<MediaItem> inhere) {

         Set<MediaItem> matchingItems = new HashSet<>();
        if (genres.length == 0 || inhere.size() == 0) {
            return matchingItems;
        }

        LevenshteinDistance instance = new LevenshteinDistance();

        for (String genre : genres) {
            
            String justSpaces = (genre != null) ? genre.replaceAll(" ", "") : "";
            
            if (justSpaces.length() == 0) {
                continue;
            }
            
            String pattern = genre.toLowerCase().trim();
            inhere.stream()
            .filter( m -> (instance.apply(pattern, m.getGenre().toLowerCase().trim()) <= 1))
            .forEach(m->matchingItems.add(m));
        }
        return matchingItems;

    }

}
