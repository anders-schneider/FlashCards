/**
 * 
 */
package flashCards;

import java.io.IOException;
import java.util.ArrayList;

import simpleIO.SimpleIO;

/**
 * @author David Matuszek
 */
public class StudyList {

	ArrayList<Item> itemArrayList;
	
    public StudyList() {
        itemArrayList = new ArrayList();
    }
    
    public void add(Item item) {
        itemArrayList.add(item);
    }
    
    public Item find(String stimulusOrResponse) throws IllegalArgumentException {
    	for (int i = 0; i < itemArrayList.size(); i++) {
    		if (itemArrayList.get(i).getStimulus() == stimulusOrResponse) {
    			return itemArrayList.get(i);
    		} else if (itemArrayList.get(i).getResponse() == stimulusOrResponse) {
    			return itemArrayList.get(i);
    		}
    	}
        throw new IllegalArgumentException ("Nothing found.");
    }
    
    public void delete(Item item) {
    	itemArrayList.remove(item);
        
    }
    
    public void modify(Item item, String newStimulus, String newResponse) {
    	itemArrayList.remove(item);
    	Item newItem = new Item(newStimulus, newResponse);
    	itemArrayList.add(newItem);
        
    }
    
    public void load() throws IOException {
    	ArrayList<String> linesToStudy = SimpleIO.load();
//    	if (linesToStudy == null || linesToStudy.isEmpty()) {
//    		throw IllegalArgumentException("The file is empty.");
//    	}
    	for (int i = 0; i < linesToStudy.size(); i++) {
        	String itemString = linesToStudy.get(i);
        	String[] itemArray = itemString.split(" *\\|\\| *");
        	Item newItem = new Item(itemArray[0].trim(), itemArray[1].trim());
        	newItem.setTimesCorrect(Integer.parseInt(itemArray[2].trim()));
        	itemArrayList.add(newItem);
        }
        
    }
    
    public void save() throws IOException {
    	ArrayList<String> stringArrayToSave = null;
    	for (int i = 0; i <itemArrayList.size(); i++) {
    		Item item = itemArrayList.get(i);
    		String stringToSave = item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
    		stringArrayToSave.add(stringToSave);
    	}
        SimpleIO.save(stringArrayToSave);
    }
    
    public void saveAs() throws IOException {
    	ArrayList<String> stringArrayToSave = null;
    	for (int i = 0; i <itemArrayList.size(); i++) {
    		Item item = itemArrayList.get(i);
    		String stringToSave = item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
    		stringArrayToSave.add(stringToSave);
    	}
        SimpleIO.saveAs(stringArrayToSave);
    }
    
    public String[] createStringArray() {
    	String[] result = null;
    	for (int i = 0; i < itemArrayList.size(); i++){
    		Item item = itemArrayList.get(i);
    		result[i] = item.getStimulus() + ", " + item.getResponse();
    	}
    	return result;
    }
    
    public void shuffleOrder() {
    	
    }
}
