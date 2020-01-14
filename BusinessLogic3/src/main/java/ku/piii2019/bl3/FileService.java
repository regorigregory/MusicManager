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
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author James & Gergo
 */
public interface FileService {
    
    Set<MediaItem> getAllMediaItems(String rootFolder);
    
    Set<MediaItem> getItemsToRemove(Set<Set<MediaItem>> duplicates);
    
    boolean removeFiles(Set<MediaItem> listToRemove);
    
    void copyFiles(String srcFolder, String targetBasePath);
    
    void copyFiles(String srcFolder, String targetBasePath, String filter);
    
    void copyMediaFiles(String srcFolder, String targetBasePath, DuplicateFinder df);
    
    List<Path> filterFileList(String path, String filter);

    Set<MediaItem> getAllID3MediaItems(String rootFolder, DuplicateFinder df);
    
    void writeLineToFile(String fileName, String path, String line);
    
     void refileAndCopyOne(String basepath, MediaItem m);
    void saveM3UFile(Set<MediaItem> filteredItems, String fileNameToSave, String destinationFolder, boolean relativePath);
 
    static Consumer<Path> copyFilesBody(Path sourceFolder, Path targetFolder) {
        return (Path filePath) -> {
            Path relativePath = sourceFolder.relativize(filePath);
            Path targetPath = Paths.get(targetFolder.toString(), relativePath.toString());
            Path newFolder = getFolder(targetPath.getParent().toString());
            try {
                System.out.println(targetPath);
                Files.copy(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioex) {
                CustomLogging.logIt(ioex);
            }
            
        };
        
    }
    
    static Consumer<Path> copyFilesBody(Path targetFolder) {
        return (Path filePath) -> {
            Path targetPath = Paths.get(targetFolder.toString());
            Path fileName = filePath.getFileName();
            Path compiledTargetPath = Paths.get(targetPath.toString(), fileName.toString());
            try {
                System.out.println(targetPath);
                Files.copy(filePath, compiledTargetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioex) {
                CustomLogging.logIt(ioex);
            }
            
        };
        
    }
    
    
    public static Predicate<MediaItem> getProcessDuplicatesBody(DuplicateFinder df, Set<MediaItem> copiedItems, Set<MediaItem> foundDuplicates) {
        return (MediaItem m) -> {
            Set<MediaItem> tempDuplicates = df.getDuplicates(copiedItems, m);
            if (tempDuplicates.size() > 0) {
                foundDuplicates.addAll(tempDuplicates);
                return false;
            }
            copiedItems.add(m);
            return true;            
        };
        
    }

    static boolean isFile(String fileName) {
        
        Path candidateFile = Paths.get(fileName);
        boolean verdict = false;
        
        if (!Files.isDirectory(candidateFile) && Files.exists(candidateFile)) {
            verdict = true;
        }
        return verdict;
    }
    
    static Path getFile(String fileName) {
        if (isFile(fileName)) {
            return Paths.get(fileName);
        }
        try {
            return Files.createFile(Paths.get(fileName));
        } catch (IOException ioex) {
            CustomLogging.logIt(ioex);
        }
        return null;
    }
    
    public static boolean isFolder(String folderCandidate) {
        return Files.isDirectory(Paths.get(folderCandidate));
    }
    
    static Path getFolder(String path) {
        Path candidateFolder = Paths.get(path);
        try {
            if (!Files.isDirectory(candidateFolder)) {
                Files.createDirectories(candidateFolder);
            }
        } catch (IOException ioex) {
            //will have to be replaced by logging!!!!
            CustomLogging.logIt(ioex);
        }
        
        return candidateFolder;
        
    }
   
}
