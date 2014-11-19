/**
 * Big Questions:
 * What is going on with save/saveAs? (ask Joo and Ted)
 * How to end the program appropriately?
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
	StudyGui congratulationsWindow;
	JButton loadButton;
	ArrayList<String> linesToStudy;
	StudyList studyList = new StudyList();
	JPanel topPanel;
	JPanel bottomPanel;
	JLabel stateDisplay;
	JTextField capitalTextField;
	JLabel progressDisplay;
	JButton submitButton;
	JButton quitButton;
	JButton saveButton;
	JButton saveAsButton;
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
    	setSize(250, 300);
    	setVisible(true);
    	topPanel = new JPanel();
    	bottomPanel = new JPanel();
    	setLayout(new BorderLayout());
    	add(topPanel, BorderLayout.NORTH);
    	add(bottomPanel, BorderLayout.SOUTH);
    	int rows = 5;
    	int columns = 2;
    	int separation = 30;
    	topPanel.setLayout(new GridLayout(rows, columns, separation, separation));
    	bottomPanel.setLayout(new BorderLayout());
    	
    	stateDisplay = new JLabel();
    	capitalTextField = new JTextField();
    	progressDisplay = new JLabel();
       	submitButton = new JButton("Submit");
    	quitButton = new JButton("Quit");
    	saveButton = new JButton("Save");
    	saveAsButton = new JButton("SaveAs");
    	
    	incorrectLabel = new JLabel();
    	userResponseLabel = new JLabel();
    	correctResponseLabel = new JLabel();
    	
    	submitButton.addActionListener(new SubmitButtonListener());
    	quitButton.addActionListener(new QuitButtonListener());
    	saveButton.addActionListener(new SaveButtonListener());
    	saveAsButton.addActionListener(new SaveAsButtonListener());
    	
    	topPanel.add(new JLabel("State:"));
    	topPanel.add(stateDisplay);
    	topPanel.add(new JLabel("Capital:"));
    	topPanel.add(capitalTextField);
    	topPanel.add(progressDisplay);
    	topPanel.add(submitButton);
    	topPanel.add(saveButton);
    	topPanel.add(saveAsButton);
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
			congratulationsWindow = new StudyGui();
			congratulationsWindow.createCongratulations();
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
		progressDisplay.setText("Item Number: " + (itemIndex + 1) + "/" + numItems);
	}

	class SubmitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String userResponse = capitalTextField.getText();
			capitalTextField.setText("");
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

	class SaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				mainWindow.studyList.save();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
		
	}

	class SaveAsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				mainWindow.studyList.saveAs();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
		
	}

	
	private void createCongratulations() {
		String[] options = new String [] {"Save", "Save as", "Quit"};
		int option = JOptionPane.showOptionDialog(congratulationsWindow, "Congratulations! You mastered all the capitals.\nWould you like to save before quitting?", "Option Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (option == 0) {
			try {
				mainWindow.studyList.save();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
		if (option == 1) {
			try {
				mainWindow.studyList.saveAs();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
		//mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
	}

	public void clearBottomPanel() {
		incorrectLabel.setText("");
		userResponseLabel.setText("");
		correctResponseLabel.setText("");
		setSize(250, 300);
	}

	public void showAnswer(String userResponse) {
		setSize(250, 350);
		incorrectLabel.setText("INCORRECT");
		userResponseLabel.setText("You entered: " + userResponse);
		correctResponseLabel.setText("Correct response: " + currentItem.getResponse());
	}
}