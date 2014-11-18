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
	JFileChooser fileChooser;
	ArrayList<String> linesToStudy;
	StudyList studyList = new StudyList();
	JPanel topPanel;
	JPanel bottomPanel;
	JLabel stateDisplay;
	JTextField capitolTextField;
	JLabel progressDisplay;
	JButton submitButton;
	JButton quitButton;
	
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
    
    class LoadButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
//				studyList = new StudyList();
				studyList.load();
				//System.out.println(studyList.itemArrayList.size());
				mainWindow = new StudyGui();
				//System.out.println(studyList.itemArrayList.size());
				mainWindow.createMainWindow();
				mainWindow.studyList = startWindow.studyList;
				//System.out.println(studyList.itemArrayList.size());
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
    	
    	topPanel.add(new JLabel("State:"));
    	topPanel.add(stateDisplay);
    	topPanel.add(new JLabel("Capitol:"));
    	topPanel.add(capitolTextField);
    	topPanel.add(progressDisplay);
    	topPanel.add(submitButton);
    	topPanel.add(new JPanel());
    	topPanel.add(quitButton);
    	
    	study();
    }

	private void study() {
		System.out.println(studyList.itemArrayList.size());
		int numItems = studyList.itemArrayList.size();
		boolean allLearned = true;
		for (int i = 0; i < numItems; i++) {
			if (!studyList.itemArrayList.get(i).isLearned){
				allLearned = false;
			}
		}
		if (allLearned){
			StudyGui congratulationsWindow = new StudyGui();
			return;
		}
		
		for (int i = 0; i < numItems; i++) {
			Item item = studyList.itemArrayList.get(i);
			stateDisplay.setText(item.getStimulus());
			progressDisplay.setText("Progress: " + i + "/" + numItems);
		}
	}
}