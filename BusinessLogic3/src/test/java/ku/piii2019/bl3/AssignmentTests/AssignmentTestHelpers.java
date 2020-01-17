/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.AssignmentTests;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import ku.piii2019.bl3.FileService;
import ku.piii2019.bl3.MediaFileService;
import ku.piii2019.bl3.MediaItem;
import ku.piii2019.bl3.Worksheet8TestHelper;

/**
 *
 * @author k1721863
 */
public class AssignmentTestHelpers {

    public static final PrintStream SYSTEM_OUTPUT = System.out;
    public static final OutputStream SELECTED_OUTPUT = new ByteArrayOutputStream();

    public static void setCustomConsoleOutput() {
        try {
            SELECTED_OUTPUT.flush();
        } catch (Exception ex) {
            System.out.println("Flushing the outputstream failed");
            ex.printStackTrace();
        }
        System.setOut(new PrintStream(SELECTED_OUTPUT));
    }

    public static void restoreSystemOutput() {
        System.setOut(SYSTEM_OUTPUT);
    }

    public static void copyOriginalTestFolders(String newFolderName) {
        Path targetFolder = Paths.get(newFolderName);
        if (!Files.isDirectory(targetFolder)) {
            try {
                Files.createDirectories(targetFolder);
            } catch (Exception e) {
                System.out.println("Creating the selected target path was not possible");
                e.printStackTrace();
            }
        }

        try {
            Path srcFolder = Paths.get(Worksheet8TestHelper.TEST_SRC_FOLDER).toAbsolutePath();
            Consumer<Path> consumer = FileService.copyFilesBody(srcFolder, targetFolder);

            Files.walk(srcFolder).forEach(consumer);

        } catch (Exception e) {
            System.out.println("The initialisation of the new test folder was not possible");
            e.printStackTrace();
        }
    }

    public static void deleteTempTestFolders(String path) {
        Worksheet8TestHelper.deleteFolderWithFileVisitor(path);
    }

    public static boolean compareRelativePaths(Set<MediaItem> m1, Set<MediaItem> m2, String tempPath, String copyTempPath) {

        m1.stream().forEach(m -> m.relativizeMyself(Paths.get(tempPath)));
        m2.stream().forEach(m -> m.relativizeMyself(Paths.get(copyTempPath)));

        List<String> relPaths1 = m1.stream().map(m -> m.getRelativePath()).collect(Collectors.toList());
        List<String> relPaths2 = m2.stream().map(m -> m.getRelativePath()).collect(Collectors.toList());

        return relPaths1.stream().allMatch(s -> relPaths2.contains(s));
    }

    public static LinkedHashMap<String, LinkedHashMap<String, LinkedList<MediaItem>>>
            getMediaFilesIndexedByArtistAndAlbum(Set<MediaItem> allItems) {

        LinkedHashMap<String, LinkedHashMap<String, LinkedList<MediaItem>>> organisedList = new LinkedHashMap<>();

        for (MediaItem m : allItems) {

            String artist = m.getArtist().toLowerCase().trim();
            organisedList.putIfAbsent(artist, new LinkedHashMap<>());

            String album = m.getAlbum().toLowerCase().trim();
            organisedList.get(artist).putIfAbsent(album, new LinkedList<>());

            if (!organisedList.get(artist).get(album).contains(m)) {
                organisedList.get(artist).get(album).add(m);
            }
        }
        return organisedList;
    }

    public static LinkedHashMap<String, LinkedList<MediaItem>>
            getAllMediaByArtist(Set<MediaItem> allItems) {

        LinkedHashMap<String, LinkedList<MediaItem>> organisedList = new LinkedHashMap<>();
        for (MediaItem m : allItems) {

            String artist = m.getArtist().toLowerCase().trim();
            organisedList.putIfAbsent(artist, new LinkedList<>());

            if (!organisedList.get(artist).contains(m)) {
                organisedList.get(artist).add(m);
            }
        }
        return organisedList;
    }

    public static LinkedHashMap<String, LinkedList<MediaItem>>
            getAllMediaByGenre(Set<MediaItem> allItems) {

        LinkedHashMap<String, LinkedList<MediaItem>> organisedList = new LinkedHashMap<>();

        for (MediaItem m : allItems) {

            String genre = m.getGenre().toLowerCase().trim();
            organisedList.putIfAbsent(genre, new LinkedList<>());

            if (!organisedList.get(genre).contains(m)) {
                organisedList.get(genre).add(m);
            }
        }
        return organisedList;
    }

}
