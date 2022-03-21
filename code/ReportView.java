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
import java.util.*;
import java.io.File;



public class ReportView implements ActionListener {



	private JFrame win;
	private JButton abort;
    int[][] cumulScores;
    Party party;
	

	public ReportView(int[][] cumulScores,Party party) throws Exception{

		this.cumulScores=cumulScores;
        this.party=party;
		

		win = new JFrame("Report");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

        
        win.setSize(400,400);
        win.setLocationRelativeTo(null);
        win.setVisible(true);

        win.setLayout(new BorderLayout());
        win.setContentPane(new JLabel(new ImageIcon("bg.png")));
        win.setLayout(new FlowLayout());

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Patron Panel
		
		
		
		


		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,1));

		
		
		abort = makeButton(buttonPanel,"Abort");

		// Clean up main panel
		
		// colPanel.add(buttonPanel, "West");
		// win.getContentPane().add("Bottom", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

        displayResult();

	}


     private void displayResult()
     {
        String res="<html><B><center><font size=+4>Bowling Score Report</font></center><br> <center><font size=+3>Lucky Strikes Bowling Club</font></center></B>\n\n\n";
        
        int winnerIndex=winnerIndex();
        
        Vector members=party.getMembers();
        for(int i=0;i<members.size();i++)
        {
           Bowler b=(Bowler)members.get(i);
		   if(i==winnerIndex){
		   File f = new File("winner.jpeg"); 
           res=res+"<center><font size=+2>"+b.getNickName()+"\t"+cumulScores[i][9]+"</font>\t\t<img src=\"file:"+f.toString()+"\"></center><br>";
		   }
		   else{
			File f = new File("looser.jpeg"); 
		    res=res+"<center><font size=+2>"+b.getNickName()+"\t"+cumulScores[i][9]+"</font>\t\t<img src=\"file:"+f.toString()+"\"></center><br>";
		   }
        }

        res=res+"<font size=+3>Thank you for your continuing patronage.<font size=+1></html>";
        
        
        
        
        JLabel label1 = new JLabel();
        label1.setText(res);
        win.add(label1);
     }

     public int winnerIndex()
	{
		int high=0;
		for(int i=0;i<cumulScores.length;i++)
		{
			if(cumulScores[high][9] < cumulScores[i][9])
				high=i;
		}
		return high;
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
			
			win.hide();
		}

		

	}



}
