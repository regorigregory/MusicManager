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
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import ku.piii2019.bl3.FileService;
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
        try{
        SELECTED_OUTPUT.flush();
        } catch(Exception ex){
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

        List<String> relPaths1 = m1.stream().map(m->m.getRelativePath()).collect(Collectors.toList());
        List<String> relPaths2 = m2.stream().map(m->m.getRelativePath()).collect(Collectors.toList());

        return relPaths1.stream().allMatch(s -> relPaths2.contains(s));
    }

}
