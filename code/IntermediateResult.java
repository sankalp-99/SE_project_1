/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;


public class IntermediateResult implements ActionListener {
    Party party;
	private JFrame win;
	private JButton yesButton;
	
    
	public int result;
    int cumulScores[][];
    

   

	
	public IntermediateResult( Party party ,int cumulScores[][]) {
        this.cumulScores=cumulScores;
        this.party=party;
		result=0;
		win = new JFrame("Intermediate Result" );
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new FlowLayout());

        
		JLabel label1=displayResult();
        colPanel.add(label1);
		
	
        
       
		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,1));

		

		yesButton = new JButton("Okay");
		JPanel yesButtonPanel = new JPanel();
		yesButtonPanel.setLayout(new FlowLayout());
		yesButton.addActionListener(this);
		yesButtonPanel.add(yesButton);
        // yesButton.setBounds(20,30,5,3);
		

		buttonPanel.add(yesButton);
		
		// Clean up main panel
		
       
		colPanel.add(buttonPanel);
        
		win.getContentPane().add("Center", colPanel);
   
		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

        

	}


    private JLabel displayResult()
    {
        System.out.println("rishu");
       String res="<html><B><center>Bowling Score Report</center><br> <center>Lucky Strikes Bowling Club</center></B>\n\n\n";
       
       
       
       Vector members=party.getMembers();
       for(int i=0;i<members.size();i++)
       {
          Bowler b=(Bowler)members.get(i);
           res=res+"<center>"+b.getNickName()+"\t"+cumulScores[i][9]+"</center><br>";
          
       }
      
       res=res+"Press Ok to continue the game between winner and runner up.</html>";
       
       JLabel label1 = new JLabel();
       label1.setText(res);
       
        return label1;
    }


	public void actionPerformed(ActionEvent e) {
		
            result=1;		
			win.hide();
		
		

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
	
	public void distroy() {
		win.hide();
	}
	
}

