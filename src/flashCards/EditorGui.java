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
 * @author Your Name Goes Here
 */
public class EditorGui extends JFrame {
	
	JFrame mainFrame;
	JFrame editWindow;
	JLabel keySearch;
	JTextField searchTextField;
	JLabel searchResultsLabel;
	JTextArea searchResultsTextArea;
	JLabel studyListLabel;
	JList<String> studyListDisplay;
	JScrollPane listScroll;
	JButton deleteButton;
	JButton addButton;
	JButton editButton;
	JButton saveButton;
	JButton saveAsButton;
	StudyList studyList;
	JLabel editStimulus;
	JLabel editResponse;
	JTextField editStimulusText;
	JTextField editResponseText;
	JButton saveChangesButton;
	Item itemToEdit;
	
    public static void main(String[] args) {
    	
    	new EditorGui().createWindow();
    	
    }

	private void createWindow() {
		studyList = new StudyList();
		
		try {
			studyList.load();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
		}
		mainFrame = new JFrame();
		mainFrame.setSize(400, 500);
		mainFrame.setVisible(true);
		int rows = 7;
		int columns = 2;
		int separation = 30;
		mainFrame.setLayout(new GridLayout(rows, columns, separation, separation));
		
		keySearch = new JLabel("Keyword Search:");
		searchTextField = new JTextField();
		searchResultsLabel = new JLabel("Results:");
		searchResultsTextArea = new JTextArea();
		studyListLabel = new JLabel("Study List:");
		String[] jListString = {"A", "B", "C", "D"};
		studyListDisplay = new JList<String>(studyList.createStringArray());
		studyListDisplay.setVisibleRowCount(10);
		studyListDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScroll = new JScrollPane(studyListDisplay);
		deleteButton = new JButton("Delete");
		//deleteButton.addActionListener(new DeleteButtonListener());
		addButton = new JButton("Add");
		//addButton.addActionListener(new AddButtonListener());
		editButton = new JButton("Edit");
		editButton.addActionListener(new EditButtonListener());
		saveButton = new JButton("Save");
		//saveButton.addActionListener(new SaveButtonListener());
		saveAsButton = new JButton("Save as");
		//saveAsButton.addActionListener(new SaveAsButtonListener());
		
		mainFrame.add(keySearch);
		mainFrame.add(searchTextField);
		mainFrame.add(searchResultsLabel);
		mainFrame.add(searchResultsTextArea);
		mainFrame.add(studyListLabel);
		mainFrame.add(listScroll);
		mainFrame.add(new JPanel());
		mainFrame.add(deleteButton);
		mainFrame.add(new JPanel());
		mainFrame.add(addButton);
		mainFrame.add(new JPanel());
		mainFrame.add(editButton);
		mainFrame.add(saveButton);
		mainFrame.add(saveAsButton);
		
		searchResultsTextArea.setEditable(false);
		listScroll.setSize(100, 100);
		
		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public void createEditWindow() {
		editWindow = new JFrame();
		editWindow.setSize(300, 200);
		editWindow.setVisible(true);
		editWindow.setLayout(new GridLayout(3, 2, 10, 10));
		editWindow.setTitle("Edit an Item");
		editStimulus = new JLabel("Stimulus:");
		editResponse = new JLabel("Response:");
		editStimulusText = new JTextField(10);
		editResponseText = new JTextField(10);
		saveChangesButton = new JButton("Save Changes");
		saveChangesButton.addActionListener(new SaveChangesButtonListener());
		editWindow.add(editStimulus);
		editWindow.add(editStimulusText);
		editWindow.add(editResponse);
		editWindow.add(editResponseText);
		editWindow.add(new JPanel());
		editWindow.add(saveChangesButton);
		
		String stringToEdit = studyListDisplay.getSelectedValue();
		for (int i = 0; i < studyList.itemArrayList.size(); i++) {
			Item itemToCompare = studyList.itemArrayList.get(i);
			String stringToCompare = itemToCompare.toString();
			if (stringToCompare.equals(stringToEdit)) {
				itemToEdit = itemToCompare;
				break;
			}	
		}
		editStimulusText.setText(itemToEdit.getStimulus());
		editResponseText.setText(itemToEdit.getResponse());
		
	}
	
	class SaveChangesButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String newStimulus = editStimulusText.getText();
			String newResponse = editResponseText.getText();
			studyList.modify(itemToEdit, newStimulus, newResponse);
			studyListDisplay.setListData(studyList.createStringArray());
			editWindow.dispose();
		}
	}
	class EditButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			createEditWindow();
			
			
			
				}
			}
	
    }

