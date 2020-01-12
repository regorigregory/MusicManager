/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ku.piii2019.bl3.CLI.CustomLogging;

/**
 *
 * @author James
 */
public interface FileService {

    Set<MediaItem> getAllMediaItems(String rootFolder);

    Set<MediaItem> getItemsToRemove(Set<Set<MediaItem>> duplicates);

    boolean removeFiles(Set<MediaItem> listToRemove);

    default List<Path> filterFileList(String path, String filter) {
        Path basePath = Paths.get(path);
        List<Path> filteredFiles = new LinkedList<>();
        String regexMask = "(.*)" + filter + "$";
        if (filter != null && filter.length() > 0)
            try {
            Stream<Path> filterable = Files.walk(basePath);
            filterable.filter((p) -> p.toString().matches(regexMask)).forEach((p) -> filteredFiles.add(p));

        } catch (IOException ex) {

        }

        return filteredFiles;
    }

    static Consumer<Path> copyFilesBody(Path sourceFolder, Path targetFolder) {
        return (Path filePath) -> {
            Path relativePath = sourceFolder.relativize(filePath);
            
            Path targetPath = Paths.get(targetFolder.toString(), relativePath.toString());
            Path newFolder = getFolder(targetPath.getParent().toString());
            System.out.println(newFolder);
            try {
                System.out.println(targetPath);
                Files.copy(filePath, targetPath);
            } catch (IOException ioex) {
                CustomLogging.logIt(ioex);
            }

        };

    }

    default void copyFiles(String srcFolder, String targetBasePath) {
        Path sourceFolder = getFolder(srcFolder);
        Path targetFolder = getFolder(targetBasePath);
        Consumer<Path> selectedConsumer = copyFilesBody(sourceFolder, targetFolder);
        try {
            Files.walk(sourceFolder).forEach(selectedConsumer);
        } catch (IOException ioex) {
            CustomLogging.logIt(ioex);

        }
    }

    default void copyFiles(String srcFolder, String targetBasePath, String filter) {
        List<Path> filteredPaths = filterFileList(srcFolder, filter);
        Path sourceFolder = getFolder(srcFolder);
        Path targetFolder = getFolder(targetBasePath);
        Consumer<Path> selectedConsumer = copyFilesBody(sourceFolder, targetFolder);

        filteredPaths.forEach(selectedConsumer);

    }
    
    default void copyMediaFiles(String srcFolder, String targetBasePath, DuplicateFinder df) {
        
        Set<MediaItem> foundMediaItems = getAllMediaItems(srcFolder);
        Set<MediaItem> copiedItems = new HashSet<>();
        Set<MediaItem> foundDuplicates = new HashSet<>();

        Path sourceFolder = getFolder(srcFolder);
        Path targetFolder = getFolder(targetBasePath);
        
        Consumer<Path> selectedConsumer = copyFilesBody(sourceFolder, targetFolder);
        Predicate<MediaItem> processDuplicates =
                (MediaItem m) ->{
                    Set<MediaItem> tempDuplicates = df.getDuplicates(copiedItems, m);
                    if(tempDuplicates.size()>0){
                        foundDuplicates.addAll(tempDuplicates);
                        return false;
                    }
                    copiedItems.add(m);
                  return true;  
                };
        
        if(df!=null){
            foundMediaItems.stream()
                .filter(processDuplicates)
                .map(MediaItem::getAbsolutePath).map(Paths::get)
                .forEach(selectedConsumer);
        } else {
              foundMediaItems.stream()
                .map(MediaItem::getAbsolutePath).map(Paths::get)
                .forEach(selectedConsumer);
        }        
        
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

    default boolean isFolder(String folderCandidate) {
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
    default  Set<MediaItem> getAllID3MediaItems(String rootFolder) {
        Path p = Paths.get(rootFolder);
        if (!p.isAbsolute()) {
            Path currentWorkingFolder = Paths.get("").toAbsolutePath();
            rootFolder = Paths.get(currentWorkingFolder.toString(), rootFolder).toString();
        }
        Set<MediaItem> items = new HashSet<>();
        SimpleFileVisitor myVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith("mp3")) {
                    MediaItem m = new MediaItem();
                    
                    m.setAbsolutePath(file.toString());
                    try{
                           MediaInfoSourceFromID3.getInstance().addMediaInfo(m);
                           items.add(m);
                    
                    }catch(Exception e){
                        CustomLogging.logIt(e);
                    }
                 
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(Paths.get(rootFolder), myVisitor);
        } catch (IOException ex) {}
        return items;
    }
}
