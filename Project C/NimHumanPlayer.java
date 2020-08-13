/**
 * Authorship
 * COMP90041 Project C
 * Author: YuWei Tsai
 * StudentID: 1071545
 * Username: yttsai
 */


public class NimHumanPlayer extends NimPlayer {

    public NimHumanPlayer(String u, String f, String g) {
        super(u, f, g);
    }

    @Override
    public int removeStone(int NUM) {//must be have same argument as method in super class, or just overload
        return NUM;
    }
}


