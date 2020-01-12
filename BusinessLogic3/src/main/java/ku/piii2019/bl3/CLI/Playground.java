/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.FileService;
import ku.piii2019.bl3.FileServiceImpl;
import ku.piii2019.bl3.MediaItem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class Playground {

    public static void main(String... args) {
        FileServiceImpl fsl = new FileServiceImpl();
        String stringPath = "C:\\original_filenames";
        String stringPath1 = "C:\\\\gdrive\\\\NetBeansProjects\\\\music-manager-assignment\\\\test_folders\\\\original_filenames\\\\collection-A";

        fsl.getAllMediaItems(stringPath).stream().map(m ->m.getAbsolutePath()).forEach(System.out::println);
        String targetPath = "C:\\gdrive\\NetBeansProjects\\"
                + "music-manager-assignment\\test_folders\\original_filenames\\"
                + "collection-c\\test_8";
        Path p1 = Paths.get(stringPath);

        Path p2 = Paths.get(stringPath1);
        
        Path rel = p1.relativize(p1);
        System.out.println(rel.toString());
        //List<Path> itemsToCopy = fsl.filterFileList(stringPath, "mp3");
       
 
         fsl.copyMediaFiles(stringPath1, targetPath, new DuplicateFindFromID3());
    }

}
