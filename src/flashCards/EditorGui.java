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
	JFrame addWindow;
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
	Item itemToDelete;
	JLabel addStimulus;
	JLabel addResponse;
	JTextField addStimulusText;
	JTextField addResponseText;
	JButton addItemButton;
	
	
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
		searchTextField.addActionListener(new SearchTextFieldListener());
		searchResultsLabel = new JLabel("Results:");
		searchResultsTextArea = new JTextArea();
		studyListLabel = new JLabel("Study List:");
		String[] jListString = {"A", "B", "C", "D"};
		studyListDisplay = new JList<String>(studyList.createStringArray());
		studyListDisplay.setVisibleRowCount(10);
		studyListDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScroll = new JScrollPane(studyListDisplay);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());
		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonListener());
		editButton = new JButton("Edit");
		editButton.addActionListener(new EditButtonListener());
		saveButton = new JButton("Save");
		saveButton.addActionListener(new SaveButtonListener());
		saveAsButton = new JButton("Save as");
		saveAsButton.addActionListener(new SaveAsButtonListener());
		
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
	
	public void createAddWindow() {
		addWindow = new JFrame();
		addWindow.setSize(300, 200);
		addWindow.setVisible(true);
		addWindow.setLayout(new GridLayout(3, 2, 10, 10));
		addWindow.setTitle("Edit an Item");
		addStimulus = new JLabel("Stimulus:");
		addResponse = new JLabel("Response:");
		addStimulusText = new JTextField(10);
		addResponseText = new JTextField(10);
		addItemButton = new JButton("Add Item");
		addItemButton.addActionListener(new AddItemButtonListener());
		addWindow.add(addStimulus);
		addWindow.add(addStimulusText);
		addWindow.add(addResponse);
		addWindow.add(addResponseText);
		addWindow.add(new JPanel());
		addWindow.add(addItemButton);
		
	}
	
	public void deleteItem() {
		String stringToDelete = studyListDisplay.getSelectedValue();
		for (int i = 0; i < studyList.itemArrayList.size(); i++) {
			Item itemToCompare = studyList.itemArrayList.get(i);
			String stringToCompare = itemToCompare.toString();
			if (stringToCompare.equals(stringToDelete)) {
				itemToDelete = itemToCompare;
				break;
			}	
		}
		studyList.delete(itemToDelete);
		studyListDisplay.setListData(studyList.createStringArray());
	}
	
	public void findItem() {
		String stringToFind = searchTextField.getText();
		try{
			Item foundItem = studyList.find(stringToFind);
			searchResultsTextArea.setText(foundItem.toString());
			studyListDisplay.setSelectedIndex(studyList.itemArrayList.indexOf(foundItem));
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
		}
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
	
	class AddItemButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String newStimulus = addStimulusText.getText();
			String newResponse = addResponseText.getText();
			Item itemToAdd = new Item(newStimulus, newResponse);
			studyList.add(itemToAdd);
			studyListDisplay.setListData(studyList.createStringArray());
			addWindow.dispose();
		}
	}
	
	class EditButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			createEditWindow();
		}
	}
	
	class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			createAddWindow();
		}
	}

	class DeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			deleteItem();
		}
	}
	
	class SearchTextFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			findItem();			
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

	
}

	