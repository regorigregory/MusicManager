/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.List;
import java.util.Set;

/**
 *
 * @author James
 */
public interface FileService {
    Set<MediaItem>          getAllMediaItems    (String rootFolder);
    Set<MediaItem>          getItemsToRemove    (Set<Set<MediaItem>> duplicates);
    boolean                 removeFiles         (Set<MediaItem> listToRemove);
    
}
