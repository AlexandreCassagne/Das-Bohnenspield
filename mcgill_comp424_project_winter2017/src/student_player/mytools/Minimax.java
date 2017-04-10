package student_player.mytools;

import java.util.ArrayList;
import java.util.LinkedList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;

public class Minimax implements Solver<SearchNode> {
	/**
	 * SearchNode is the root of the tree
	 * of exploration (stored during execution).
	 */
	private SearchNode start;
	int maxPlayer, minPlayer;
	
	// The depth at which to run the algorithm
	public int depth = 6;

	/**
	 * We set maxPlayer to be this search class,
	 * and minPlayer the opponent.
	 * @param me -> maxPlayer
	 * @param opponent -> minPlayer
	 */
	public Minimax(BohnenspielBoardState state, int me, int opponent) throws Exception {
		setState(state);
		
		this.minPlayer = opponent;
		this.maxPlayer = me;
		
		HeuristicWizard.maxPlayer = this.maxPlayer;
	}
	
	/**
	 * The heuristic used by this search
	 * @param n
	 * @return the heuristic for node n
	 */
	int heuristic(SearchNode n) {
		return HeuristicWizard.heuristic(n);
	}
	
	public SearchNode getStart() { return start; }
	private void setState(BohnenspielBoardState state) throws Exception {
		if (state == null)
			throw new Exception("State cannot be null when setting state !");
		this.start = new SearchNode(null, state,
				0, 0, state.getTurnPlayer());
		this.start.heuristic = heuristic(this.start);
	}
	
	/**
	 * Searches (recursively) for a SearchNode in the tree 
	 * which equals the one passed as an argument.
	 * If it is found, it sets the `start` field
	 * to that node, and returns `true` for "found".
	 * Otherwise, returns false and initialises the appropriate SearchNode
	 * to set as start.
	 * Preserves tree structure and avoids re-calculating work done.
	 * 
	 * @param state Board to search for in tree
	 * @param node (Recursive) starting point in the search
	 * @param depth levels to go
	 * @return true if found, false otherwise.
	 */
	public boolean findAndSet(BohnenspielBoardState state, SearchNode node, int depth) throws Exception {
		if (node == null) {
			if (getStart() == null)
				return false;
			else
				node = getStart();
		}
		
		if (node.postState.toString().equals(state.toString())) {
			start = node;
			return true;
		}
		else if (depth == 0) {
			setState(state);
			return false;
		}
		
		for (SearchNode child : node.children) {
			if (child == null) break;
			boolean foundAndSet = findAndSet(state, child, depth - 1);
			if (foundAndSet)
				return true;
		}
		setState(state);
		return false; 
	}
	
	
	/**
	 * The node with the best heuristic in a collection of SearchNodes
	 * @param children The collection to search
	 * @return If the collection is not empty, the best heuristic. Returns null otherwise
	 */
	public SearchNode best(LinkedList<SearchNode> children) {
		if (children.size() == 0)
			return null;
		
		SearchNode best = children.removeFirst();
		int bestHeuristic = heuristic(best);
		
		for (SearchNode node : children) {
			int h = heuristic(node);
			// if max player -> maximise else minimise
			if (node.player == maxPlayer ? (bestHeuristic < h) : (bestHeuristic > h)) {
				best = node;
				bestHeuristic = h;
			}
		}
		return best;
	}
	/**
	 * Recursively apply mini-max algorithm.
	 * @param node The node from which to carry out the search
	 * @param depth How deep to search
	 * @return The heuristic of the 'best' terminal node 
	 */
	public int minimax(SearchNode node, int depth) {
		// 1. populate the node with children
		node.makeChildren();
		// 2.i. Terminal node case:
		if (node.children.isEmpty())
			return heuristic(node);
		if (depth == 0) {
			return heuristic(best(node.children));
		}
		
		boolean maximize = node.postState.getTurnPlayer() == maxPlayer;
		
		// 2. ii. General case:
		Integer best = null;
		SearchNode bestNode = null;
		for (SearchNode child : node.children) {
			int m = minimax(child, depth - 1);
			if (best == null || (maximize ? best < m : best > m)) {
				best = m;
				bestNode = child;
			}
		}
		node.next = bestNode;
		if (node.next == null)
			System.out.println("Found empty node!" + node + depth);
		return best;
	}
	
	public void run(int depth) {
		this.minimax(start, depth);
	}
	
	@Override
	public void run() {
		this.minimax(start, depth);
	}
	
	@Override
	public void setBoard(SearchNode state) {
		this.start = state;
	}

	@Override
	public SearchNode getBoard() {
		return this.start;
	}

}
