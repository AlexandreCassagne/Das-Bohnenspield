package student_player;

import java.util.ArrayList;

import boardgame.Move;
import bohnenspiel.BohnenspielBoard;
import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import bohnenspiel.BohnenspielPlayer;
import bohnenspiel.BohnenspielMove.MoveType;
import student_player.mytools.Minimax;
import student_player.mytools.MyTools;
import student_player.mytools.Solver;

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
	
	public BohnenspielMove chooseMove(BohnenspielBoardState board_state)
	{
		try {
			if (solver == null) {
				solver = new Minimax(board_state, this.player_id, this.opponent_id);
				solver.run(6);
			}
			else {
				System.out.println("Found board: " + solver.findAndSet(board_state, null, 5));
				solver.run();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Ensure unused memory is cleared
		System.gc();
		
		BohnenspielMove nextMove = solver.getStart().next.move;
		return nextMove;

	}
}