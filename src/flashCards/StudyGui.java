/**
 * 
 */
package flashCards;

import javax.swing.*;

import simpleIO.SimpleIO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Anders Schneider
 */
public class StudyGui extends JFrame {

	static StudyGui startWindow; // Why does this have to be "static"?
	StudyGui mainWindow;
	JButton loadButton;
	ArrayList<String> linesToStudy;
	StudyList studyList = new StudyList();
	JPanel topPanel;
	JPanel bottomPanel;
	JLabel stateDisplay;
	JTextField capitolTextField;
	JLabel progressDisplay;
	JButton submitButton;
	JButton quitButton;
	int itemIndex;
	Item currentItem;
	int numItems;
	boolean allLearned = false;
	boolean currentItemCorrect;
	JLabel incorrectLabel;
	JLabel userResponseLabel;
	JLabel correctResponseLabel;
	
    public static void main(String[] args) {
    	startWindow = new StudyGui();
    	startWindow.createStartWindow();
    }
    
    void createStartWindow(){
    	setSize(200, 100);
    	setLayout(new BorderLayout());
    	loadButton = new JButton ("Load study list");
    	add(loadButton, BorderLayout.SOUTH);
    	loadButton.addActionListener(new LoadButtonListener());
    	add(new JLabel("Let's get started!"), BorderLayout.NORTH);
    	setVisible(true);
    }
    
    class LoadButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				mainWindow = new StudyGui();
				mainWindow.studyList = new StudyList();
				mainWindow.studyList.load();
				mainWindow.createMainWindow();
				startWindow.dispatchEvent(new WindowEvent(startWindow, WindowEvent.WINDOW_CLOSING));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
    }
    
    void createMainWindow(){
    	setSize(250, 250);
    	setVisible(true);
    	topPanel = new JPanel();
    	bottomPanel = new JPanel();
    	setLayout(new BorderLayout());
    	add(topPanel, BorderLayout.NORTH);
    	add(bottomPanel, BorderLayout.SOUTH);
    	int rows = 4;
    	int columns = 2;
    	int separation = 30;
    	topPanel.setLayout(new GridLayout(rows, columns, separation, separation));
    	bottomPanel.setLayout(new BorderLayout());
    	
    	stateDisplay = new JLabel();
    	capitolTextField = new JTextField();
    	progressDisplay = new JLabel();
       	submitButton = new JButton("Submit");
    	quitButton = new JButton("Quit");
    	
    	incorrectLabel = new JLabel();
    	userResponseLabel = new JLabel();
    	correctResponseLabel = new JLabel();
    	
    	submitButton.addActionListener(new SubmitButtonListener());
    	quitButton.addActionListener(new QuitButtonListener());
    	
    	topPanel.add(new JLabel("State:"));
    	topPanel.add(stateDisplay);
    	topPanel.add(new JLabel("Capitol:"));
    	topPanel.add(capitolTextField);
    	topPanel.add(progressDisplay);
    	topPanel.add(submitButton);
    	topPanel.add(new JPanel());
    	topPanel.add(quitButton);
    	
    	bottomPanel.add(incorrectLabel, BorderLayout.NORTH);
    	bottomPanel.add(userResponseLabel, BorderLayout.CENTER);
    	bottomPanel.add(correctResponseLabel, BorderLayout.SOUTH);
    	
    	setDefaultCloseOperation(mainWindow.EXIT_ON_CLOSE);
    	
    	itemIndex = 0;
    	study();
    }

	private void study() {
		
		// check if all items have been learned
		// if yes -> make a congratulations window pop up and close the mainWindow (ending the program)
		
		// scroll through indices until you find one that is not yet learned
		// if you get to the end of the indices, shuffle the list, set itemIndex to first unlearned item, and start again
		
		// update currentItem
		// update mainWindow to show currentItem 
		
		numItems = studyList.itemArrayList.size();
		boolean allLearned = true;
		for (int i = 0; i < numItems; i++) {
			if (!studyList.itemArrayList.get(i).isLearned){
				allLearned = false;
			}
		}
		if (allLearned){
			StudyGui congratulationsWindow = new StudyGui();
			congratulationsWindow.createCongratulations();
			mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
		}
		
		try{
			currentItem = studyList.itemArrayList.get(itemIndex);
		} catch (IndexOutOfBoundsException e) {
			studyList.shuffleOrder();
			itemIndex = 0;
			currentItem = studyList.itemArrayList.get(itemIndex);
		}
		while (currentItem.isLearned) {
			itemIndex++;
			try{
				currentItem = studyList.itemArrayList.get(itemIndex);
			} catch (IndexOutOfBoundsException e) {
				studyList.shuffleOrder();
				itemIndex = 0;
				currentItem = studyList.itemArrayList.get(itemIndex);
			}
		}
		
		stateDisplay.setText(currentItem.getStimulus());
		progressDisplay.setText("Progress: " + (itemIndex + 1) + "/" + numItems);
	}

	class SubmitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String userResponse = capitolTextField.getText();
			if (currentItem.getResponse().equals(userResponse)) {
				currentItem.setTimesCorrect(currentItem.getTimesCorrect() + 1);
				itemIndex++;
				clearBottomPanel();
				study();
			} else {
				currentItem.setTimesCorrect(0);
				showAnswer(userResponse);
				study();
			}
		}
	}
	
	class QuitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void createCongratulations() {
		// TODO Auto-generated method stub
		
	}

	public void clearBottomPanel() {
		incorrectLabel.setText("");
		userResponseLabel.setText("");
		correctResponseLabel.setText("");
		setSize(250, 250);
	}

	public void showAnswer(String userResponse) {
		setSize(250, 350);
		incorrectLabel.setText("INCORRECT");
		userResponseLabel.setText("You entered: " + userResponse);
		correctResponseLabel.setText("Correct response: " + currentItem.getResponse());
	}
}