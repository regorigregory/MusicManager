/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

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
        String justSpaces = thisSearchTerm.replaceAll(" ", "");
        if(thisSearchTerm==null || thisSearchTerm.length()==0 || justSpaces.length()==0){
            return null;
        }
        String pattern = "(.*)"+thisSearchTerm.toLowerCase()+"(.*)";
        return inhere.stream().filter(m -> {
            return m.getTitle().toLowerCase().matches(pattern) || m.getArtist().toLowerCase().matches(pattern) || m.getAlbum().toLowerCase().matches(pattern);}
        ).collect(Collectors.toSet());
       
    }

}
