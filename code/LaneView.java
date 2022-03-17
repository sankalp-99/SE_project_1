/*
 *  constructs a prototype Lane View
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LaneView implements LaneObserver, ActionListener {


	private boolean initDone = true;

	JFrame frame;
	Container cpanel;
	Vector bowlers;
	int cur;
	Iterator bowlIt;

	JPanel[][] balls;
	JLabel[][] ballLabel;
	JPanel[][] scores;
	JLabel[][] scoreLabel;
	JPanel[][] ballGrid;
	JPanel[] pins;
	

	JButton maintenance;
	Lane lane;

	public LaneView(Lane lane, int laneNum) {

		this.lane = lane;

		initDone = true;
		frame = new JFrame("Lane " + laneNum + ":");
		cpanel = frame.getContentPane();
		cpanel.setLayout(new BorderLayout());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.hide();
			}
		});

		cpanel.add(new JPanel());

	}

	public void show() {
		frame.show();
	}

	public void hide() {
		frame.hide();
	}

	private JPanel makeFrame(Party party) throws Exception{

		initDone = false;
		bowlers = party.getMembers();
		int numBowlers = bowlers.size();

		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(0, 1));
		

		balls = new JPanel[numBowlers][23];
		ballLabel = new JLabel[numBowlers][23];
		scores = new JPanel[numBowlers][10];
		scoreLabel = new JLabel[numBowlers][10];
		ballGrid = new JPanel[numBowlers][10];
		pins = new JPanel[numBowlers];
		

		for (int i = 0; i != numBowlers; i++) {
			for (int j = 0; j != 23; j++) {
				ballLabel[i][j] = new JLabel(" ");
				balls[i][j] = new JPanel();
				balls[i][j].setBorder(
					BorderFactory.createLineBorder(Color.BLACK));
				balls[i][j].add(ballLabel[i][j]);
			}
		}

		for (int i = 0; i != numBowlers; i++) {
			for (int j = 0; j != 9; j++) {
				ballGrid[i][j] = new JPanel();
				ballGrid[i][j].setLayout(new GridLayout(0, 3));
				ballGrid[i][j].add(new JLabel("  "), BorderLayout.EAST);
				ballGrid[i][j].add(balls[i][2 * j], BorderLayout.EAST);
				ballGrid[i][j].add(balls[i][2 * j + 1], BorderLayout.EAST);
			}
			int j = 9;
			ballGrid[i][j] = new JPanel();
			ballGrid[i][j].setLayout(new GridLayout(0, 3));
			ballGrid[i][j].add(balls[i][2 * j]);
			ballGrid[i][j].add(balls[i][2 * j + 1]);
			ballGrid[i][j].add(balls[i][2 * j + 2]);
		}

		for (int i = 0; i != numBowlers; i++) {
			pins[i] = new JPanel();
			
			pins[i].setBorder(
				BorderFactory.createTitledBorder(
					((Bowler) bowlers.get(i)).getNickName()));
			pins[i].setLayout(new GridLayout(0, 11));


			
			for (int k = 0; k != 10; k++) {
				scores[i][k] = new JPanel();
				scoreLabel[i][k] = new JLabel("  ", SwingConstants.CENTER);
				scores[i][k].setBorder(
					BorderFactory.createLineBorder(Color.BLACK));
				scores[i][k].setLayout(new GridLayout(0, 1));
				scores[i][k].add(ballGrid[i][k], BorderLayout.EAST);
				scores[i][k].add(scoreLabel[i][k], BorderLayout.SOUTH);
				pins[i].add(scores[i][k], BorderLayout.EAST);

				
			}

			
			// BufferedImage myPicture = ImageIO.read(new File("fail.jpg"));
			// JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			// pins[i].add(picLabel);
			
			panel.add(pins[i]);
			
		}

		initDone = true;
		return panel;
	}




	


	private void partyAssignedPreFun(){
		while (!initDone) {
			//System.out.println("chillin' here.");
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void bowlerPreFun(LaneEvent le, int k){
	
		int[][] lescores = le.getCumulScore();

		for (int i = 0; i <= le.getFrameNum() - 1; i++) {
			if (lescores[k][i] != 0)
				scoreLabel[k][i].setText((new Integer(lescores[k][i])).toString());
				
				// System.out.print(lescores[k][i]+" ");
		}
        BufferedImage myPicture = getEmoji(lescores[k][le.getFrameNum()-1]);
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		
		
		if(pins[k].getComponents().length > 10){
			pins[k].remove(10);
			pins[k].revalidate();
			pins[k].repaint();
		}
		
		pins[k].add(picLabel);

		
		// pins[k].re
		
			
		// System.out.println();
	}

	public BufferedImage getEmoji(int score)
	{
		try{
		if(score<80)
			return  ImageIO.read(new File("fail.jpeg"));
		else if(score<120)
			return  ImageIO.read(new File("average.jpeg"));
		else
			return  ImageIO.read(new File("best.jpeg"));
		}
		catch(Exception e)
		{

		}
		return null;
	}

	private void conditionWorkFun(LaneEvent le, int k, int i){
		if (((int[]) ((HashMap) le.getScore())
			.get(bowlers.get(k)))[i]
			== 10
			&& (i % 2 == 0 || i == 19)){
			ballLabel[k][i].setText("X");
		}else if (
			i > 0
				&& ((int[]) ((HashMap) le.getScore())
					.get(bowlers.get(k)))[i]
					+ ((int[]) ((HashMap) le.getScore())
						.get(bowlers.get(k)))[i
					- 1]
					== 10
				&& i % 2 == 1){
			ballLabel[k][i].setText("/");
		}else if ( ((int[])((HashMap) le.getScore()).get(bowlers.get(k)))[i] == -2 ){
			
			ballLabel[k][i].setText("F");
		} else{
			ballLabel[k][i].setText(
				(new Integer(((int[]) ((HashMap) le.getScore())
					.get(bowlers.get(k)))[i]))
					.toString());
		}	
	}

	private void eachBowlFun(LaneEvent le, int k, int i){
		if (((int[]) ((HashMap) le.getScore())
				.get(bowlers.get(k)))[i]
				!= -1){
				
			conditionWorkFun(le, k, i);	
		}
	}

	private void bowlerFun(LaneEvent le, int k){

		bowlerPreFun(le, k);
		
		for (int i = 0; i < 21; i++) {
			eachBowlFun(le, k, i);
		}
	}

	private void partyAssignedPostFun(LaneEvent le, int numBowlers){
		for (int k = 0; k < numBowlers; k++) {
			bowlerFun(le, k);
		}

	}

	private void partyAssignedFun(LaneEvent le){
		int numBowlers = le.getPartyMembers().size();
		
		partyAssignedPreFun();

		if (le.getFrameNum() == 1
			&& le.getBall() == 0
			&& le.getIndex() == 0) {

			System.out.println("Making the frame.");
			cpanel.removeAll();
			try{
			cpanel.add(makeFrame(le.getParty()), "Center");
			}catch(Exception e)
			{

			}

			// Button Panel
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());

//				Insets buttonMargin = new Insets(4, 4, 4, 4);

			maintenance = new JButton("Maintenance Call");
			JPanel maintenancePanel = new JPanel();
			maintenancePanel.setLayout(new FlowLayout());
			maintenance.addActionListener(this);
			maintenancePanel.add(maintenance);

			buttonPanel.add(maintenancePanel);

			cpanel.add(buttonPanel, "South");

			frame.pack();

		}

		partyAssignedPostFun(le, numBowlers);
	}

	public void receiveLaneEvent(LaneEvent le) {
		if (lane.isPartyAssigned()) {
			partyAssignedFun(le);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(maintenance)) {
			lane.pauseGame();
		}
	}

}
