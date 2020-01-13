/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static ku.piii2019.bl3.FileService.copyFilesBody;
import static ku.piii2019.bl3.FileService.getFolder;
import static ku.piii2019.bl3.FileService.getProcessDuplicatesBody;

/**
 *
 * @author James
 */
public class FileServiceImpl implements FileService {

    private static FileServiceImpl instance = null;

    private FileServiceImpl() {
    }

    public static FileServiceImpl getInstance() {
        if (instance == null) {
            instance = new FileServiceImpl();
        }
        return instance;
    }

    @Override
    public Set<MediaItem> getAllMediaItems(String rootFolder) {
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
                    items.add(m);
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(Paths.get(rootFolder), myVisitor);
        } catch (IOException ex) {
        }
        return items;
    }

    @Override
    public Set<MediaItem> getItemsToRemove(Set<Set<MediaItem>> duplicates) {
        Set<MediaItem> outputSet = new HashSet<>();
        for (Set<MediaItem> s : duplicates) {

            MediaItem firstItem = s.iterator().next();
            outputSet.addAll(s);
            outputSet.remove(firstItem);
        }
        return outputSet;
    }

    @Override
    public boolean removeFiles(Set<MediaItem> listToRemove) {
        boolean retVal = true;
        for (MediaItem m : listToRemove) {
            try {
                Files.delete(Paths.get(m.getAbsolutePath()));
            } catch (Exception e) {
                e.printStackTrace();
                retVal = false;
            }
        }
        return retVal;
    }

    @Override
    public List<Path> filterFileList(String path, String filter) {
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

    @Override
    public void copyFiles(String srcFolder, String targetBasePath) {
        Path sourceFolder = getFolder(srcFolder);
        Path targetFolder = getFolder(targetBasePath);
        Consumer<Path> selectedConsumer = copyFilesBody(sourceFolder, targetFolder);
        try {
            Files.walk(sourceFolder).forEach(selectedConsumer);
        } catch (IOException ioex) {
            CustomLogging.logIt(ioex);

        }
    }

    @Override
    public void copyFiles(String srcFolder, String targetBasePath, String filter) {
        List<Path> filteredPaths = filterFileList(srcFolder, filter);
        Path sourceFolder = getFolder(srcFolder);
        Path targetFolder = getFolder(targetBasePath);
        Consumer<Path> selectedConsumer = copyFilesBody(sourceFolder, targetFolder);

        filteredPaths.forEach(selectedConsumer);

    }

    @Override
    public void copyMediaFiles(String srcFolder, String targetBasePath, DuplicateFinder df) {

        Set<MediaItem> foundMediaItems = getAllID3MediaItems(srcFolder, null);
        Set<MediaItem> copiedItems = new HashSet<>();
        Set<MediaItem> foundDuplicates = new HashSet<>();

        Path sourceFolder = getFolder(srcFolder);
        Path targetFolder = getFolder(targetBasePath);

        Consumer<Path> selectedConsumer = copyFilesBody(sourceFolder, targetFolder);
        Predicate<MediaItem> processDuplicates = getProcessDuplicatesBody(df, copiedItems, foundDuplicates);

        if (df != null) {
            foundMediaItems.stream()
                    .filter(processDuplicates)
                    .map(MediaItem::getAbsolutePath).map(Paths::get)
                    .forEach(selectedConsumer);

        } else {
            foundMediaItems.stream()
                    .map(MediaItem::getAbsolutePath).map(Paths::get)
                    .forEach(selectedConsumer);
        }

        if (foundDuplicates.size() > 0) {
            System.out.println("The following duplicates were excluded from the copy process:");
            for (MediaItem m : foundDuplicates) {
                System.out.println(m.getAbsolutePath());
            }
        }

    }

    @Override
    public Set<MediaItem> getAllID3MediaItems(String rootFolder, DuplicateFinder df) {
        Path p = Paths.get(rootFolder);
        if (!p.isAbsolute()) {
            Path currentWorkingFolder = Paths.get("").toAbsolutePath();
            rootFolder = Paths.get(currentWorkingFolder.toString(), rootFolder).toString();
        }
        Set<MediaItem> noDuplicates = new HashSet<>();
        Set<MediaItem> foundDuplicates = new HashSet<>();
        try {
            Stream<MediaItem> s = Files.walk(Paths.get(rootFolder)).filter(file -> file.toString().endsWith("mp3"))
                    .map(path -> new MediaItem()
                    .setAbsolutePath(path.toString()))
                    .map(m -> {
                        try {
                            MediaInfoSourceFromID3.getInstance().addMediaInfo(m);
                        } catch (Exception e) {
                            CustomLogging.logIt(e);
                        }
                        return m;
                    });

            if (df != null) {

                s.filter(getProcessDuplicatesBody(df, noDuplicates, foundDuplicates));
            } else {
                s.forEach(m -> noDuplicates.add(m));
            }
        } catch (Exception ex) {
            CustomLogging.logIt(ex);
        }
        return noDuplicates;

    }

    @Override
    public void writeLineToFile(String fileName, String path, String line) {
        Path candidateFolder = FileService.getFolder(path);
        Path candidateFile = Paths.get(candidateFolder.toString(), fileName);
        Path realFile = FileService.getFile(candidateFile.toString());
        try ( BufferedWriter bw = Files.newBufferedWriter(realFile, StandardOpenOption.APPEND)) {
            bw.write(line);
        } catch (Exception ex) {
            CustomLogging.logIt(ex);
        }
    }

    @Override
    public void refileAndCopyOne(String basePath, MediaItem m) {
        //should filter for not allowed characters later on...
        String artist = m.getArtist();
        String album = m.getAlbum();
        Path src = Paths.get(m.getAbsolutePath());
        String fileName = Paths.get(m.getAbsolutePath()).getFileName().toString();
        Path dst = Paths.get(basePath, artist, album), filename;
        Path newDirectories = Paths.get(basePath, artist, album);
        try {
            Files.createDirectories(newDirectories);
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            CustomLogging.logIt(ex);
        }
    }

    public void saveM3UFile(Set<MediaItem> filteredItems, String fileNameToSave, String destinationFolder) {
        String header = M3U.getHeader();

        FileServiceImpl.getInstance().writeLineToFile(fileNameToSave, destinationFolder, header);

        for (MediaItem mi : filteredItems) {
            String line = M3U.getMediaItemInf(mi);
            FileServiceImpl.getInstance().writeLineToFile(fileNameToSave, destinationFolder, line);
        }

    }

}
