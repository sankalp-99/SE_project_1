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


public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp;
    

	private JLabel curBowler;
	private JLabel pinsDown;
	private JButton viewLane;
	private JButton viewPinSetter;
	private JButton maintenance;

	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.subscribe(psv);

		lv = new LaneView( lane, laneNum );
		lane.getLaneSubscriber().subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );

		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());


		
		viewLane = makeButton(buttonPanel,"View Lane");
		viewPinSetter = makeButton(buttonPanel,"Pinsetter");
		maintenance = makeButton(buttonPanel,"     ");
		


		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );
		maintenance.setBackground( Color.GREEN );

		

		jp.add( cLabel );
		jp.add( curBowler );

		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

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

	public JPanel showLane() {
		return jp;
	}

	public void actionPerformed( ActionEvent e ) {
		if ( lane.isPartyAssigned() ) { 
			if (e.getSource().equals(viewPinSetter) && !psShowing) {
				
					psv.show();
					psShowing=true;
				} else {
					psv.hide();
					psShowing=false;
				}
			
		}
		if (e.getSource().equals(viewLane)) {
			if ( lane.isPartyAssigned() && !laneShowing ) { 
				
					lv.show();
					laneShowing=true;
				} else  {
					lv.hide();
					laneShowing=false;
				}
			
		}
		if (e.getSource().equals(maintenance) && lane.isPartyAssigned()) {
			
				lane.unPauseGame();
				maintenance.setBackground( Color.GREEN );
			
		}
	}

	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( (le.getBowler()).getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
		}	
		if ( !lane.isPartyAssigned() ) {
			viewLane.setEnabled( false );
			viewPinSetter.setEnabled( false );
		} else {
			viewLane.setEnabled( true );
			viewPinSetter.setEnabled( true );
		}
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );

	}

}
