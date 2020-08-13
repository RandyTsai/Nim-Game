/**
 * Authorship
 * COMP90041 Project B
 * Author: YuWei Tsai
 * StudentID: 1071545
 * Username: yttsai
 */


/**========================CODE BEGIN==========================*/
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

public class Nimsys {

    public static Scanner SC = new Scanner(System.in);
    public static int playerNumber = 0;
    public static NimPlayer players[] = new NimPlayer[100];

    /**
     * This main Method is for all the commands in the Nim game.
     */
    public static void main(String[] args) {


        // Initialize game message.*/
        System.out.println("Welcome to Nim");


        while (true) {
            System.out.print("\n$");
            String command = SC.next(); //read the first command (1 out of 8)
            //System.out.println(command);

            String tmpString[];
            String Uname, Fname, Gname;

            switch (command) {
                case "addplayer":
                    tmpString = parseString();
                    //System.out.println("tmpString = "+Arrays.toString(tmpString));
                    Uname = tmpString[0];
                    Fname = tmpString[1];
                    Gname = tmpString[2];
                    addPlayer(Uname, Fname, Gname);
                    break;
                case "removeplayer":
                    tmpString = parseString();
                    Uname = tmpString[0];
                    removePlayer(Uname);
                    //System.out.println("tmpString = "+Arrays.toString(tmpString));
                    break;
                case "editplayer":
                    tmpString = parseString();
                    Uname = tmpString[0];
                    Fname = tmpString[1];
                    Gname = tmpString[2];
                    editPlayer(Uname, Fname, Gname);
                    break;
                case "resetstats":
                    tmpString = parseString();
                    Uname = tmpString[0];
                    resetPlayer(Uname);
                    break;
                case "displayplayer":
                    tmpString = parseString();
                    Uname = tmpString[0];
                    displayPlayer(Uname);
                    break;
                case "rankings":
                    tmpString = parseString();
                    rankings(tmpString[0]);
                    break;
                case "startgame":
                    String tmpList[] = parseString();
                    int initial_stone = Integer.parseInt(tmpList[0]);
                    int upperBound = Integer.parseInt(tmpList[1]);
                    String nameP1 = tmpList[2];
                    String nameP2 = tmpList[3];
                    startGame(initial_stone, upperBound, nameP1, nameP2);
                    break;
                case "exit":
                    System.out.println("");
                    System.exit(0);

            }
        }


/*
 // The loop is designed for repeating the game or not.
 while(true){
 playGame();
 System.out.println("Do you want to play again (Y/N):");
 if(!SC.next().toUpperCase().equals("Y")) {break;}
 }
 */


    }


    /**
     * All functions start here=====================================================================
     **/
    //help parsing and split
    public static String[] parseString() {
        String tmpS = SC.nextLine().trim(); //**use nextLine here,if after command is "nothing",still can scan,and trim() to get rid of spaces
        String resultList[] = tmpS.split(",");
        //System.out.println(Arrays.toString(resultList));
        return resultList;
    }


    //addPlayer
    public static void addPlayer(String Uname, String Fname, String Gname) {
        //check player already exist
        for (int i = 0; i < playerNumber; i++) { //from player1, instead of player0
            if (players[i].getUsername().equals(Uname)) {
                System.out.println("The player already exists.");
                return;
            }
        }
        //if not exist, new player
        players[playerNumber] = new NimPlayer(Uname, Fname, Gname);
        playerNumber++;
        //System.out.println("added successfully");
        //System.out.println("player"+(playerNumber-1)+"="+players[playerNumber-1].getUsername());
        //for (int i=0;i<playerNumber;i++){System.out.println("playerlist= "+players[i].getUsername());}
    }


    //removePlayer
    public static void removePlayer(String Uname) {

        if (Uname.equals("")) {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            if (SC.nextLine().toLowerCase().equals("y")) {
                //players = null;  cannot do this, anyways add new player[playerNumber] will replace old user tho
                playerNumber = 0;
                //System.out.println(players+" all removed");
            }
            return; //if n, just return, if y return after delete
        }


        //check player already exist
        for (int i = 0; i < playerNumber; i++) { //from player1, instead of player0
            if (players[i].getUsername().equals(Uname)) {

                for (int j = i; j < playerNumber; j++) {
                    players[j] = players[j + 1]; //all after player[i] move forward one position, and the last one is covered as null
                }
                playerNumber--;
                //System.out.println("remove successfully");
                return;
            }
        }
        System.out.println("The player does not exist.");
    }


    //editPlayer
    public static void editPlayer(String Uname, String Fname, String Gname) {

        //check whether it does exist
        for (int i = 0; i < playerNumber; i++) {
            if (players[i].getUsername().equals(Uname)) {
                players[i].setfamilyname(Fname);
                players[i].setgivenname(Gname);
                //System.out.println(players[i].getUsername()+players[i].getfamilyname()+players[i].getgivenname()+" success");
                return;
            }
        }
        System.out.println("The player does not exist.");
    }


    //resetPlayer
    public static void resetPlayer(String Uname) {

        if (Uname.equals("")) {
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            if (SC.nextLine().toLowerCase().equals("y")) {
                for (int i = 0; i < playerNumber; i++) {
                    players[i].reset();

                }
                //System.out.println(" all reset");
            }
            return; //return here because if "n" just return let user command again instead of doing below
        }

        for (int i = 0; i < playerNumber; i++) {
            if (players[i].getUsername().equals(Uname)) {
                players[i].reset();
                //System.out.println("reset success");
                return;
            }
        }
        System.out.println("The player does not exist.");
    }


    //displayPlayer
    public static void displayPlayer(String Uname) {
        if (Uname.equals("")) {
            Arrays.sort(players,0,playerNumber, cmp_Pname);
            for (int i = 0; i < playerNumber; i++) {
                System.out.printf("%s,%s,%s,%d games,%d wins\n", players[i].getUsername(), players[i].getgivenname(), players[i].getfamilyname(), players[i].getGamesPlayed(), players[i].getGamesWon());
            }
            //System.out.println();
            return;
        }
        for (int i = 0; i < playerNumber; i++) {
            if (players[i].getUsername().equals(Uname)) {
                String grabUname = players[i].getUsername();
                String grabFname = players[i].getfamilyname();
                String grabGname = players[i].getgivenname();
                int grabGamePlayed = players[i].getGamesPlayed();
                int grabGameWon = players[i].getGamesWon();
                System.out.printf("%s,%s,%s,%d games,%d wins\n", grabUname, grabGname, grabFname, grabGamePlayed, grabGameWon);
                //System.out.println();
                return;
            }
        }
        System.out.println("The player does not exist.");
    }


    //rankings
    public static void rankings(String order) {
        int displayNumber = playerNumber > 10 ? 10 : playerNumber;

        if (order.equals("asc")) {
            Arrays.sort(players, 0, displayNumber, cmp_Pratio_asc); //(list,fromidx, toidx, comparator)
        } else {
            Arrays.sort(players, 0, displayNumber, cmp_Pratio_desc);
        }

        for (int i = 0; i < displayNumber; i++) {
            //System.out.println(players[i].getUsername()+" gamewon="+players[i].getGamesWon());
            int ratioInt = (int) Math.round(players[i].getWinRatio() * 100);
            System.out.printf("%-5s", (String) (ratioInt + "%")); //'-' left align 4 spaces
            System.out.printf("| %02d games |", players[i].getGamesPlayed()); //'02' totally 2digit, insert 0 if not enough digit
            System.out.printf(" %s %s%n", players[i].getgivenname(), players[i].getfamilyname());
        }

    }
    /**
     * construct comparator ========================================================
     **/
    //merely compare by username alphabetically
    private static Comparator<NimPlayer> cmp_Pname = new Comparator<NimPlayer>() { //must be a static variable
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            //insure they compare by lower case!or would result different
            return o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
        }
    };

    //sort by ratio asc, if equal ratio, compare by username alphabetically
    private static Comparator<NimPlayer> cmp_Pratio_asc = new Comparator<NimPlayer>() {
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            double ratio1 = o1.getWinRatio();
            double ratio2 = o2.getWinRatio();
            if (ratio1 == ratio2) {
                return o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
            } else {
                return ratio1 - ratio2 > 0 ? 1 : -1;
            }
        }
    };

    //sort by ratio desc, if equal ratio, compare by username alphabetically
    private static Comparator<NimPlayer> cmp_Pratio_desc = new Comparator<NimPlayer>() {
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            double ratio1 = o1.getWinRatio();
            double ratio2 = o2.getWinRatio();
            if (ratio1 == ratio2) {
                return o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
            } else {
                return ratio2 - ratio1 > 0 ? 1 : -1;
            }
        }
    };
    /**
     * end of construct comparator ===================================================
     **/


    //startGame
    public static void startGame(int iniStones, int uprBound, String p1Name, String p2Name) {
        for (int i = 0; i < playerNumber; i++) {
            if (players[i].getUsername().equals(p1Name)) {
                for (int j = 0; j < playerNumber; j++) {
                    if (players[j].getUsername().equals(p2Name)) {
                        NimGame game = new NimGame(iniStones, uprBound, players[i], players[j]);
                        game.playGame();
                        return;
                    }
                }
            }
        }
        System.out.println("One of the players does not exist.");
    }


}





