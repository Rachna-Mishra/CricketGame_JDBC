package com.rachna;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchController
{
    private int team1Score;
    private int team2Score;
    private int wickets1;
    private int wickets2;
    private String winnerOfSeries;
    private List<ScoreBoard> listOfScoreBoard;
    private List<String> listOfWinnerTeam;
    private Teams selectedTeamForBatting;
    private Teams selectedTeamForBowling;
    private static String result;
    private int numberOfOvers;
    private int numberOfTeams;
    private int numberOfMatches;
    private List<Teams> selectedTeams;
    private int winningPointOfTeam1;
    private int winningPointOfTeam2;

    MatchController(int numberOfMatches,int numberOfOvers,int numberOfTeams)
    {
        this.numberOfTeams=numberOfTeams;
        this.numberOfOvers=numberOfOvers;
        this.numberOfMatches=numberOfMatches;
    }

    public void organizeMatches()
    {
        winningPointOfTeam1=0;
        winningPointOfTeam2=0;
        listOfScoreBoard = new ArrayList<>();
        listOfWinnerTeam = new ArrayList<>();

        // =====================Selection Of 2 Teams from Number Of Teams ======================
        selectedTeams = GeneralUtils.getSelectedTeams(numberOfTeams);
        System.out.println("-------------Match Started between Teams " + " " + selectedTeams.get(0) + " and " + selectedTeams.get(1) + "-----------------");
        int winnerPoint=numberOfMatches/2+1;
        int i=0;
        while (numberOfMatches-- > 0)
        {
            i++;
            System.out.println("=============Match "+i+" started================");
            ScoreBoard scoreBoard = new ScoreBoard();
            listOfScoreBoard.add(scoreBoard);
            Match match = new Match(numberOfOvers, scoreBoard);

            //------------------Match Tossed between selected Teams-------------------
            Teams tossWonTeamCode = (GeneralUtils.getWinner() == 0) ? selectedTeams.get(0) : selectedTeams.get(1);
            selectedTeamForBatting = selectedTeams.get(0);
            selectedTeamForBowling = selectedTeams.get(1);
            System.out.println("------------Team " + tossWonTeamCode + " Won the Toss------------");

            String batOrBowl = (GeneralUtils.getWinner() == 0) ? Constants.Batting : Constants.Bowling;
            System.out.println("------------Team " + tossWonTeamCode + " Opted for " + batOrBowl + "---------------");

            if (tossWonTeamCode == selectedTeams.get(1))
            {
                if (Constants.Batting.equalsIgnoreCase(batOrBowl)) {
                    selectedTeamForBatting = selectedTeams.get(1);
                    selectedTeamForBowling = selectedTeams.get(0);
                } else {
                    selectedTeamForBatting = selectedTeams.get(0);
                    selectedTeamForBowling = selectedTeams.get(1);
                }
            }
            else{
                if (Constants.Bowling.equalsIgnoreCase(batOrBowl)) {
                    selectedTeamForBatting = selectedTeams.get(1);
                    selectedTeamForBowling = selectedTeams.get(0);
                }
            }

            /* -------Adding Players detail Of Playing Teams in scoreBoard --------- */
            addPlayersDetail(scoreBoard, selectedTeamForBatting);
            addPlayersDetail(scoreBoard, selectedTeamForBowling);

            //----------------------------Match Started-----------------
            String winnerOfMatch=startMatch(match, selectedTeamForBatting, selectedTeamForBowling);
            if(winnerOfMatch==null){}
            else if(winnerOfMatch.equalsIgnoreCase(selectedTeams.get(0).name()))
                winningPointOfTeam1++;
            else if(winnerOfMatch.equalsIgnoreCase(selectedTeams.get(1).name()))
                winningPointOfTeam2++;

            //------------------Showing ScoreBoard Details------------------------
            scoreBoard.displayScoreBoard(selectedTeamForBatting, selectedTeamForBowling);
            System.out.println();
            if(winningPointOfTeam1==winnerPoint)
            {
                winnerOfSeries=selectedTeams.get(0).name();
                break;
            }
            if(winningPointOfTeam2==winnerPoint)
            {
                winnerOfSeries=selectedTeams.get(1).name();
                break;
            }
        }
        getWinnerOfSeries();
        displayAllMatchScoreBoard();
        System.out.println("Winner of Series : "+winnerOfSeries);
    }

    public String startMatch(Match match, Teams team1, Teams team2)
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
        String  winnerTeam = null;
        if (team1Score > team2Score)
        {
            result = "team1";
            winnerTeam=team1.name();
        }
        else if (team1Score < team2Score)
        {
            result = "team2";
            winnerTeam=team2.name();
        }
        else
            result = "tie";
        printScore(result);
        return winnerTeam;
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
                    PlayerDetail player=new PlayerDetail(jerseyNumber,playerName,team);
                    String playerKey = player.getUniquePlayerKey(player);
                    scoreBoard.playerDetailsMap.put(playerKey, player);
                }
              //  scoreBoard.displayTeamScore(team);
                break;
            }
        }
    }

    public void getWinnerOfSeries()
    {
        if(winningPointOfTeam1>winningPointOfTeam2)
            winnerOfSeries=selectedTeams.get(0).name();
        else if (winningPointOfTeam2>winningPointOfTeam1)
            winnerOfSeries=selectedTeams.get(1).name();
        else
            winnerOfSeries="Series Draw";
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
    }
}
