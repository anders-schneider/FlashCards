/**
 * 
 */
package flashCards;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Anders Schneider
 */
public class StudyGui extends JFrame {

	JButton loadButton;
	
    public static void main(String[] args) {
    	new StudyGui().createGUI();
    }
    
    void createGUI(){
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
					
		}
    }
}