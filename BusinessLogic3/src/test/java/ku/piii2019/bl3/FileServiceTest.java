/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.*;

/**
 *
 * @author James
 */
public class FileServiceTest {

    
    // I think we can test on original collection only
    FileStore fileStore = new FileStoreOriginalNames();

    
    public FileServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Path cwdPath = Paths.get("").toAbsolutePath();
        // remove temporary folder, if it is there
        try {
            File destFolder = new File(Paths.get(cwdPath.toString(), 
                                                Worksheet8TestHelper.TEST_SCRATCH_FOLDER, 
                                                fileStore.getRootFolder()
                                                ).toString());
//            Workshop5TestHelper.deleteFolderRecursively(destFolder);    
            Worksheet8TestHelper.deleteFolderWithFileVisitor(destFolder.toString());
        }
        // make the 
        catch (Exception e) {
            // no problem
            e.printStackTrace();
        }
        try {
            Path testScratchFolder = 
                Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER);
            Files.createDirectory(testScratchFolder);
        }
        catch (Exception e) {
            // no problem
            e.printStackTrace();
        }

        // copy permanent folder to temp location
        try {
//            Workshop5TestHelper.copyFolder(cwdPath, srcFolder, destFolder);
            Path srcPath = Paths.get(cwdPath.toString(), 
                                     Worksheet8TestHelper.TEST_SRC_FOLDER,
                                     fileStore.getRootFolder());
            Path dstPath = Paths.get(cwdPath.toString(), 
                                     Worksheet8TestHelper.TEST_SCRATCH_FOLDER );
            Path relDstFolder = Paths.get(fileStore.getRootFolder() );
            
            Worksheet8TestHelper.copyFolder(srcPath, dstPath, relDstFolder);
        }
        catch (Exception e) {
            // problem
            e.printStackTrace();
            fail("could not create test folder");
        }
        
    }
    
    @After
    public void tearDown() {
//        Workshop5TestHelper.deleteFolderRecursively
//                    (new File(Workshop5TestHelper.TEMP_INPUT_FOLDER_FOR_ORIGINAL_FILENAMES));
            Worksheet8TestHelper.
                    deleteFolderWithFileVisitor
                        (Worksheet8TestHelper.TEST_SCRATCH_FOLDER);
    }

    /**
     * Test of getAllMediaItems method, of class FileService.
     */
    
    @Test
    public void testGetAllMediaItems() {


        System.out.println("getAllMediaItems: begin by testing .equals and .hashcode...");
        assertTrue(testEquals());
        assertTrue(testHashCode());
        
        Path currentWorkingFolder = Paths.get("").toAbsolutePath();
        String rootFolder = Worksheet8TestHelper.TEST_SCRATCH_FOLDER;
        MediaFileService instance = MediaFileService.getInstance();
        String rootTestFolder = Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<MediaItem> expResult = fileStore.getAllMediaItems(rootTestFolder, null);
        Set<MediaItem> result = instance.getAllMediaItems(rootFolder);
        System.out.println("the expected result:");
        Worksheet8TestHelper.print2(expResult);
        System.out.println("the actual result:");
        Worksheet8TestHelper.print2(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuplicates method, of class FileService.
     */

    /**
     * Test of getListToRemove method, of class FileService.
     */
    @Test
    public void testGetListToRemove() {
                
        System.out.println("getListToRemove");
        assertTrue(testEquals());
        assertTrue(testHashCode());
        MediaFileService instance = MediaFileService.getInstance();
        String rootTestFolder = Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<Set<MediaItem>> duplicates = 
                fileStore.getAllDuplicates(rootTestFolder, null);

        Set<MediaItem> itemsToRemove = instance.getMediaItemsToRemove(duplicates);
        
        System.out.println("the duplicates to choose from:");
        Worksheet8TestHelper.print1(duplicates);
        System.out.println("the list selected to remove:");
        Worksheet8TestHelper.print2(itemsToRemove);
        
        for(Set<MediaItem> thisDuplicateSet : duplicates) {
            // for each duplicate set, find out how many elements in 'itemsToRemove'
            // are the duplicates (i.e. have the same filename)
            // the answer should be #num-items-in-set - 1 
            MediaItem firstDuplicateInSet = thisDuplicateSet.iterator().next();
            int numLikeThis = 
                    Worksheet8TestHelper.getNumLikeThis(firstDuplicateInSet, itemsToRemove);
            assertEquals( thisDuplicateSet.size()-1, numLikeThis);
        }
    }

    /**
     * Test of removeFiles method, of class FileService.
     */
    
    @Test
    public void testRemoveFiles() {
        System.out.println("removeFiles");
        assertTrue(testEquals());
        assertTrue(testHashCode());
        Set<MediaItem> listToRemove = null;
        String rootTestFolder = Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<MediaItem> allMediaItems = 
                        fileStore.getAllMediaItems(rootTestFolder, null);
        assertTrue(Worksheet8TestHelper.filesExist(allMediaItems));

        MediaFileService instance =  MediaFileService.getInstance();
        
        Set<MediaItem> allDuplicates = new HashSet<>();
        Set<Set<MediaItem>> duplicates = 
                        fileStore.getAllDuplicates(rootTestFolder, null);
        for(Set<MediaItem> m : duplicates)
            allDuplicates.addAll(m);
        instance.removeMediaFiles(allDuplicates);
        allMediaItems.removeAll(allDuplicates);
        assertTrue(Worksheet8TestHelper.filesExist(allMediaItems));
        assertTrue(Worksheet8TestHelper.filesDontExist(allDuplicates));
    }
    boolean testEquals()
    {
        MediaItem foo = new MediaItem().setAbsolutePath("foo");
        MediaItem bar = new MediaItem().setAbsolutePath("bar");
        
        if(foo.equals(bar)){
            System.out.println("testEquals: foo does not equal bar!");
            return false;
        }
        bar.setAbsolutePath("foo");
        if(foo.equals(bar)==false){
            System.out.println("testEquals: foo does now equal bar, so .equals() method should return true!");
            return false;
        }
        return true;
    }
    boolean testHashCode()
    {
        MediaItem foo = new MediaItem().setAbsolutePath("foo");
        MediaItem bar = new MediaItem().setAbsolutePath("bar");
        
        int fooHashCode = foo.hashCode();
        int barHashCode = bar.hashCode();
        System.out.println("testHashCode: foo and bar different, with hashCodes of " 
        + fooHashCode + " and " + barHashCode );
        
        if(fooHashCode==barHashCode){
            System.out.println("testHashCode: slightly surprising (but not actually incorrect) for these to have the same hashcode...");
        }
        bar.setAbsolutePath("foo");
        fooHashCode = foo.hashCode();
        barHashCode = bar.hashCode();
        System.out.println("testHashCode: foo and bar same, with hashCodes of " 
        + fooHashCode + " and " + barHashCode );
        if(fooHashCode!=barHashCode){
            System.out.println("testHashCode: foo and bar hashCodes must be the same...");
            return false;
        }
        return true;
    }
}
