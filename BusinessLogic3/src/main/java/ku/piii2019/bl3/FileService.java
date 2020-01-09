/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author James
 */
public interface FileService {
    Set<MediaItem>          getAllMediaItems                   (String rootFolder);
    Set<MediaItem>          getItemsToRemove                   (Set<Set<MediaItem>> duplicates);
    boolean                 removeFiles                        (Set<MediaItem> listToRemove);
    
   
    
    
    default List<Path> filterFileList (String path, String filter){
        Path basePath = Paths.get(path);
        List<Path> filteredFiles = new LinkedList<>();
        String regexMask = "(.*)"+filter+"$";
        if(filter !=null && filter.length()>0)
            try{
                Stream<Path> filterable = Files.walk(basePath);
                filterable.filter((p)-> p.toString().matches(regexMask)).forEach((p)->filteredFiles.add(p));
 
            }catch(IOException ex)
            {

            }
        
        return filteredFiles;    
    }
    
     default void moveFiles(String path){
        Path candidateFolder = Paths.get(path);
        throw UnsupportedOpreationException();
        
//        try{
//            if(!Files.isDirectory(candidateFolder)){
//                Files.createDirectories(candidateFolder);
//            }
//        } catch (IOException ex){
//            //will have to be replaced by logging!!!!
//            ex.printStackTrace();
//        }
//        
//        return candidateFolder;
 
    }
    
    default List<MediaItem> getRawMediaItems(String path) {
        List<Path> rawPaths=  filterFileList(path, "\\.mp3");
        return rawPaths.stream().map((p)->{
                MediaItem m = new MediaItem().setAbsolutePath(path);
                try{
                MediaInfoSource.staticAddMediaInfo(m);
                } catch (Exception ex){
                    //System.out.println();
                    ex.printStackTrace();
                }
                return m;
                })
        .collect(Collectors.toList());
          
    }
    
   
    default boolean isFile(String fileName){
        
        Path candidateFile = Paths.get(fileName);
        boolean verdict = false;
        
        if(!Files.isDirectory(candidateFile) && Files.exists(candidateFile)){
            verdict = true;
        }
        return verdict;
    }
    
    default Path getFile(String fileName){
        if(isFile(fileName)){
            return Paths.get(fileName);
        }
        return null;
        
    }
    
    default boolean isFolder(String folderCandidate){
        return Files.isDirectory(Paths.get(folderCandidate));
    }
    default Path getFolder(String path){
        Path candidateFolder = Paths.get(path);
        try{
            if(!Files.isDirectory(candidateFolder)){
                Files.createDirectories(candidateFolder);
            }
        } catch (IOException ex){
            //will have to be replaced by logging!!!!
            ex.printStackTrace();
        }
        
        return candidateFolder;
 
    }
    
   
   
    
}
