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
	StudyList studyList;
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
				studyList = new StudyList();
				studyList.load();
				mainWindow = new StudyGui();
				mainWindow.createMainWindow();
				startWindow.dispatchEvent(new WindowEvent(startWindow, WindowEvent.WINDOW_CLOSING));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
			}
		}
    }
    
    void createMainWindow(){
    	setSize(400, 400);
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
    	stateDisplay = new JLabel("Pennsylvania");
    	capitolTextField = new JTextField("Harrisburg");
    	progressDisplay = new JLabel("Progress: 2/3");
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
    }
}