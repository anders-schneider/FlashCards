/**
 * 
 */
package flashCards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author David Matuszek
 */
public class ItemTest {
	Item item1;
	Item item2;
	Item item3;
	Item item4;
	String stimulus1 = "Pennsylvania";
	String stimulus2 = "Ohio";
	String stimulus3 = "Illinois";
	String stimulus4 = "Michigan";
	String response1 = "Harrisburg";
	String response2 = "Columbus";
	String response3 = "Springfield";
	String response4 = "Lansing";
	
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    	item1 = new Item(stimulus1, response1);
    	item2 = new Item(stimulus2, response2);
    	item3 = new Item(stimulus3, response3);
    	item4 = new Item(stimulus4, response4);
    }

    /**
     * Test method for {@link flashCards.Item#Item(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testItem() {
        Item duplicateItem4 = new Item(stimulus4, response4);
        assertTrue(item4.equals(duplicateItem4));
        assertEquals(0, duplicateItem4.timesCorrect);
        assertEquals(false, duplicateItem4.isLearned);
    }

    /**
     * Test method for {@link flashCards.Item#setStimulus(java.lang.String)} and
     * {@link flashCards.Item#getStimulus()} (combined).
     */
    @Test
    public final void testSetAndGetStimulus() {
        item1.setStimulus(stimulus3);
        assertEquals(stimulus3, item1.getStimulus());
    }

    /**
     * Test method for {@link flashCards.Item#setResponse(java.lang.String)} and
     * {@link flashCards.Item#getResponse()} (combined).
     */
    @Test
    public final void testSetAndGetResponse() {
        item1.setResponse(response3);
        assertEquals(response3, item1.getResponse());
        item2.setResponse(response1);
        assertEquals(response1, item2.getResponse());
    }

    /**
     * Test method for {@link flashCards.Item#setTimesCorrect(int)} and
     * {@link flashCards.Item#getTimesCorrect()} (combined).
     */
    @Test
    public final void testSetAndGetTimesCorrect() {
        item1.setTimesCorrect(4);
        assertEquals(4, item1.timesCorrect);
        assertEquals(0, item2.getTimesCorrect());
        assertEquals(4, item1.getTimesCorrect());
    }
    
    @Test
    public void testEqualsObject() {
		Item duplicateItem1 = new Item(stimulus1, response1);
    	assertFalse(item1.equals(stimulus1));		
		assertFalse(item1.equals(item2));	
		assertTrue(item1.equals(duplicateItem1));	
    }		

    @Test
    public void testToString() {
    	assertTrue("Pennsylvania, Harrisburg".equals(item1.toString()));
    	assertTrue("Michigan, Lansing".equals(item4.toString()));
    }
}
