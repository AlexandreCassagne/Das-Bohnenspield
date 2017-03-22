package student_player.mytools;

public class MyTools {
	
	Runtime rt = Runtime.getRuntime();
	public long usedMemory() {
		return (rt.totalMemory() - rt.freeMemory());
	}
	
    public static double getSomething(){
        return Math.random();
    }
}
