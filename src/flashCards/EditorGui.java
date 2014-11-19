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

	static EditorGui editorGui;
	
	JLabel keySearch;
	JTextField searchTextField;
	JLabel searchResultsLabel;
	JTextArea searchResultsTextArea;
	JLabel studyListLabel;
	JList<String> studyListDisplay;
	JScrollPane studyListDisplayScrollable;
	JButton deleteButton;
	JButton addButton;
	JButton editButton;
	JButton saveButton;
	JButton saveAsButton;
	
    public static void main(String[] args) {
    	editorGui = new EditorGui();
    	editorGui.createWindow();
    }

	private void createWindow() {
		setSize(400, 500);
		setVisible(true);
		int rows = 7;
		int columns = 2;
		int separation = 30;
		setLayout(new GridLayout(rows, columns, separation, separation));
		
		keySearch = new JLabel("Keyword Search:");
		searchTextField = new JTextField();
		searchResultsLabel = new JLabel("Results:");
		searchResultsTextArea = new JTextArea();
		studyListLabel = new JLabel("Study List:");
		String[] jListString = {"A", "B", "C", "D"};
		studyListDisplay = new JList<String>(jListString);
		studyListDisplayScrollable = new JScrollPane(studyListDisplay);
		deleteButton = new JButton("Delete");
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		saveButton = new JButton("Save");
		saveAsButton = new JButton("Save as");
		
		add(keySearch);
		add(searchTextField);
		add(searchResultsLabel);
		add(searchResultsTextArea);
		add(studyListLabel);
		add(studyListDisplayScrollable);
		add(new JPanel());
		add(deleteButton);
		add(new JPanel());
		add(addButton);
		add(new JPanel());
		add(editButton);
		add(saveButton);
		add(saveAsButton);
		
		searchResultsTextArea.setEditable(false);
		
    	setDefaultCloseOperation(editorGui.EXIT_ON_CLOSE);
	}

}
