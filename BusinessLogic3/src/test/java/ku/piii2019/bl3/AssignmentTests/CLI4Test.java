/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.AssignmentTests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static ku.piii2019.bl3.AssignmentTests.AssignmentTestHelpers.*;
import static ku.piii2019.bl3.AssignmentTests.CLI3Test.extractLengthFromM3U;
import static ku.piii2019.bl3.AssignmentTests.CLI3Test.getNumberOfArtists;
import ku.piii2019.bl3.MediaFileService;
import ku.piii2019.bl3.MediaItem;
import static org.junit.Assert.*;
import ku.piii2019.bl3.CLI.CreateM3U;

/**
 *
 * @author k1721863
 */
public class CLI4Test {

    public static final String TO_BE_COPIED_PATH = Paths.get("../CLI3_TEST").normalize().toAbsolutePath().toString();
    public static final boolean DELETE_TEMP_FOLDERS = true;
    public static final MediaFileService FS_INSTANCE = MediaFileService.getInstance();
    public static Set<MediaItem> ALL_MEDIA_ITEMS;
    public static LinkedHashMap<String, LinkedHashMap<String, LinkedList<MediaItem>>> ALL_MEDIA_ITEMS_ORGANISED;
    public static LinkedHashMap<String, LinkedList<MediaItem>> BY_ARTIST;
    public static LinkedHashMap<String, LinkedList<MediaItem>> BY_GENRE;

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




   

}
