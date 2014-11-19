/**
 * Big Questions:
 * What is going on with save/saveAs? (ask TAs)
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

	JFrame startWindow;
	JFrame mainWindow;
	JFrame congratulationsWindow;
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
    	new StudyGui().createStartWindow();
    }
    
    void createStartWindow(){
    	startWindow = new JFrame();
    	startWindow.setSize(200, 100);
    	startWindow.setLayout(new BorderLayout());
    	loadButton = new JButton ("Load study list");
    	startWindow.add(loadButton, BorderLayout.SOUTH);
    	loadButton.addActionListener(new LoadButtonListener());
    	startWindow.add(new JLabel("Let's get started!"), BorderLayout.NORTH);
    	startWindow.setVisible(true);
    }
    
    class LoadButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				studyList = new StudyList();
				studyList.load();
				createMainWindow();
				startWindow.dispose();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
    }
    
    void createMainWindow(){
    	mainWindow = new JFrame();
    	mainWindow.setSize(250, 300);
    	mainWindow.setVisible(true);
    	topPanel = new JPanel();
    	bottomPanel = new JPanel();
    	mainWindow.setLayout(new BorderLayout());
    	mainWindow.add(topPanel, BorderLayout.NORTH);
    	mainWindow.add(bottomPanel, BorderLayout.SOUTH);
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
    	
    	mainWindow.setDefaultCloseOperation(mainWindow.EXIT_ON_CLOSE);
    	
    	itemIndex = 0;
    	study();
    }

	void study() {
		
		numItems = studyList.itemArrayList.size();
		boolean allLearned = true;
		for (int i = 0; i < numItems; i++) {
			if (!studyList.itemArrayList.get(i).isLearned){
				allLearned = false;
			}
		}
		if (allLearned){
			createCongratulations();
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
			mainWindow.dispose();
		}
	}

	class SaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				studyList.save();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}	
	}

	class SaveAsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				studyList.saveAs();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
	}

	
	private void createCongratulations() {
		congratulationsWindow = new JFrame();
		String[] options = new String [] {"Save", "Save as", "Quit"};
		int option = JOptionPane.showOptionDialog(congratulationsWindow, "Congratulations! You mastered all the capitals.\nWould you like to save before quitting?", "Option Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (option == 0) {
			try {
				studyList.save();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
		if (option == 1) {
			try {
				studyList.saveAs();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
		mainWindow.dispose();
	}

	public void clearBottomPanel() {
		incorrectLabel.setText("");
		userResponseLabel.setText("");
		correctResponseLabel.setText("");
		mainWindow.setSize(250, 300);
	}

	public void showAnswer(String userResponse) {
		mainWindow.setSize(250, 350);
		incorrectLabel.setText("INCORRECT");
		userResponseLabel.setText("You entered: " + userResponse);
		correctResponseLabel.setText("Correct response: " + currentItem.getResponse());
	}
}