package student_player.mytools;

import bohnenspiel.BohnenspielBoardState;

public interface Solver<Node extends SearchNode> {
	public void run();
	public void setBoard(Node state);
	public Node getBoard();
}
