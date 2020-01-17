/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.AssignmentTests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static ku.piii2019.bl3.AssignmentTests.AssignmentTestHelpers.*;
import ku.piii2019.bl3.DuplicateFindFromFilename;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.DuplicateFinder;
import ku.piii2019.bl3.MediaFileService;
import ku.piii2019.bl3.MediaItem;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author k1721863
 */
public class CLI1Test {

    public static final String TO_BE_COPIED_PATH = Paths.get("../CLI1_TEST").normalize().toAbsolutePath().toString();
    public static final String COPY_TARGET_PATH = TO_BE_COPIED_PATH + "_TEMP";
    public static final boolean DELETE_TEMP_FOLDERS = false;
    public static final MediaFileService FS_INSTANCE = MediaFileService.getInstance();

    @BeforeClass
    public static void setUpClass() {
        copyOriginalTestFolders(TO_BE_COPIED_PATH);
        try {
            Files.createDirectories(Paths.get(TO_BE_COPIED_PATH));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        if (DELETE_TEMP_FOLDERS) {
            deleteTempTestFolders(TO_BE_COPIED_PATH);
        }
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

        deleteTempTestFolders(COPY_TARGET_PATH);

    }

    
    @Test
    public void testCopyMediaFilesWithDuplicates() {
        Set<MediaItem> filesToCopy = FS_INSTANCE.getAllID3MediaItems(TO_BE_COPIED_PATH, null)[0];
        Set<MediaItem> copiedFiles = FS_INSTANCE.getAllID3MediaItems(COPY_TARGET_PATH, null)[0];
        assertEquals(copiedFiles.size(), 0);

        FS_INSTANCE.copyMediaFiles(TO_BE_COPIED_PATH, COPY_TARGET_PATH, null);
        copiedFiles = FS_INSTANCE.getAllMediaItems(COPY_TARGET_PATH);

        assertEquals(filesToCopy.size(), copiedFiles.size());
        assertTrue(compareRelativePaths(filesToCopy, copiedFiles, TO_BE_COPIED_PATH, COPY_TARGET_PATH));

    }

   
    @Test
    public void testCopyMediaFilesWithoutID3Duplicates() {
        DuplicateFinder df = new DuplicateFindFromID3();
        Set[] filesToCopy = FS_INSTANCE.getAllID3MediaItems(TO_BE_COPIED_PATH, df);
        Set[] copiedFiles = FS_INSTANCE.getAllID3MediaItems(COPY_TARGET_PATH, null);
        assertEquals(copiedFiles[0].size(), 0);
        assertEquals(copiedFiles[1].size(), 0);

        FS_INSTANCE.copyMediaFiles(TO_BE_COPIED_PATH, COPY_TARGET_PATH, df);

        //No filter to read in every single existing media file.
        copiedFiles[0] = FS_INSTANCE.getAllMediaItems(COPY_TARGET_PATH);

        Set[] copiedFilesDoubleCheck = FS_INSTANCE.getAllID3MediaItems(COPY_TARGET_PATH, null);

        assertEquals(filesToCopy[0].size(), copiedFiles[0].size());
        assertTrue(compareRelativePaths(filesToCopy[0], copiedFiles[0], TO_BE_COPIED_PATH, COPY_TARGET_PATH));

    }

   
    @Test
    public void testCopyMediaFilesWithoutFileNameDuplicates() {
        DuplicateFinder df = new DuplicateFindFromFilename();
        Set[] filesToCopy = FS_INSTANCE.getAllID3MediaItems(TO_BE_COPIED_PATH, df);
        Set[] copiedFiles = FS_INSTANCE.getAllID3MediaItems(COPY_TARGET_PATH, null);
        assertEquals("The target folders are not empty.", copiedFiles[0].size(), 0);
        assertEquals("The target folders are not empty.", copiedFiles[1].size(), 0);

        FS_INSTANCE.copyMediaFiles(TO_BE_COPIED_PATH, COPY_TARGET_PATH, df);

        //No filter to read in every single existing media file.
        copiedFiles[0] = FS_INSTANCE.getAllMediaItems(COPY_TARGET_PATH);

        Set[] copiedFilesDoubleCheck = FS_INSTANCE.getAllID3MediaItems(COPY_TARGET_PATH, null);

        assertEquals(filesToCopy[0].size(), copiedFiles[0].size());
        assertTrue(compareRelativePaths(filesToCopy[0], copiedFiles[0], TO_BE_COPIED_PATH, COPY_TARGET_PATH));
    }
    
    
    //Please modify file paths according to your settings.
    @Ignore
    @Test
    public void testIfDuplicatesAreOutput() {
        String outputShouldBe = TO_BE_COPIED_PATH+"\\short_filenames\\collection-A\\file2.mp3\n"
                + TO_BE_COPIED_PATH+"\\short_filenames\\collection-B\\directory1\\item1.mp3\n"
                + TO_BE_COPIED_PATH+"\\original_filenames\\collection-B\\Orxata Sound System\\Richard Stallman - Guantanamero - clip.mp3\n"
                + TO_BE_COPIED_PATH+"\\original_filenames\\collection-A\\Orxata Sound System\\fuster meets guevara - clip.mp3\n"
                +  TO_BE_COPIED_PATH+"\\short_filenames\\collection-A\\file1.mp3\n"
                +  TO_BE_COPIED_PATH+"\\original_filenames\\collection-A\\Freak Fandango Orchestra\\Freak Fandango Orchestra - No means no - clip.mp3";
        String[] outputLinesShouldBe = outputShouldBe.split("\n");

        setCustomConsoleOutput();
        
        testCopyMediaFilesWithoutFileNameDuplicates();
        
        restoreSystemOutput();
        
        String output = SELECTED_OUTPUT.toString();
        String pattern = "The following duplicates were excluded from the copy process:";
        int index = output.indexOf(pattern) + pattern.length() + 2;
        output = output.substring(index);
        String[] actualLines = output.split("\r\n");
        assertEquals(outputLinesShouldBe.length, actualLines.length);
        for(int i = 0; i<actualLines.length; i++){
            assertEquals(Paths.get(actualLines[i]).normalize(), Paths.get(outputLinesShouldBe[i]).normalize());
            
        }
 
    }

}
