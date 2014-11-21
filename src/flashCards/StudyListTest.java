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
public class StudyListTest {

	String stimulus1 = "Pennsylvania";
	String response1 = "Harrisburg";
	String stimulus2 = "Ohio";
	String response2 = "Columbus";
	String stimulus3 = "Illinois";
	String response3 = "Springfield";
	String stimulus4 = "Michigan";
	String response4 = "Lansing";

	Item item1 = new Item(stimulus1, response1);
	Item item2 = new Item(stimulus2, response2);
	Item item3 = new Item(stimulus3, response3);
	Item item4 = new Item(stimulus4, response4);
	
	StudyList studyList1;
	StudyList studyList2;
	StudyList studyList3;
	
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    	studyList1 = new StudyList();
    	studyList1.add(item1);
    	studyList1.add(item2);
    	studyList1.add(item3);
    	studyList1.add(item4);
    	
    	studyList2 = new StudyList();
    	studyList2.add(item1);
    	studyList2.add(item2);
    	studyList2.add(item3);
    	
    	studyList3 = new StudyList();
    	studyList3.add(item1);
    	studyList3.add(item2);
    	studyList3.add(item3);
    }

    /**
     * Test method for {@link flashCards.StudyList#StudyList()}.
     */
    @Test
    public final void testStudyList() {
    	assertTrue(studyList2.equals(studyList2));
    	assertFalse(studyList1.equals(studyList2));
    }

    /**
     * Test method for {@link flashCards.StudyList#add(flashCards.Item)}.
     */
    @Test
    public final void testAdd() {
    	assertFalse(studyList1.equals(studyList2));
    	assertTrue(studyList2.equals(studyList3));
    	studyList2.add(item4);
    	assertTrue(studyList1.equals(studyList2));
    	assertFalse(studyList2.equals(studyList3));
    }

    /**
     * Test method for {@link flashCards.StudyList#find(java.lang.String)}.
     */
    @Test
    public final void testFind() {
    	assertTrue(studyList1.find("Pennsylvania").equals(item1));
    	assertTrue(studyList1.find("Lansing").equals(item4));
    	try{
    		studyList1.find("Chicago");
    	} catch (IllegalArgumentException e) {
    		assertTrue(e.getMessage().equals("Nothing found."));
    	}
    }

    /**
     * Test method for {@link flashCards.StudyList#delete(flashCards.Item)}.
     */
    @Test
    public final void testDelete() {
    	assertFalse(studyList1.equals(studyList2));
    	studyList1.delete(item4);
    	assertTrue(studyList1.equals(studyList2));
    }

    /**
     * Test method for {@link flashCards.StudyList#modify(flashCards.Item, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testModify() {
    	assertTrue(studyList1.itemArrayList.get(2).getResponse().equals("Springfield"));
    	studyList1.modify(item1, "Illinois", "Chicago");
    	assertTrue(studyList1.itemArrayList.get(3).getResponse().equals("Chicago"));
    }

    /**
     * Test method for {@link flashCards.StudyList#modify(flashCards.Item, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testCreateStringArray() {
    	String[] stringArray = {"Pennsylvania, Harrisburg", "Ohio, Columbus", "Illinois, Springfield"};
    	assertTrue(stringArray[0].equals(studyList2.createStringArray()[0]));
    	assertTrue(stringArray[1].equals(studyList2.createStringArray()[1]));
    	assertTrue(stringArray[2].equals(studyList2.createStringArray()[2]));
    }
    
}
