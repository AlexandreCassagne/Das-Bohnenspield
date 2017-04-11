package student_player;

import java.util.Date;
import java.util.LinkedList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import bohnenspiel.BohnenspielPlayer;
import student_player.mytools.HeuristicWizard;
import student_player.mytools.Minimax;

/** A Hus player submitted by a student. */
public class StudentPlayer extends BohnenspielPlayer {
	/** You must modify this constructor to return your student number.
	 * This is important, because this is what the code that runs the
	 * competition uses to associate you with your agent.
	 * The constructor should do nothing else. */
	public StudentPlayer() { super("260617865"); }

	/** This is the primary method that you need to implement.
	 * The ``board_state`` object contains the current state of the game,
	 * which your agent can use to make decisions. See the class 
	 * bohnenspiel.RandomPlayer
	 * for another example agent.
	 **/
	
	private Minimax solver;
	
	// Time limits (seconds)
	double firstRun = 30.0;
	double subsequentRuns = 0.700;
	
	int shortDepth;
	
	public BohnenspielMove chooseMove(BohnenspielBoardState board_state)
	{
		try {
			if (solver == null) {
				/**
				 * solver == null means that this is the first run.
				 */
				
				
				HeuristicWizard.setPlayers(this.player_id, this.opponent_id);
				solver = new Minimax(board_state, this.player_id, this.opponent_id);
				
				shortDepth = calibrateDepth() - 1;
				solver.setDepth(shortDepth);
				
			}
			else {
				System.out.println("Found board: " + solver.findAndSet(board_state, null, 4));
				solver.run(shortDepth);
			}
		} catch (Exception e) {
			/**
			 * todo
			 */
			e.printStackTrace();
		}
		
		BohnenspielMove nextMove = solver.getStart().next.move;
		return nextMove;

	}
	private static double timeDifference(long end, long start) {
		return (double) (end - start) / 1000.0;
	}
	private static double timeDifference(Date end, Date start) {
		return (double) timeDifference(end.getTime(), start.getTime());
	}
	
	/**
	 * @return the evaluated optimal depth to stay in the time limit
	 */
	public int calibrateDepth() {
		Date start = new Date();
		System.out.println(start);
		
		int maxDepth = 0;
		
		int attemptedDepth = 0;
		boolean done = false;
		
		LinkedList<Double> times = new LinkedList<>();
		times.add(0.1);
		
		while ( true ) {
			attemptedDepth ++;
			
			Date experimentStart = new Date();
			solver.run(attemptedDepth);
			Date experimentEnd = new Date();
			
			double td = timeDifference(experimentEnd, experimentStart);
			
			if (!done && td > (subsequentRuns * 0.9) ) {
				maxDepth = attemptedDepth - 1;
				done = true;
			}
			
			times.add(td);
			System.out.println( "Depth " + attemptedDepth+ " : " + td);
			System.out.println("Ratio of times: " + times.get(attemptedDepth) / times.get(attemptedDepth - 1));
			
			double projection = td * (times.get(attemptedDepth) / times.get(attemptedDepth - 1));
			double projectedTD = timeDifference( (new Date()).getTime() + (long)(projection * 1000.0), start.getTime() );
			System.out.println("Next step projected to take " + td + "s and end after " + projectedTD);
			
			if (projectedTD > firstRun * 0.9)
				break;
		}
		
		System.out.println("Stop. Attempted depths 0 to " + (attemptedDepth - 1) + ".\nUsing "+ maxDepth +" for subsequent runs.");
		
		return maxDepth;
	}
	
}