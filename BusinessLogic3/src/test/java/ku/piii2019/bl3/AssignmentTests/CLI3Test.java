/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.AssignmentTests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import ku.piii2019.bl3.CLI.CreateM3U;

/**
 *
 * @author k1721863
 */
public class CLI3Test {

    public static final String TO_BE_COPIED_PATH = Paths.get("../CLI3_TEST").normalize().toAbsolutePath().toString();
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
    public void testExtractDurationFromM3U() {
        String sampleM3U = getSampleM3UText();
        int sampleM3ULength = getSampleM3ULength();
        int extractedLength = extractLengthFromM3U(sampleM3U);

        assertEquals(extractedLength, sampleM3ULength);
    }
    @Test
    public void testIfM3UFileIsCreated() {
        int testLength = 1/60*20;
        List<String> artistNames = new LinkedList(BY_ARTIST.keySet());
        int max = artistNames.size();

        Random rand = new Random();
        int randomIndex = rand.nextInt(max);
        String fileName = "by_artist.m3u";
        String selectedArtist = artistNames.get(randomIndex);
        String[] args = new String[]{"-s", TO_BE_COPIED_PATH.toString(), "-f", fileName, "-ml", "10"};
        CreateM3U.getInstance().processArgs(args);
        Path completeFilePath = Paths.get(TO_BE_COPIED_PATH.toString(), fileName);
        assertTrue(Files.exists(completeFilePath));
        try {
            Files.deleteIfExists(completeFilePath);
        } catch (Exception ex) {
            System.out.println("Deleting the test file failed. File path:" + completeFilePath.toString());
        }
    }
    @Test
    public void checkIfOneArtist() {
        assertEquals(getNumberOfArtists(getSampleM3UTextWithOneArtist()), 1);
    }

  
    @Test
    public void testm3UDurationByArtist() {
        double testLengthsInMinutes = 1/60*20;
        List<String> artistNames = new LinkedList(BY_ARTIST.keySet());
        int max = artistNames.size();

        Random rand = new Random();
        int randomIndex = rand.nextInt(max);
        String fileName = "testm3UDurationByArtist_10.m3u";
        String selectedArtist = artistNames.get(randomIndex);
        String[] args = new String[]{"-s", TO_BE_COPIED_PATH.toString(), "-f", fileName, "-a", selectedArtist, "-ml", String.valueOf(testLengthsInMinutes)};

        CreateM3U.getInstance().processArgs(args);
        Path completeFilePath = Paths.get(TO_BE_COPIED_PATH.toString(), fileName);
        int extractedLength = 0;
        String contents = "";
        try {
            contents = Files.readString(completeFilePath);
            extractedLength = extractLengthFromM3U(contents);
        } catch (Exception e) {
            System.out.println("Reading the created file was not possible. The file's path:" + completeFilePath.toString());
        }
        assertEquals(getNumberOfArtists(contents), 1);
        
        assertTrue(testLengthsInMinutes > extractedLength/60);
        //assertEquals(checkIfOneArtist(contents), 1);
//        Path filePath = Paths.get(TO_BE_COPIED_PATH.toString(), fileName)
//        Integer[] array = new Integer[];

    }

    public static int extractLengthFromM3U(String sampleM3U) {
        int extractedLength = 0;
        String pattern = "(#EXTINF:)(\\d{1,})(,)";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(sampleM3U);
        while (matcher.find()) {
            String temp = matcher.group(2);
            int tempLength = Integer.parseInt(temp);
            extractedLength += tempLength;
        }
        return extractedLength;
    }

    public static int getNumberOfArtists(String sampleText) {
        int extractedLength = 0;
        String pattern = "(#EXTINF:)(\\d{1,})(,)(.*)( - )";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(sampleText);
        LinkedList extractedArtists = new LinkedList<>();
        while (matcher.find()) {
            String temp = matcher.group(4);
            extractedArtists.add(temp);
        }
        return new HashSet(extractedArtists).size();
    }

}
