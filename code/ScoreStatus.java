
import java.util.Iterator;
import java.util.HashMap;
import java.io.Serializable;



public class ScoreStatus implements Serializable{
    
	private HashMap scores;
    
	private int[] curScores;
	private int[][] cumulScores;
	
	private int[][] finalScores;

	public ScoreStatus()
	{
		scores = new HashMap();
	}

	public void setFinalScore(int bowlIndex, int gameNumber, int input){
		finalScores[bowlIndex][gameNumber] = input;
	}

	public int[] getFinalScore(int bowlIndex){
		return finalScores[bowlIndex];
	}

	public int getCumulScore(int bowlIndex, int gameNumber){
		return cumulScores[bowlIndex][gameNumber];
	}

	public void resetScoreAray(Lane lane){
		curScores = new int[lane.getParty().getMembers().size()];
		cumulScores = new int[lane.getParty().getMembers().size()][10];
		finalScores = new int[lane.getParty().getMembers().size()][128];
	}

	public int[][] getFinalScore(){
		return finalScores;
	}

	public int[][] getCumulScores(){
		return cumulScores;
	}

	public HashMap getScore(){
		return scores;
	}

	public int[] getCurScores(){
		return curScores;
	}

	/** resetScores()
	 * 
	 * resets the scoring mechanism, must be called before scoring starts
	 * 
	 * @pre the party has been assigned
	 * @post scoring system is initialized
	 */
	public void resetScores(Lane lane) {
		
		Iterator bowlIt = (lane.getParty().getMembers()).iterator();

		while ( bowlIt.hasNext() ) {
			int[] toPut = new int[25];
			for ( int i = 0; i != 25; i++){
				toPut[i] = -1;
			}
			scores.put( bowlIt.next(), toPut );
		}
		
		

		lane.setGameFinished(false);
		lane.setFrameNumber(0);
	}

	/** markScore()
	 *
	 * Method that marks a bowlers score on the board.
	 * 
	 * @param Cur		The current bowler
	 * @param frame	The frame that bowler is on
	 * @param ball		The ball the bowler is on
	 * @param score	The bowler's score 
	 */
	public void markScore(Lane lane, Bowler Cur, int frame, int ball, int score, int bowlIndex){
		
		int[] curScore;
		int index =  ( (frame - 1) * 2 + ball);

		curScore = (int[]) scores.get(Cur);

	
		curScore[ index - 1] = score;
		scores.put(Cur, curScore);
		getScore( Cur, frame ,bowlIndex, ball);
		lane.getLaneSubscriber().publish( lane.lanePublish() );
	}

/** getScore()
	 *
	 * Method that calculates a bowlers score
	 * 
	 * @param Cur		The bowler that is currently up
	 * @param frame	The frame the current bowler is on
	 * 
	 * @return			The bowlers total score
	 */



private void preWork(Bowler Cur, int bowlIndex){

	int[] curScore;
	curScore = (int[]) scores.get(Cur);

	for(int frameNum = 0; frameNum<10; frameNum++){

		cumulScores[bowlIndex][frameNum] = curScore[frameNum * 2] == -1 ? 0 : curScore[frameNum * 2];
		cumulScores[bowlIndex][frameNum] += curScore[frameNum * 2 + 1] == -1 ? 0 : curScore[frameNum * 2 + 1];

	}
}
	private void  strikeWork(int[] curScore , int bowlIndex , int frameNum)
	{
		if(curScore[frameNum * 2] == 10){
			if(curScore[(frameNum+1) * 2] == 10){

				cumulScores[bowlIndex][frameNum] += curScore[(frameNum+2) * 2] == -1 ? 0 : curScore[(frameNum+2) * 2];
			}else{
				cumulScores[bowlIndex][frameNum] += curScore[(frameNum+1) * 2 + 1] == -1 ? 0 : curScore[(frameNum+1) * 2 + 1];
			}
		}
	}

	private void first8FrameWorkFun(Bowler Cur, int bowlIndex){

		int[] curScore;
		curScore = (int[]) scores.get(Cur);

		for(int frameNum = 0; frameNum<=7; frameNum++){

			if(cumulScores[bowlIndex][frameNum] == 10){

				cumulScores[bowlIndex][frameNum] += curScore[(frameNum+1) * 2] == -1 ? 0 : curScore[(frameNum+1) * 2];

				strikeWork(curScore,bowlIndex,frameNum);
			}
		}

	}

	private void ninethFrameWorkFun(Bowler Cur, int bowlIndex){

		int[] curScore;
		curScore = (int[]) scores.get(Cur);
		int frameNum = 8;

		if(cumulScores[bowlIndex][frameNum] == 10){

			cumulScores[bowlIndex][frameNum] += curScore[(frameNum+1) * 2] == -1 ? 0 : curScore[(frameNum+1) * 2];

			if(curScore[frameNum * 2] == 10){
				cumulScores[bowlIndex][frameNum] += curScore[(frameNum+1) * 2 + 1] == -1 ? 0 : curScore[(frameNum+1) * 2 + 1];
			}
		}
	}

	private void tenthFrameWorkFun(Bowler Cur, int bowlIndex){

		int[] curScore;
		curScore = (int[]) scores.get(Cur);
		int frameNum= 9;
		cumulScores[bowlIndex][frameNum] += curScore[frameNum * 2 + 2] == -1 ? 0 : curScore[frameNum * 2 + 2];

	}

	private void accumulateScore(int bowlIndex){

		for(int frameNum = 1; frameNum<10; frameNum++){
			cumulScores[bowlIndex][frameNum] += cumulScores[bowlIndex][frameNum-1];
		}
	}

	private void postWork(Bowler Cur, int bowlIndex, int frame, int ball){
        int[] curScore;
        curScore = (int[]) scores.get(Cur);
        // System.out.println();
        // System.out.println("***************");
        // System.out.println(Cur.getNickName() + " " + bowlIndex + " => " + frame + " : " + ball);
        // System.out.print("Before :: ");
        // for(int i=0; i<frame; i++){
        //  System.out.print(cumulScores[bowlIndex][i] + "("+curScore[i*2]+","+curScore[i*2+1]+") ");
        // }
        // System.out.println();
        int highest = -1;
        if(curScore[0] == 0 && curScore[1] == 0){
            highest = 0;
            cumulScores[bowlIndex][0] -= (curScore[2] == -1 ? 0 : Math.ceil((float)curScore[2])/2);
        }else{
            highest = Math.max(curScore[0], curScore[1]);
        }
        for(int frameNum=1; frameNum<frame; frameNum++){
            if(curScore[frameNum * 2] == 0){
                // System.out.println();
                // System.out.println("IN");
                // System.out.println();
                if(curScore[(frameNum-1) * 2] != 10 && curScore[(frameNum-1) * 2 + 1] == 0){
                    cumulScores[bowlIndex][frameNum] -= Math.ceil((float)highest/2);
                }
                if(curScore[frameNum * 2 + 1] == 0){
                    cumulScores[bowlIndex][frameNum] -= Math.ceil((float)highest/2);
                }
            }
            highest = Math.max(highest, curScore[frameNum * 2]);
            highest = Math.max(highest, curScore[frameNum * 2 + 1]);            
        }
        if(curScore[9 * 2 + 2] == 0){
            if(curScore[9*2 + 1] == 0){
                cumulScores[bowlIndex][9] -= Math.ceil((float)highest/2);
            }
        }
        // System.out.print("After  :: ");
        
        // for(int i=0; i<frame; i++){
        //  System.out.print(cumulScores[bowlIndex][i] + " ");
        // }
        // System.out.println();
        // System.out.println("***************");
        // System.out.println();
    }
    private void findStrikeCount(Bowler Cur){
        int[] curScore;
        curScore = (int[]) scores.get(Cur);
        Cur.resetStrikeCount();
        for(int frameNum = 0; frameNum<10; frameNum++){
            if(curScore[frameNum*2] == 10)
                Cur.increseStrikeCount();           
        }
    }
    public int getScore( Bowler Cur, int frame, int bowlIndex, int ball) {
        
        // System.out.println("here : ");
        findStrikeCount(Cur);
        preWork(Cur, bowlIndex);
        first8FrameWorkFun(Cur, bowlIndex);
        ninethFrameWorkFun(Cur, bowlIndex);
        tenthFrameWorkFun(Cur, bowlIndex);
        postWork(Cur, bowlIndex, frame, ball);
        accumulateScore(bowlIndex);
        // for(int i=0;i<10;i++)
        // System.out.print(cumulScores[bowlIndex][i]+" ");
        // System.out.println();
        return cumulScores[bowlIndex][9];
    }
}
