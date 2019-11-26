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
public class ApproxSearch implements SearchService{

    @Override
    public Set<MediaItem> find(String thisSearchTerm, Set<MediaItem> inhere) {
        
        String justSpaces = (thisSearchTerm!=null) ? thisSearchTerm.replaceAll(" ", "") : "";
        if(justSpaces.length()==0){
            return new HashSet<>();
        }
        LevenshteinDistance instance = new LevenshteinDistance();
        String pattern =thisSearchTerm.toLowerCase().trim();
        
        return inhere.stream().filter(m -> {
//            System.out.println(instance.apply(pattern, m.getTitle().toLowerCase().trim()));
//            System.out.println(instance.apply(pattern, m.getTitle().toLowerCase().trim()));
//            System.out.println(instance.apply(pattern, m.getTitle().toLowerCase().trim()));

            return 
                  instance.apply(pattern, m.getTitle().toLowerCase().trim())<=1 ||
                  instance.apply(pattern, m.getArtist().toLowerCase().trim())<=1 || 
                  instance.apply(pattern, m.getAlbum().toLowerCase().trim())<=1;
        }
        ).collect(Collectors.toSet());
       
    }


    }
    
    

