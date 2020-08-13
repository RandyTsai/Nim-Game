/** Authorship
 *  COMP90041 Project A
 *  Author: YuWei Tsai
 *  StudentID: 1071545
 *  Username: yttsai
 */

/*===============CODE BEGIN================*/
import java.util.Scanner;
public class Nimsys {

        static Scanner SC = new Scanner(System.in);
        public static int STONE_REMOVED;
        public static int TOTAL_STONE_LEFT;
        public static NimPlayer P1, P2;

    /** This main Method is for the rules of the Nim game.*/
    public static void main(String[] args) {


        /** Initialize game message.*/
	    System.out.println("Welcome to Nim\n");
        System.out.println("Please enter Player 1's name:\n");
        String S1 = SC.next();
        P1 = new NimPlayer(S1);

        System.out.println("Please enter Player 2's name:\n");
        String S2 = SC.next();
        P2 = new NimPlayer(S2);


        /** The loop is designed for repeating the game or not.*/
        while(true){
            playGame();
            System.out.println("Do you want to play again (Y/N):");
            if(!SC.next().toUpperCase().equals("Y")) {break;}
        }


    }





/*===============FUNCTIONS DEFINE BELOW================*/
    private static void playGame(){
        System.out.println("Please enter upper bound of stone removal:");
        int bound_of_stone = SC.nextInt();
        System.out.println("\nPlease enter initial number of stones:");
        TOTAL_STONE_LEFT = SC.nextInt();

        boolean player=true;
        while(TOTAL_STONE_LEFT>0){

            printStones();

            if(player == true){
                System.out.println(P1.name+"'s turn - remove how many?");
                STONE_REMOVED = P1.removeStone(SC.nextInt());
            }else{
                System.out.println(P2.name+"'s turn - remove how many?");
                STONE_REMOVED = P2.removeStone(SC.nextInt());
            }
            TOTAL_STONE_LEFT= TOTAL_STONE_LEFT - STONE_REMOVED;
            player = !player;

        }

        /** Judging the winner.*/
        System.out.println("\nGame Over");
        if(player==true){System.out.println(P1.name+"win'\n");}
        else{ System.out.println(P2.name+" wins!\n");}
    }




    public static void printStones(){
        System.out.print("\n"+TOTAL_STONE_LEFT+" stones left:");
        int i=0;
        while(i< TOTAL_STONE_LEFT){
            System.out.print(" *");
            i++;
        }System.out.println("");
    }









}
