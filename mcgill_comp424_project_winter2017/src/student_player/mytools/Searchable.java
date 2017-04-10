package student_player.mytools;

public interface Searchable {
	public void equals();
	public int hashCode();
	public Searchable next();
	public boolean legal();
	public int heuristic();
}