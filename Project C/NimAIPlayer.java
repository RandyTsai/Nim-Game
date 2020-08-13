/*
	NimAIPlayer.java

	This class is provided as a skeleton code for the tasks of
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks.
*/

public class NimAIPlayer extends NimPlayer implements Testable {
    // you may further extend a class or implement an interface
    // to accomplish the tasks.

    public NimAIPlayer(String u, String f, String g) {
        super(u, f, g);
    }


    @Override
    public int removeStone(int NUM) {//must be have same argument as method in super class, or just overload
        return NUM;
    }


    public String advancedMove(boolean[] available, String lastMove) {
        // the implementation of the victory
        // guaranteed strategy designed by you
        String move = "";
        return move;
    }
}
