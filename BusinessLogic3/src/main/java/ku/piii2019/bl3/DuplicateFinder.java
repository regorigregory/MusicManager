/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Gergo based on James' code
 */
public interface DuplicateFinder {


    boolean areDuplicates(MediaItem m1, MediaItem m2);
    
    default Set<MediaItem> getDuplicates (Set<MediaItem> inThese, MediaItem toThis) {
           Predicate<MediaItem> myFilter = (m)-> (this.areDuplicates(m, toThis));
           return inThese.stream().filter(myFilter).collect(Collectors.toSet());
       //throw new UnsupportedOperationException("Not written yet."); //To change body of generated methods, choose Tools | Templates.
   }
    

    default boolean hasDuplicates(Set<MediaItem> inThese, MediaItem toThis){
        return getDuplicates(inThese, toThis).size()>0 ? true : false;  
    };
    
    
    default public Set<Set<MediaItem>> getAllDuplicates(Set<MediaItem> allMediaItems) {
        Set<Set<MediaItem>> allDuplicates = new HashSet<>();
        Set<MediaItem> possibleDuplicates = new HashSet<>();
        possibleDuplicates.addAll(allMediaItems);
        while (possibleDuplicates.iterator().hasNext()) {
            MediaItem m = possibleDuplicates.iterator().next();
            Set<MediaItem> duplicates = getDuplicates(possibleDuplicates, m);
            if (duplicates.size() > 1) {
                allDuplicates.add(duplicates);
            }
            possibleDuplicates.removeAll(duplicates);
        }
        return allDuplicates;
    }
    
    default public Set<MediaItem> removeAllDuplicates(Set<MediaItem> mis){
        Set<Set<MediaItem>> allDupes = getAllDuplicates(mis);
        allDupes.stream().forEach(dupes->mis.removeAll(dupes));
        return mis;

    }
    
   
    default Set<MediaItem> getMissingItems(Set<MediaItem> myCollection,
            Set<MediaItem> yourCollection) {
        Set<MediaItem> missingItems = new HashSet<>();
        for (MediaItem item : yourCollection) {
            Set<MediaItem> duplicates1 = getDuplicates(myCollection, item);
            if (duplicates1.isEmpty()) {
                Set<MediaItem> duplicates2 = getDuplicates(missingItems, item);
                if (duplicates2.isEmpty()) {
                    missingItems.add(item);
                }
            }
        }
        return missingItems;
    }

}
