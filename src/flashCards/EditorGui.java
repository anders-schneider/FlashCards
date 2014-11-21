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
 * @author Monica Ionescu
 */
public class EditorGui extends JFrame {
	
	JFrame mainFrame;
	JPanel topPanel;
	JPanel midPanel;
	JPanel bottomPanel;
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

    /* First the user is prompted to load in a study list. Afterwards, the GUI is created. */
	private void createWindow() {
		studyList = new StudyList();
		
		try {
			studyList.load();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Oops! The following error ocurred: " + e.getMessage());
		}
		mainFrame = new JFrame();
		mainFrame.setSize(370, 430);
		mainFrame.setVisible(true);
		
		mainFrame.setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		midPanel = new JPanel();
		bottomPanel = new JPanel();
		
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
		
		topPanel.setLayout(new GridLayout(2,2,10,10));
		topPanel.add(keySearch);
		topPanel.add(searchTextField);
		topPanel.add(searchResultsLabel);
		topPanel.add(searchResultsTextArea);
		
		midPanel.add(studyListLabel);
		midPanel.add(listScroll);
		
		bottomPanel.add(deleteButton);
		bottomPanel.add(addButton);
		bottomPanel.add(editButton);
		bottomPanel.add(saveButton);
		bottomPanel.add(saveAsButton);

		mainFrame.add(topPanel, BorderLayout.NORTH);
		mainFrame.add(midPanel, BorderLayout.CENTER);
		mainFrame.add(bottomPanel, BorderLayout.SOUTH);
		
		searchResultsTextArea.setEditable(false);
		listScroll.setSize(200, 300);
		
		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
	}
	
	/* When a user wants to edit an item, this window pops up */
	public void createEditWindow() {
		
		if (studyListDisplay.getSelectedValue() != null) {
			editWindow = new JFrame();
			editWindow.setSize(260, 150);
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
			searchResultsTextArea.setText("");
		} else {
			JOptionPane.showMessageDialog(null, "No item selected.");
		}
	}
	
	/* When a user clicks the "Add" button, this window pops up */
	public void createAddWindow() {
		addWindow = new JFrame();
		addWindow.setSize(200, 150);
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
	
	/* This method permanently deletes the highlighted item) */
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
	
	/* Searches for the item based on user input. If no items match the input, a 
	 * window pops up alerting the user that there are no such items.
	 */
	public void findItem() {
		String stringToFind = searchTextField.getText();
		try{
			Item foundItem = studyList.find(stringToFind);
			searchResultsTextArea.setText(foundItem.toString());
			studyListDisplay.setSelectedIndex(studyList.itemArrayList.indexOf(foundItem));
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Nothing found.\nCheck your spelling and try again.");
		}
	}
	
	/* Completes the process of editing an item by creating a new item from user input */
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
	
	/* Completes the process of adding an item by creating a new item from user input */
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