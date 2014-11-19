/**
 * 
 */
package flashCards;

/**
 * @author 
 */
public class Item {

	String stimulus;
	String response;
	int timesCorrect;
	boolean isLearned;
	
    public Item(String stimulus, String response) {
        this.stimulus = stimulus;
        this.response = response;
        this.timesCorrect = 0;
        this.isLearned = false;
    }
    
    public String getStimulus() {
        return stimulus;
    }
    
    public void setStimulus(String stimulus) {
        this.stimulus = stimulus;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public int getTimesCorrect() {
        return timesCorrect;
    }
    
    public void setTimesCorrect(int times) {
        timesCorrect = times;
        if (timesCorrect >= 4) {
        	isLearned = true;
        } else {
        	isLearned = false;
        }
    }
    
    @Override
    public String toString() {
    	return stimulus + ", " + response;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof Item)) {
    		return false;
    	}
    	Item otherItem = (Item) o;
    	return this.toString().equals(otherItem.toString());
    }
}