/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author James
 */
@RunWith(Parameterized.class)
public class DuplicateFinderTest {
    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        
        Collection<Object[]> listOfInstances = new ArrayList<>();
        
            
        // for Question 1:
        listOfInstances.add(new Object[]{new DuplicateFindFromFilename(), 
                                         new FileStoreOriginalNames(), 
                                         null, 
                                         rightAnswersForFilename});
        // for Question 2: 
        listOfInstances.add(new Object[]{new DuplicateFindFromID3(),
                                         new FileStoreShortNames(),
                                         new MediaInfoSourceFromID3(), 
                                         rightAnswersForID3});
        
        return listOfInstances;
    }
    public DuplicateFinderTest(DuplicateFinder testThisOneNext, 
                               FileStore fs, 
                               MediaInfoSource mis, 
                               List<Boolean> rightAnswers) {
       instance =  testThisOneNext;
       fileStore = fs;
       mediaInfoSource = mis;
       rightAnswersForTheseTwoAreDuplicates = rightAnswers;
    }
    
    DuplicateFinder instance = null;
    FileStore fileStore = null;
    MediaInfoSource mediaInfoSource = null;
    List<Boolean> rightAnswersForTheseTwoAreDuplicates = null;
    static List<Boolean> rightAnswersForFilename = Arrays.asList(true, true, false);
    static List<Boolean> rightAnswersForID3 = Arrays.asList(false, false, true);
    
    List<MediaItem> item1list = Arrays.asList(new MediaItem()
                                                .setAbsolutePath("c:" + File.separator + "file.mp3"),
                                              new MediaItem()
                                                .setAbsolutePath("c:" + File.separator + "file.mp3")
                                                .setTitle("title")
                                                .setArtist("artist"),
                                              new MediaItem()
                                                .setTitle("title")
                                                .setArtist("artist")
                                                .setAlbum("album") 
                                                .setAbsolutePath("c:" + File.separator + "file.mp3")
                                                        );
    List<MediaItem> item2list = Arrays.asList(new MediaItem()
                                                .setAbsolutePath("c:" + File.separator + "file.mp3"),
                                              new MediaItem()
                                                .setAbsolutePath("c:" + File.separator + "sameFilename" + File.separator + "file.mp3")
                                                .setTitle("title")
                                                .setArtist("artist"),
                                              new MediaItem()
                                                .setTitle("title")
                                                .setArtist("artist")
                                                .setAlbum("album") 
                                                .setAbsolutePath("c:" + File.separator + "differentFilename.mp3")
                                                        );
    

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }


    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
        Worksheet8TestHelper.
                deleteFolderWithFileVisitor(Worksheet8TestHelper.TEST_SCRATCH_FOLDER);
    }

    /**
     * Test of getAllDuplicates method, of class DuplicateFindFromFilename.
     */
    @Test
    public void testGetAllDuplicates() {
        System.out.println("getAllDuplicates");
        
        initializeTestFolder();
        
        String rootTestFolder = Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<MediaItem> allMediaItems = fileStore.getAllMediaItems(rootTestFolder, mediaInfoSource);
        Set<Set<MediaItem>> expResult = fileStore.getAllDuplicates(rootTestFolder, mediaInfoSource);
        Set<Set<MediaItem>> result = instance.getAllDuplicates(allMediaItems);
        
        System.out.println("the expected result:");
        Worksheet8TestHelper.print1(expResult);
        System.out.println("the actual result:");
        Worksheet8TestHelper.print1(result);

        assertEquals(expResult, result);
    }
    @Test
    public void testGetSingleDuplicateSet() {
        System.out.println("getSingleDuplicateSet");
        
        initializeTestFolder();
        
        String rootTestFolder = Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<MediaItem> allMediaItems = fileStore.getAllMediaItems(rootTestFolder, mediaInfoSource);
        Set<Set<MediaItem>> expectedOutputSets = fileStore.getAllDuplicates(rootTestFolder, mediaInfoSource);
        
        for(Set<MediaItem> expectedOutput : expectedOutputSets) {
            MediaItem testInputItem = expectedOutput.iterator().next();
            Set<MediaItem> actualOutput = instance.getDuplicates(allMediaItems, testInputItem);    
            assertEquals(expectedOutput,  actualOutput);
        }
    }
    @Test
    public void testTheseTwoAreDuplicates() {
        System.out.println("getSingleDuplicateSet");
        
        int n = rightAnswersForTheseTwoAreDuplicates.size();
        for(int i = 0;i<n;i++)
        {
            boolean actualReturnValue = instance.areDuplicates(item1list.get(i),
                                                                       item2list.get(i));
            boolean expectedReturnValue = rightAnswersForTheseTwoAreDuplicates.get(i);
            
            String comment = "class is " + instance.getClass() + " and index is " + i;
            assertEquals(comment, expectedReturnValue, actualReturnValue);
        }
    }

    private void initializeTestFolder() {
        
        Path cwdPath = Paths.get("").toAbsolutePath();
        Path testSrcFolder = Paths.get(Worksheet8TestHelper.TEST_SRC_FOLDER);
        Path testScratchFolder = 
                Paths.get(Worksheet8TestHelper.TEST_SCRATCH_FOLDER);

        File srcFolder = new File(Worksheet8TestHelper.TEST_SRC_FOLDER);
        File destFolder = new File(Worksheet8TestHelper.TEST_SCRATCH_FOLDER);
        try {
//            Workshop5TestHelper.deleteFolderRecursively(destFolder);    
            Worksheet8TestHelper.deleteFolderWithFileVisitor(testScratchFolder.toString());
        } catch (Exception e) {
            // no problem
            e.printStackTrace();
        }
        try {
            Files.createDirectory(testScratchFolder);
//            Workshop5TestHelper.copyFolder(cwdPath, srcFolder, destFolder);
            Path srcPath = Paths.get(cwdPath.toString(), 
                                     Worksheet8TestHelper.TEST_SRC_FOLDER,
                                     fileStore.getRootFolder());
            Path dstPath = Paths.get(cwdPath.toString(), 
                                     Worksheet8TestHelper.TEST_SCRATCH_FOLDER );
            Path relDstFolder = Paths.get(fileStore.getRootFolder() );
            
            Worksheet8TestHelper.copyFolder(srcPath, dstPath, relDstFolder);
        } catch (Exception e) {
            // problem
            e.printStackTrace();
            fail("could not create test folder");
        }
        // remove temporary folder, if it is there
        // copy permanent folder to temp location

    }

 
    private void assertSameMedia(Set<MediaItem> expResult, Set<MediaItem> result) {
        for(MediaItem expItem : expResult)
        {
            System.out.println("We should have item " + expItem + "do we?");
            Set<MediaItem> shouldBeOne = instance.getDuplicates(result, expItem);
            if(shouldBeOne.size()!=1)
            {
                System.out.println("eek");
            }
            assertEquals(shouldBeOne.size(), 1);
        }
        for(MediaItem item : result)
        {
            
            System.out.println("We do have item " + item  + "is this ok?");
            Set<MediaItem> shouldBeOne = instance.getDuplicates(expResult, item);
            if(shouldBeOne.size()!=1)
            {
                System.out.println("eek2");
            }
            assertEquals(shouldBeOne.size(), 1);
        }
    }

 
}
