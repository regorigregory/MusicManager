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
public class ApproxSearchTest {
    
    public ApproxSearchTest() {
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
     * Test of find method, of class ApproxSearch.
     */
    @Test
    public void testFindDifferentCase() {
        System.out.println("find - different case:");
        String thisSearchTerm = "bill evans";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        ApproxSearch instance = new ApproxSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Test
    public void testFindInsertion() {
        System.out.println("find - different case and insertion:");
        String thisSearchTerm = "Billy Evans";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        ApproxSearch instance = new ApproxSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Test
    public void testFindDeletion() {
        System.out.println("find - deletion:");
        String thisSearchTerm = "Autum Leaves";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        ApproxSearch instance = new ApproxSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Test
    public void testFindSubstition() {
        System.out.println("find - different case and substitution:");
        String thisSearchTerm = "bill-evans";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        ApproxSearch instance = new ApproxSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    @Test
    public void testFindSubstitionAndDeletion() {
        System.out.println("find - different case and substitution and deletion:");
        String thisSearchTerm = "Autum Leafes";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        ApproxSearch instance = new ApproxSearch();
        Set<MediaItem> expResult = new HashSet<>();
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    
    @Ignore
    @Test
    public void testPartBapproxSubstring() {
        System.out.println("find - different case and substitution and deletion:");
        String thisSearchTerm = "Billy";
        Set<MediaItem> inHere = SearchTestHelper.getStandardSet();
        ApproxSearch instance = new ApproxSearch();
        Set<MediaItem> expResult = new HashSet<>();
        expResult.add(SearchTestHelper.billEvans1());
        expResult.add(SearchTestHelper.billEvans2());               
        Set<MediaItem> result = instance.find(thisSearchTerm, inHere);
        assertEquals(expResult, result);
    }
    
}
