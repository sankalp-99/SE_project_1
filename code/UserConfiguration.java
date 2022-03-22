/* AddPartyView.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a patron
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class UserConfiguration implements ActionListener {


    int result=0;
	private JFrame win;
	private JButton abort,addPatron;
	private JLabel nickLabel, fullLabel;
	private JTextField nickField, fullField;
	private int nick, full;

	private boolean done;


	

	public UserConfiguration() {
        
		
		done = false;


		win = new JFrame("Configure Your Game");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Patron Panel
		JPanel patronPanel = new JPanel();
		patronPanel.setLayout(new GridLayout(3, 1));
		patronPanel.setBorder(new TitledBorder("Game Info"));
		
		nickLabel = new JLabel("Number of Lanes");
		nickField = new JTextField("", 15);
		fullLabel = new JLabel("Max patrons per party");
		fullField = new JTextField("", 15);
		
		
		makePanel("Nick Name",patronPanel,nickLabel,nickField);
		makePanel("Full Name",patronPanel,fullLabel,fullField);
	
		
		


		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));

		
		addPatron = makeButton(buttonPanel,"Set configurations");
		abort = makeButton(buttonPanel,"Abort");

		// Clean up main panel
		colPanel.add(patronPanel, "Center");
		colPanel.add(buttonPanel, "East");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

        getResult();

	}
	
	public void makePanel(String str,JPanel panel,JLabel label,JTextField text)
	{
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new FlowLayout());
		newPanel.add(label);
		newPanel.add(text);
		panel.add(newPanel);
		
	}
	
	public JButton makeButton(JPanel panel,String str)
	{
		JPanel buttonPanel=new JPanel();
		JButton button=new JButton(str);
		buttonPanel.setLayout(new FlowLayout());
		button.addActionListener(this);
		buttonPanel.add(button);
		panel.add(buttonPanel);
		
		return button;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(abort)) {
			done = true;
			win.hide();
		}

		if (e.getSource().equals(addPatron)) {
			nick = Integer.parseInt(nickField.getText());
			full = Integer.parseInt(fullField.getText());
			result=1;
			done = true;
			
			win.hide();
		}

	}

	public boolean done() {
		return done;
	}

	public int getNickName() {
		return nick;
	}

	public int getFull() {
		return full;
	}

    public int getResult() {
		while ( result == 0 ) {
			try {
				Thread.sleep(10);
			} catch ( InterruptedException e ) {
				System.err.println( "Interrupted" );
			}
		}
		return result;	
	}


	

}
