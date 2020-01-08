/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 *
 * @author James
 */
public interface FileService {
    Set<MediaItem>          getAllMediaItems                   (String rootFolder);
    Set<MediaItem>          getItemsToRemove                   (Set<Set<MediaItem>> duplicates);
    boolean                 removeFiles                        (Set<MediaItem> listToRemove);
    
    
    Set<Path>               getAllFiles                        (String rootFolder);
    Set<Path>               getM3UFiles                        (String rootFolder);
    
    boolean                 moveMP3Files                        (String srcFolder, String dstFolder);
    boolean                 moveMP3FilesExcludingDuplicates     (String srcFolder, String dstFolder, DuplicateFinder df);
    boolean                 copyMP3Files                        (String srcFolder, String dstFolder);
    boolean                 copyMP3FilesExcludingDuplicates     (String srcFolder, String dstFolder, DuplicateFinder df);
    
     boolean                 moveMP3Files                       (List<MediaItem> srcFolder, String dstFolder);
    boolean                 moveMP3FilesExcludingDuplicates     (List<MediaItem> srcFolder, String dstFolder, DuplicateFinder df);
    boolean                 copyMP3Files                        (List<MediaItem> srcFolder, String dstFolder);
    boolean                 copyMP3FilesExcludingDuplicates     (List<MediaItem> srcFolder, String dstFolder, DuplicateFinder df);
   
    
}
