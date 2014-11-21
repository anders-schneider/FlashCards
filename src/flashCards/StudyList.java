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
    		if (itemArrayList.get(i).getStimulus().equals(stimulusOrResponse)) {
    			return itemArrayList.get(i);
    		} else if (itemArrayList.get(i).getResponse().equals(stimulusOrResponse)) {
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
    	for (int i = 0; (i < linesToStudy.size()) && (i < 15); i++) {
        	String itemString = linesToStudy.get(i);
        	String[] itemArray = itemString.split(" *\\|\\| *");
        	Item newItem = new Item(itemArray[0].trim(), itemArray[1].trim());
        	if (itemArray.length == 3){
        		newItem.setTimesCorrect(Integer.parseInt(itemArray[2].trim()));
        	}
        	itemArrayList.add(newItem);
        }
            	
    }
    
    public void save() throws IOException {
    	ArrayList<String> stringArrayToSave = new ArrayList();
    	for (int i = 0; i <itemArrayList.size(); i++) {
    		Item item = itemArrayList.get(i);
    		String stringToSave = item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
    		stringArrayToSave.add(stringToSave);
    	}
        SimpleIO.save(stringArrayToSave);
    }
    
    public void saveAs() throws IOException {
    	ArrayList<String> stringArrayToSave = new ArrayList();
    	for (int i = 0; i <itemArrayList.size(); i++) {
    		Item item = itemArrayList.get(i);
    		String stringToSave = item.getStimulus() + " || " + item.getResponse() + " || " + item.getTimesCorrect();
    		stringArrayToSave.add(stringToSave);
    	}
        SimpleIO.saveAs(stringArrayToSave);
    }
    
    public String[] createStringArray() {
    	String [] result = new String[itemArrayList.size()];
    	for (int i = 0; i < itemArrayList.size(); i++){
    		Item item = itemArrayList.get(i);
    		result[i] = item.getStimulus() + ", " + item.getResponse();
    	}
    	return result;
    }
    
    public void shuffleOrder() {
    	double threshold = 0.5;
    	Item testItem = itemArrayList.get(1);
    	for (int i = 0; i < (itemArrayList.size() - 1); i++) {
    		double rand = Math.random();
    		if(rand < threshold) {
    			Item temp = itemArrayList.get(i);
    			itemArrayList.set(i, itemArrayList.get(i + 1));
        		itemArrayList.set(i + 1, temp);
    		}
    	}
    }
    
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof StudyList)){
    		return false;
    	}
    	StudyList otherList = (StudyList) o;
    	
    	if (itemArrayList.size() != otherList.itemArrayList.size()) {
    		return false;
    	}
    		
    	for (int i = 0; i < itemArrayList.size(); i++) {
    		if (! itemArrayList.contains(otherList.itemArrayList.get(i))) {
    			return false;
    		}
    	}
    	return true;
    }
}
