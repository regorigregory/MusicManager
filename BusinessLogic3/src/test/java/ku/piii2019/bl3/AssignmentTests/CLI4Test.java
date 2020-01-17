/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.AssignmentTests;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
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
import org.junit.Ignore;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
/**
 *
 * @author k1721863
 */


@RunWith(Parameterized.class)
public class CLI4Test {

    public static final String TO_BE_COPIED_PATH = Paths.get("../CLI3_TEST").normalize().toAbsolutePath().toString();
    public static final boolean DELETE_TEMP_FOLDERS = true;
    public static final MediaFileService FS_INSTANCE = MediaFileService.getInstance();
    public static Set<MediaItem> ALL_MEDIA_ITEMS;
    public static LinkedHashMap<String, LinkedHashMap<String, LinkedList<MediaItem>>> ALL_MEDIA_ITEMS_ORGANISED;
    public static LinkedHashMap<String, LinkedList<MediaItem>> BY_ARTIST;
    public static LinkedHashMap<String, LinkedList<MediaItem>> BY_GENRE;
    public DuplicateFinder df;
    public String currentDfCommand;
    @Parameters
    public static Collection<Object[]> returnTestClasses()
    {
        return Arrays.asList(
                new Object[]{null, "-NOEX"},
                new Object[]{new DuplicateFindFromFilename(), "-FEX"},
                new Object[]{new DuplicateFindFromID3(), "-ID3EX"}
        );

    }
    
    private CLI4Test(Object df, Object cliArgument){
        this.df = (DuplicateFinder) df;
        this.currentDfCommand = (String) cliArgument;
    }

    
    
    @BeforeClass
    public static void setUpClass() {
        copyOriginalTestFolders(TO_BE_COPIED_PATH);

        ALL_MEDIA_ITEMS = MediaFileService.getInstance().getAllID3MediaItems(TO_BE_COPIED_PATH, null)[0];
        ALL_MEDIA_ITEMS_ORGANISED = getMediaFilesIndexedByArtistAndAlbum(ALL_MEDIA_ITEMS);
        BY_ARTIST = getAllMediaByArtist(ALL_MEDIA_ITEMS);
        BY_GENRE = getAllMediaByGenre(ALL_MEDIA_ITEMS);

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

    }

    @Ignore
    @Test
    public void checkIfDestinationFolderIsCreated() {
        throw new UnsupportedOperationException("Yet to come!");
    }

    @Ignore
    @Test
    public void checkDirectoryStructure() {
        throw new UnsupportedOperationException("Yet to come!");
    }

    @Ignore
    @Test
    public void checkFilteredDirectoryStructure() {
        throw new UnsupportedOperationException("Yet to come!");
    }

    @Ignore
    @Test
    public void checkFilteredArtists() {
        throw new UnsupportedOperationException("Yet to come!");
    }

    @Ignore
    @Test
    public void checkFilteredGenres() {
        throw new UnsupportedOperationException("Yet to come!");
    }
    
    @Ignore
    @Test
    public void checkFilesWithoutDuplicates() {
        throw new UnsupportedOperationException("Yet to come!");
    }

}
