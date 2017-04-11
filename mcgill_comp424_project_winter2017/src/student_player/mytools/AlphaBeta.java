package student_player.mytools;

import bohnenspiel.BohnenspielBoardState;

import java.util.LinkedList;

public class AlphaBeta implements Solver<SearchNode> {
	/**
	 * SearchNode is the root of the tree
	 * of exploration (stored during execution).
	 */
	private SearchNode start;
	private int maxPlayer, minPlayer;
	
	
	// The depth at which to run the algorithm
	private int depth = 6;
	public void setDepth(int depth) {
		this.depth = depth;
	}
	/**
	 * We set maxPlayer to be this search class,
	 * and minPlayer the opponent.
	 * @param me -> maxPlayer
	 * @param opponent -> minPlayer
	 */
	public AlphaBeta(BohnenspielBoardState state, int me, int opponent) throws Exception {
		setState(state);
		
		this.minPlayer = opponent;
		this.maxPlayer = me;
	
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
			if (getStart() == null) {
				throw new Exception("Cannot find and set !");
//				return false;
			}
			else
				node = getStart();
		}
		
		if (MyTools.boardStatesEqual(node.postState, state)) { //node.postState.toString().equals(state.toString())) {
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
			if (foundAndSet) return true;
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
	 * Recursively apply alpha-beta algorithm.
	 * @param node The node from which to carry out the search
	 * @param depth How deep to search
	 * @return The heuristic of the 'best' terminal node 
	 */
	public int alphabeta(SearchNode node, int depth, int alpha, int beta) {
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
		if (maximize) {
			int v = Integer.MIN_VALUE;
			for (SearchNode child : node.children) {
				int m = alphabeta(child, depth - 1, alpha, beta);
				if (best == null || (maximize ? best < m : best > m)) {
					best = m;
					bestNode = child;
				}
				
				v = Integer.max(m, v);
				alpha = Integer.max(alpha, v);
				
				if (beta <= alpha)
					break;
			}
			node.next = bestNode;
			if (node.next == null)
				System.out.println("Found empty node! " + node + depth);
			return best;
		} else {
			int v = Integer.MAX_VALUE;
			for (SearchNode child : node.children) {
				int m = alphabeta(child, depth - 1, alpha, beta);
				if (best == null || (maximize ? best < m : best > m)) {
					best = m;
					bestNode = child;
				}
				
				v = Integer.min(m, v);
				beta = Integer.min(alpha, v);
				
				if (beta <= alpha)
					break;
			}
			node.next = bestNode;
			if (node.next == null)
				System.out.println("Found empty node! " + node + depth);
			return best;
		}
	}
	
	@Override
	public void run(int depth) {
		// Ensure unused memory is cleared
		System.gc();
		
		this.alphabeta(start, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	@Override
	public void run() {
		this.run(depth);
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
