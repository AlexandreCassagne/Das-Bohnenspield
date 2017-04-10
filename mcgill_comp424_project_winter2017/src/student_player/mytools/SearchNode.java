package student_player.mytools;

import java.util.*;
import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import student_player.StudentPlayer;

public class SearchNode {
	public BohnenspielMove move;
	public BohnenspielBoardState postState;
	public int heuristic;
	public int depth;
	public long player;
	
	public SearchNode next;
	
	public LinkedList<SearchNode> children = new LinkedList<SearchNode>();
	public SearchNode parent;
	
	public int depthExplored;
	
	void makeChildren() {
		
		if (this.children.size() != 0) 
			return;
		
		ArrayList<BohnenspielMove> legalMoves = postState.getLegalMoves();
		
		for (BohnenspielMove legalMove : legalMoves) {
			BohnenspielBoardState nextState = (BohnenspielBoardState) postState.clone();
			nextState.move(legalMove);
			SearchNode child = new SearchNode(legalMove, nextState, 0, depth + 1, nextState.getTurnPlayer());
			child.heuristic = HeuristicWizard.heuristic(child);
			
			children.add(child);
		}
	}
	
	// for pruning
	// alpha: the minimum 
	public int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
	
	// Doesn't copy the children and parent nodes
	// (so loses tree structure)
	public SearchNode copy() {
		return new SearchNode(move, (BohnenspielBoardState) postState.clone(), heuristic, depth, player);
	}
	
	public SearchNode(BohnenspielMove move, BohnenspielBoardState postState,
			int heuristic, int depth, long player) {
		this.move = move;
		this.postState = postState;
		this.heuristic = heuristic;
		this.depth = depth;
		this.player = player;
	}
}
