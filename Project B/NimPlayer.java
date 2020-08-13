/**
 * Authorship
 * COMP90041 Project B
 * Author: YuWei Tsai
 * StudentID: 1071545
 * Username: yttsai
 */


/**========================CODE BEGIN==========================*/
public class NimPlayer {

    private String username;
    private String given_name;
    private String family_name;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    //private double winRatio;

    /** CONSTRUCTOR */
    public NimPlayer(String u, String f, String g) {
        this.username = u;
        this.family_name = f;
        this.given_name = g;
    }


    /** GETTERS */
    public String getUsername() {
        return username;
    }

    public String getgivenname() {
        return given_name;
    }

    public String getfamilyname() {
        return family_name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public double getWinRatio() {
        return this.gamesWon == 0 ? 0 : (double) this.gamesWon / (double) this.gamesPlayed;
    }

    /** SETTERS */
    public String setUsername(String s) {
        return this.username = s;
    }

    public String setgivenname(String s) {
        return this.given_name = s;
    }

    public String setfamilyname(String s) {
        return this.family_name = s;
    }

    public int setgamesPlayed(int i) {
        return this.gamesPlayed = i;
    }

    public int setgamesWon(int j) {
        return this.gamesWon = j;
    }



    public int removeStone(int NUM) {
        return NUM;
    }



    public void reset(){
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }


}
