/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import static ku.piii2019.bl3.FileService.copyFilesBody;
import static ku.piii2019.bl3.FileService.getFolder;

/**
 *
 * @author regor
 */
public class GenericFileService implements FileService{
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
}
