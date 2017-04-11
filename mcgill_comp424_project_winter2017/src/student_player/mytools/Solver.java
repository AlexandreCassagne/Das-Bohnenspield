package student_player.mytools;

/**
 * Solver is a generic interface representing an algorithm that performs a search
 * It is used as a the framework for implementing Minimax.
 *
 * @param <Node> The type of the nodes in the tree
 */
public interface Solver<Node extends SearchNode> {
	
	public void run();
	public void run(int depth);
	
	public void setBoard(Node state);
	public Node getBoard();
	
}
