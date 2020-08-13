/**
 * Authorship
 * COMP90041 Project C
 * Author: YuWei Tsai
 * StudentID: 1071545
 * Username: yttsai
 * ========================CODE BEGIN==========================
 */


/**========================CODE BEGIN==========================*/

import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class Nimsys {

    public static Scanner SC = new Scanner(System.in);
    public static int playerNumber = 0;
    public static NimPlayer players[] = new NimPlayer[100];

    /**
     * This main Method is for all the commands in the Nim game.
     */
    public static void main(String[] args) {
        File file = new File("players.dat");


        if (file.exists()) {// KEY point for importing players
            try {
                NimPlayer player;
                FileInputStream fis = new FileInputStream("players.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                players = (NimPlayer[]) ois.readObject(); //restore players
                for (int i = 0; i < players.length; i++) {
                    if (players[i] != null) {
                        playerNumber++;
                    }
                }
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }


        // Initialize game message.*/
        System.out.println("Welcome to Nim");


        while (true) {
            System.out.print("\n$");
            String command = SC.next(); //read the first command (1 out of 8)
            //System.out.println(command);

            String tmpString[];
            String Uname, Fname, Gname;
            try {
                switch (command) {
                    case "addplayer":
                        tmpString = parseString("notequal", 3);
                        //System.out.println("tmpString = "+Arrays.toString(tmpString));
                        Uname = tmpString[0];
                        Fname = tmpString[1];
                        Gname = tmpString[2];
                        addPlayer(Uname, Fname, Gname);
                        break;
                    case "addaiplayer":
                        tmpString = parseString("notequal", 3);
                        //System.out.println("tmpString = "+Arrays.toString(tmpString));
                        Uname = tmpString[0];
                        Fname = tmpString[1];
                        Gname = tmpString[2];
                        addaiPlayer(Uname, Fname, Gname);
                        break;
                    case "removeplayer":
                        tmpString = parseString("higher", 1);
                        Uname = tmpString[0];
                        removePlayer(Uname);
                        //System.out.println("tmpString = "+Arrays.toString(tmpString));
                        break;
                    case "editplayer":
                        tmpString = parseString("notequal", 3);
                        Uname = tmpString[0];
                        Fname = tmpString[1];
                        Gname = tmpString[2];
                        editPlayer(Uname, Fname, Gname);
                        break;
                    case "resetstats":
                        tmpString = parseString("higher", 1);
                        Uname = tmpString[0];
                        resetPlayer(Uname);
                        break;
                    case "displayplayer":
                        tmpString = parseString("higher", 1);
                        Uname = tmpString[0];
                        displayPlayer(Uname);
                        break;
                    case "rankings":
                        tmpString = parseString("higher", 1);
                        rankings(tmpString[0]);
                        break;
                    case "startgame":
                        tmpString = parseString("notequal", 4);
                        int initial_stone = Integer.parseInt(tmpString[0]);
                        int upperBound = Integer.parseInt(tmpString[1]);
                        String nameP1 = tmpString[2];
                        String nameP2 = tmpString[3];
                        startGame(initial_stone, upperBound, nameP1, nameP2);
                        break;
                    case "exit":
                        //while get exit command, write all players into players.dat
                        try {
                            FileOutputStream fos = new FileOutputStream("players.dat");
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(players);
                            oos.flush();
                            oos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("");
                        System.exit(0);
                    default:
                        SC.nextLine();
                        /*here we read out the rest of strings after 'commend',
                         but not use it(if there are both Command+Argument error).
                         Without this, the While loop will continue and input
                         strings after command as "new command"*/
                        throw new InvalidCommandException(command);
                }

            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            } catch (InvalidArgmException e) {
                System.out.println(e.getMessage());
            }
        }


    }


    /**
     * All functions start here=====================================================================
     **/
    //help parsing and split, also check the argument length is correct, or throw exception
    public static String[] parseString(String logic, int threshould) throws InvalidArgmException {
        String tmpS = SC.nextLine().trim(); /*use nextLine here,if after command is "nothing",
                                              still can scan. trim() is to get rid of spaces.*/
        String resultList[] = tmpS.split(",");
        if (logic.equals("higher")) {
            if (resultList.length > threshould) {
                throw new InvalidArgmException();
            }
        } else if (logic.equals("notequal")) {
            if (resultList.length != threshould) {
                throw new InvalidArgmException();
            }
        }
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
        players[playerNumber] = new NimHumanPlayer(Uname, Fname, Gname);
        playerNumber++;
        //System.out.println("added successfully");
        //System.out.println("player"+(playerNumber-1)+"="+players[playerNumber-1].getUsername());
        //for (int i=0;i<playerNumber;i++){System.out.println("playerlist= "+players[i].getUsername());}
    }


    //addaiPlayer
    public static void addaiPlayer(String Uname, String Fname, String Gname) {
        //check player already exist
        for (int i = 0; i < playerNumber; i++) { //from player1, instead of player0
            if (players[i].getUsername().equals(Uname)) {
                System.out.println("The player already exists.");
                return;
            }
        }
        //if not exist, new player
        players[playerNumber] = new NimAIPlayer(Uname, Fname, Gname);
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
            Arrays.sort(players, 0, playerNumber, cmp_Pname);
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

    //sort by ratio asc, if equal ratio, compare by username alphabetically(and order asc)
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

    //sort by ratio desc, if equal ratio, compare by username alphabetically(and order asc)
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


/**=====================define Exception Class================**/
class InvalidCommandException extends Exception {
    public InvalidCommandException(String command) {
        super("'" + command + "' is not a valid command.");
    }
}

class InvalidArgmException extends Exception {
    public InvalidArgmException() {
        super("Incorrect number of arguments supplied to command.");
    }
}

