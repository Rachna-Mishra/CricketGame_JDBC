package com.rachna;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MatchController {
    Scanner sc = new Scanner(System.in);
    private int team1Score;
    private int team2Score;
    private int wickets1;
    private int wickets2;
    private static int numberOfTeams;
    private static int numberOfOvers;
    private static int numberOfMatches;
    private static String winnerOfSeries;
    private List<ScoreBoard> listOfScoreBoard;
    private List<String> listOfWinnerTeam;
    private static String selectedTeamForBatting;
    private static String selectedTeamForBowling;
    private static String result;

    MatchController(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public void organizeMatches() throws Exception {
        listOfScoreBoard = new ArrayList<>();
        listOfWinnerTeam = new ArrayList<>();
        while (numberOfMatches-- > 0) {
            System.out.println("Enter number of overs for this match");
            numberOfOvers = sc.nextInt();

            System.out.println("Number of teams participating for this match range upto 4");
            numberOfTeams = sc.nextInt();

            ScoreBoard scoreBoard = new ScoreBoard();
            listOfScoreBoard.add(scoreBoard);

            Match match = new Match(numberOfOvers, scoreBoard);

            // =====================Selection Of 2 Teams from Number Of Teams ======================
            List<String> selectedTeams = GeneralUtils.getSelectedTeams(numberOfTeams);

            System.out.println("-------------Match Started between Teams " + " " + selectedTeams.get(0) + " and " + selectedTeams.get(1) + "-----------------");

            //------------------Match Tossed between selected Teams-------------------
            String tossWonTeamCode = (GeneralUtils.getWinner() == 0) ? selectedTeams.get(0) : selectedTeams.get(1);
            selectedTeamForBatting = selectedTeams.get(0);
            selectedTeamForBowling = selectedTeams.get(1);
            System.out.println("------------Team " + tossWonTeamCode + " Won the Toss------------");

            String batOrBowl = (GeneralUtils.getWinner() == 0) ? Constant.BATTING.name() : Constant.BOWLING.name();
            System.out.println("------------Team " + tossWonTeamCode + " Opted for " + batOrBowl + "---------------");

            if (tossWonTeamCode == selectedTeams.get(1)) {
                if (Constant.BATTING.name().equalsIgnoreCase(batOrBowl)) {
                    selectedTeamForBatting = selectedTeams.get(1);
                    selectedTeamForBowling = selectedTeams.get(0);
                } else {
                    selectedTeamForBatting = selectedTeams.get(0);
                    selectedTeamForBowling = selectedTeams.get(1);
                }
            } else {
                if (Constant.BOWLING.name().equalsIgnoreCase(batOrBowl)) {
                    selectedTeamForBatting = selectedTeams.get(1);
                    selectedTeamForBowling = selectedTeams.get(0);
                }
            }

            /*   /* -------Adding Players detail Of Playing Teams in scoreBoard --------- */
        /*    addPlayersDetail(scoreBoard, selectedTeamForBatting);
            addPlayersDetail(scoreBoard, selectedTeamForBowling);

            //----------------------------Match Started-----------------
            startMatch(match, selectedTeamForBatting, selectedTeamForBowling);

            //------------------Showing ScoreBoard Details------------------------
            scoreBoard.displayScoreBoard(selectedTeamForBatting, selectedTeamForBowling);
            System.out.println();
        }
        getWinnerOfSeries();
        displayAllMatchScoreBoard();
        System.out.println("Winner of Series : "+winnerOfSeries);
    }

    public void startMatch(Match match, Teams team1, Teams team2)
    {
        System.out.println("----------Match Started---------------");
        System.out.println("---------Team " + team1 + " Ready for Batting----------------");
        team1Score = match.calculateScore(team1);
        wickets1 = match.getTotalWickets(team1);
        System.out.println("Total Wickets Of Team : " + wickets1);
        System.out.println();
        System.out.println("---------Now Team " + team2 + " Ready for Batting----------------");
        team2Score = match.calculateScore(team2);
        wickets2 = match.getTotalWickets(team2);
        System.out.println("Total Wickets Of Team : " + wickets2);
        System.out.println();
        if (team1Score > team2Score)
            result = "team1";
        else if (team1Score < team2Score)
            result = "team2";
        else
            result = "tie";
        printScore(result);
    }

    public void printScore(String winnerTeamOfMatch) {
        System.out.println();
        System.out.println();
        System.out.println("Team1 Score : " + team1Score + " " + "Total Wickets : " + wickets1);
        System.out.println("Team2 Score : " + team2Score + " " + "Total Wickets : " + wickets2);
        if(winnerTeamOfMatch.equalsIgnoreCase("team1"))
        {
            listOfWinnerTeam.add(selectedTeamForBatting.name());
            System.out.println("---------------------Team 1 Won--------------------");
        }
        else if(winnerTeamOfMatch.equalsIgnoreCase("team2"))
        {
            listOfWinnerTeam.add(selectedTeamForBowling.name());
            System.out.println("---------------------Team 2 Won--------------------");
        }
        else
        {
            listOfWinnerTeam.add("Tie");
            System.out.println("---------------------Match Tie--------------------");
        }
    }

    public void addPlayersDetail(ScoreBoard scoreBoard,Teams teamPlaying)
    {
        Teams[] teams = Teams.values();
        for (Teams team : teams) {
            if(team.equals(teamPlaying))
            {
                Map<Integer,String> players=team.getPlayerList();
                for(Map.Entry<Integer,String> playerList:players.entrySet())
                {
                    int jerseyNumber=playerList.getKey();
                    String playerName=playerList.getValue();
                    PlayerDetail player=new PlayerDetail(jerseyNumber,playerName,team).newPlayer();
                    String playerKey = player.getUniquePlayerKey(player);
                    scoreBoard.playerDetailsMap.put(playerKey, player);
                }
              //  scoreBoard.displayTeamScore(team);
                break;
            }
        }
    }

    public void getWinnerOfSeries() {
        int max = 0;
        int[] countWinningPoint = new int[numberOfTeams];
        for (String winner : listOfWinnerTeam) {
            if (winner.equalsIgnoreCase(Teams.CSK.name())) {
                countWinningPoint[Teams.CSK.ordinal()] += 1;
                max = max < countWinningPoint[Teams.CSK.ordinal()] ? countWinningPoint[Teams.CSK.ordinal()] : max;
                winnerOfSeries = Teams.CSK.name();
            } else if (winner.equalsIgnoreCase(Teams.KKR.name())) {
                countWinningPoint[Teams.KKR.ordinal()] += 1;
                max = max < countWinningPoint[Teams.KKR.ordinal()] ? countWinningPoint[Teams.KKR.ordinal()] : max;
                winnerOfSeries = Teams.KKR.name();
            } else if (winner.equalsIgnoreCase(Teams.MI.name())) {
                countWinningPoint[Teams.MI.ordinal()] += 1;
                max = max < countWinningPoint[Teams.MI.ordinal()] ? countWinningPoint[Teams.MI.ordinal()] : max;
                winnerOfSeries = Teams.MI.name();
            } else if (winner.equalsIgnoreCase(Teams.RBC.name())) {
                countWinningPoint[Teams.RBC.ordinal()] += 1;
                max = max < countWinningPoint[Teams.RBC.ordinal()] ? countWinningPoint[Teams.RBC.ordinal()] : max;
                winnerOfSeries = Teams.RBC.name();
            } else{}
        }
    }

    public void displayAllMatchScoreBoard()
    {
        System.out.println("========================All Matches ScoreBoard==========================");
        int matchNumber = 1;
        for (ScoreBoard scoreBoard : listOfScoreBoard) {
            System.out.println("==============Match " + matchNumber + " ScoreBoard===============");
            System.out.println();
            System.out.println("Team_Name Player_Name Jersey_Number Player_Type Total_Run Total_Wickets Total_Balls_Played");
            scoreBoard.displayMatchBoard(scoreBoard);
            System.out.println();
            matchNumber++;
        }
    }*/
        }
    }
}
