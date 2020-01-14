/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;


import ku.piii2019.bl3.CLI.DoCopy;


/**
 *
 * @author regor
 */
public class Main {

    public static void main(String... args) {
        //-s c://test -d c://test2 -ID3EX
        String srcDir = "C:\\gdrive\\NetBeansProjects\\music-manager-assignment\\test_folders\\original_filenames\\collection-A";
        String dstDir = "C:\\gdrive\\NetBeansProjects\\"
                + "music-manager-assignment\\test_folders\\"
                + "original_filenames\\collection-C\\test_15";
        String[] testArgs = new String[]{"-s", srcDir, "-d", dstDir, "-ID3EX"};
        

          DoCopy.getInstance().processArgs(testArgs);
//        DuplicateFinder df = new DuplicateFindFromID3();
//        FileService fs = new FileServiceImpl();
//        Set<MediaItem> mis = fs.getAllMediaItems(srcDir);
//        Set<MediaItem> copiedItems = new HashSet<>();
//        Set<MediaItem> foundDuplicates = new HashSet<>();
        
        
        
    }

}
