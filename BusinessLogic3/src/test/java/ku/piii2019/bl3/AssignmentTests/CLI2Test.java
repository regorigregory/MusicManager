/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.AssignmentTests;

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
import ku.piii2019.bl3.MediaFileService;
import ku.piii2019.bl3.MediaItem;
import ku.piii2019.bl3.SearchService.FilterType;
import ku.piii2019.bl3.SimpleSearch;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author k1721863
 */
public class CLI2Test {

    public static final String TO_BE_COPIED_PATH = Paths.get("../CLI2_TEST").normalize().toAbsolutePath().toString();
    public static final boolean DELETE_TEMP_FOLDERS = true;
    public static final MediaFileService FS_INSTANCE = MediaFileService.getInstance();
    public static Set<MediaItem> ALL_MEDIA_ITEMS;
    public static LinkedHashMap<String, LinkedList<MediaItem>> BY_ARTIST;
    public static LinkedHashMap<String, LinkedList<MediaItem>> BY_GENRE;

    @BeforeClass
    public static void setUpClass() {
        copyOriginalTestFolders(TO_BE_COPIED_PATH);

        ALL_MEDIA_ITEMS = MediaFileService.getInstance().getAllID3MediaItems(TO_BE_COPIED_PATH, null)[0];
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


    @Test

    public void testFilterByArtist() {
        List<String> artists = new LinkedList(BY_ARTIST.keySet());
        int max = artists.size();

        Random rand = new Random();
        int randomIndex = rand.nextInt(max);
        String artistName = artists.get(randomIndex);
        List<MediaItem> artistsMedia = BY_ARTIST.get(artists.get(randomIndex));

        Set<MediaItem> foundItems = new SimpleSearch().filterBy(new String[]{artistName}, ALL_MEDIA_ITEMS, FilterType.ARTIST);
        assertEquals(artistsMedia.size(), foundItems.size());
        for (MediaItem m : foundItems) {
            assertTrue(artistsMedia.contains(m));
        }

    }

    

    @Test

    public void testFilterByGenre() {
        List<String> genres = new LinkedList(BY_GENRE.keySet());
        int max = genres.size();

        Random rand = new Random();
        int randomIndex = rand.nextInt(max);

        String genreName = genres.get(randomIndex);
        List<MediaItem> genreMedia = BY_GENRE.get(genres.get(randomIndex));

        Set<MediaItem> foundItems = new SimpleSearch().filterBy(new String[]{genreName}, ALL_MEDIA_ITEMS, FilterType.GENRE);

        assertEquals(genreMedia.size(), foundItems.size());

        for (MediaItem m : foundItems) {
            assertTrue(genreMedia.contains(m));
        }
    }


    @Test
    public void testFilterByMultipleArtists() {
        List<String> artists = new LinkedList(BY_ARTIST.keySet());
        int max = artists.size();

        Random rand = new Random();
        int randomIndex = rand.nextInt(max);
        String artistName1 = artists.get(randomIndex);
        List<MediaItem> artistsMedia1 = BY_ARTIST.get(artists.get(randomIndex));

        randomIndex = rand.nextInt(max);
        String artistName2 = artists.get(randomIndex);
        List<MediaItem> artistsMedia2 = BY_ARTIST.get(artists.get(randomIndex));

        Set<MediaItem> foundItems = new SimpleSearch().filterBy(new String[]{artistName1, artistName2}, ALL_MEDIA_ITEMS, FilterType.ARTIST);
        assertEquals(artistsMedia1.size() + artistsMedia2.size(), foundItems.size());
        for (MediaItem m : foundItems) {
            assertTrue((artistsMedia1.contains(m) || artistsMedia2.contains(m)));
        }

    }

    @Test

    public void testFilterByMultipleGenres() {
        List<String> genres = new LinkedList(BY_GENRE.keySet());
        int max = genres.size();

        Random rand = new Random();
        int randomIndex = rand.nextInt(max);
        String genreName1 = genres.get(randomIndex);
        List<MediaItem> genreMedia1 = BY_GENRE.get(genres.get(randomIndex));

        randomIndex = rand.nextInt(max);
        String genreName2 = genres.get(randomIndex);
        List<MediaItem> genreMedia2 = BY_GENRE.get(genres.get(randomIndex));

        Set<MediaItem> foundItems = new SimpleSearch().filterBy(new String[]{genreName1, genreName2}, ALL_MEDIA_ITEMS, FilterType.GENRE);
        assertEquals(genreMedia1.size() + genreMedia2.size(), foundItems.size());
        for (MediaItem m : foundItems) {
            assertTrue((genreMedia1.contains(m) || genreMedia2.contains(m)));
        }
    }

}
