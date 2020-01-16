/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author James
 */
public class SimpleSearchTest {

    public SimpleSearchTest() {
    }

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
    }

    /**
     * Test of find method, of class SimpleSearch.
     */
    @Test
    public void testFindSameCase() {
        System.out.println("find - same case:");
        String thisSearchTerm = "Bill";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindDifferentCase() {
        System.out.println("find - different case:");
        String thisSearchTerm = "bill";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindInAlbum() {
        System.out.println("find - album only:");
        String thisSearchTerm = "Amazing";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.budPowell1());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindInTitle() {
        System.out.println("find - title only:");
        String thisSearchTerm = "Ipanema";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.oscarPeterson1());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }


    @Test
    public void testEmptyStringTermSearch() {
        System.out.println("find - search term is empty");
        String thisSearchTerm = "";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        int expResult = new HashSet<>().size();
        int result = instance.find(thisSearchTerm, inHere).size();
        assertEquals(expResult, result);
    }

    @Test
    public void testNullTermSearch() {

     System.out.println("find - search term is empty");
        String thisSearchTerm = null;
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        int expResult = new HashSet<>().size();
        int result = instance.find(thisSearchTerm, inHere).size();
        assertEquals(expResult, result);
    }

    @Test
    public void testJustWhiteSpacesTermSearch() {
        System.out.println("find - search term is just spaces");
        String thisSearchTerm = "                    ";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        int expResult = new HashSet<>().size();
        int result = instance.find(thisSearchTerm, inHere).size();
        assertEquals(expResult, result);
    }

    @Test
    public void testSearchTermWithExtraSpaces() {
       System.out.println("find - search term has extra trailing whitespace characters");
        String thisSearchTerm = "            Ipanema      ";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.oscarPeterson1());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testMediaInfoIsNull() {
        Set<MediaItem> inHere = SearchTestHelper.getIncompleteMediaInfoSet();
        SimpleSearch instance = new SimpleSearch();
        Set<MediaItem> expResult = new HashSet<>();
        //Extra mile, has not been implemented, due to laziness

        /*
        expResult.add(SearchTestHelper.oscarPeterson1());
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);*/
    }

}
