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
        
        String justSpaces = (thisSearchTerm!=null) ? thisSearchTerm.replaceAll(" ", "") : "";
        if(justSpaces.length()==0){
            return new HashSet<>();
        }
        String pattern = "(.*)"+thisSearchTerm.toLowerCase().trim()+"(.*)";
        return inhere.stream().filter(m -> {
            return m.getTitle().toLowerCase().matches(pattern) || m.getArtist().toLowerCase().matches(pattern) || m.getAlbum().toLowerCase().matches(pattern);}
        ).collect(Collectors.toSet());
       
    }

}
