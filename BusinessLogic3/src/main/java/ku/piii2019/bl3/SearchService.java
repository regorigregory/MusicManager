/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.Set;

/**
 *
 * @author James
 */
public interface SearchService {
    enum FilterType{
        ARTIST, GENRE;
    }
    Set<MediaItem> find(String  thisSearchTerm, Set<MediaItem> inhere);
    Set<MediaItem> findByArtists(String[] artists,  Set<MediaItem> inhere);
    Set<MediaItem> findByGenres(String[] genres, Set<MediaItem> inhere);
    
    default Set<MediaItem> filterBy(String[] filterWords, Set<MediaItem> inhere, FilterType FILTER_TYPE){
        
        Set<MediaItem> filteredList = null;
        if(FILTER_TYPE == FilterType.ARTIST){
            filteredList = findByArtists(filterWords, inhere);
        } else if(FILTER_TYPE == FilterType.GENRE){
            filteredList = findByArtists(filterWords, inhere);

        }
        return filteredList;
    };
}
