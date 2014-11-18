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
	int numCorrect;
	
    public Item(String stimulus, String response) {
        this.stimulus = stimulus;
        this.response = response;
        this.numCorrect = 0;
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
        return numCorrect;
    }
    
    public void setTimesCorrect(int times) {
        numCorrect = times;
    }
}