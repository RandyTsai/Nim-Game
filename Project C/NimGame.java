/**
 * Authorship
 * COMP90041 Project C
 * Author: YuWei Tsai
 * StudentID: 1071545
 * Username: yttsai
 */


/**
 * ========================CODE BEGIN==========================
 */
public class NimGame {

    private int totalStoneLeft;
    private int upperBound = 0;
    private NimPlayer p1, p2;
    private int stoneRemoved;


    /**
     * CONSTRUCTOR
     */
    public NimGame(int initialStone, int upperBound, NimPlayer p1, NimPlayer p2) {

        this.totalStoneLeft = initialStone;
        this.upperBound = upperBound;
        this.p1 = p1;
        this.p2 = p2;
    }


    /**
     * This Method is for the rules of the Nim game.
     */
    //function playGame
    public void playGame() {
        //count game played of players
        p1.setgamesPlayed(p1.getGamesPlayed() + 1);
        p2.setgamesPlayed(p2.getGamesPlayed() + 1);
        System.out.println();
        System.out.println("Initial stone count: " + this.totalStoneLeft);
        System.out.println("Maximum stone removal: " + this.upperBound);
        System.out.println("Player 1: " + this.p1.getgivenname() + " " + this.p1.getfamilyname());
        System.out.println("Player 2: " + this.p2.getgivenname() + " " + this.p2.getfamilyname());

        boolean player = true;
        while (totalStoneLeft > 0) {

            printStones();
            //int validMoveStones = Math.min(totalStoneLeft, upperBound);
            if (player == true) {
                if (!takeTurns(this.p1)) continue;
            } else {
                if (!takeTurns(this.p2)) continue;
            }
            totalStoneLeft = totalStoneLeft - stoneRemoved;
            player = !player; //alternating fashion of players

        }

        // Judging the winner.
        System.out.println("\nGame Over");
        if (player == true) {
            judging(this.p1);
        } else {
            judging(this.p2);
        }
    }


    //function takeTurns
    public boolean takeTurns(NimPlayer player) {
        int validMoveStones = Math.min(totalStoneLeft, upperBound);
        System.out.println(player.getgivenname() + "'s turn - remove how many?");
        int input = Nimsys.SC.nextInt();
        if (input > 0 && input <= validMoveStones) {
            stoneRemoved = player.removeStone(input);
            return true;
        } else {
            System.out.println();
            System.out.println("Invalid move. You must remove between 1 and " + validMoveStones + " stones.");
            return false; //continue
        }
    }


    //function printStones
    public void printStones() {
        System.out.print("\n" + totalStoneLeft + " stones left:");
        int i = 0;
        while (i < totalStoneLeft) {
            System.out.print(" *");
            i++;
        }
        System.out.println("");
    }


    //function judging
    public void judging(NimPlayer player) {
        System.out.println(player.getgivenname() + " " + player.getfamilyname() + " wins!");
        player.setgamesWon(player.getGamesWon() + 1);
    }


}
