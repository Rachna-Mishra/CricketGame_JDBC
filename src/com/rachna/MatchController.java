package com.rachna;

import java.util.List;

public class MatchController {

    private static MatchController matchController;
    private MatchController(){}
    public static MatchController getInstance()
    {
        if(matchController==null)
        {
            matchController=new MatchController();
        }
        return matchController;
    }

    public int numberOfOvers;
    public int numberOfTeams;
    public int numberOfMatches;
    public int seriesId=0;
    public int matchId=0;
    private int winnerOfSeries;
    private int team1Score;
    private int team2Score;
    private int wickets1;
    private int wickets2;
    private List<Integer> selectedTeams;
    private int winningPointOfTeam1;
    private int winningPointOfTeam2;
    private int teamIdOfBowlingTeam;
    private int teamIdOfBattingTeam;
    private String battingTeamName;
    private String bowlingTeamName;

    public void organizeMatches(int seriesId, int numberOfMatches, int numberOfOvers, int numberOfTeams) throws Exception {
        this.seriesId=seriesId;
        this.numberOfMatches=numberOfMatches;
        this.numberOfOvers=numberOfOvers;
        this.numberOfTeams=numberOfTeams;
        winningPointOfTeam1 = 0;
        winningPointOfTeam2 = 0;

        // =====================Selection Of 2 Teams from Number Of Teams ======================
        selectedTeams = GeneralUtils.getSelectedTeams(numberOfTeams);
        battingTeamName=GlobalObjects.teamObject.getTeamName(selectedTeams.get(0));
        bowlingTeamName=GlobalObjects.teamObject.getTeamName(selectedTeams.get(1));
        System.out.println("-------------Match Started between Teams " + " " + battingTeamName + " and " + bowlingTeamName + "-----------------");
        int winnerPoint=numberOfMatches/2+1;
        while (++matchId<=numberOfMatches)
        {
            System.out.println(GlobalObjects.matchController.seriesId+"  "+GlobalObjects.matchController.matchId);
            System.out.println("=============Match "+matchId+" started================");

            //------------------Match Tossed between selected Teams-------------------
            int tossWonTeamCode = (GeneralUtils.getWinner() == 0) ? selectedTeams.get(0) : selectedTeams.get(1);
            battingTeamName = GlobalObjects.teamObject.getTeamName(selectedTeams.get(0));
            bowlingTeamName = GlobalObjects.teamObject.getTeamName(selectedTeams.get(1));
            System.out.println("------------Team " + GlobalObjects.teamObject.getTeamName(tossWonTeamCode) + " Won the Toss------------");

            String batOrBowl = (GeneralUtils.getWinner() == 0) ? Constants.Batting : Constants.Bowling;
            System.out.println("------------Team " + GlobalObjects.teamObject.getTeamName(tossWonTeamCode) + " Opted for " + batOrBowl + "---------------");

            if (tossWonTeamCode == selectedTeams.get(1))
            {
                if (Constants.Batting.equalsIgnoreCase(batOrBowl)) {
                    teamIdOfBattingTeam = selectedTeams.get(1);
                    teamIdOfBowlingTeam = selectedTeams.get(0);
                } else {
                    teamIdOfBattingTeam = selectedTeams.get(0);
                    teamIdOfBowlingTeam = selectedTeams.get(1);
                }
            }
            else{
                if (Constants.Bowling.equalsIgnoreCase(batOrBowl)) {
                    teamIdOfBattingTeam = selectedTeams.get(1);
                    teamIdOfBowlingTeam = selectedTeams.get(0);
                }
            }

            /* -------Adding Players detail Of Playing Teams in scoreBoard --------- */
          GlobalObjects.playerObject.addPlayerRecord(teamIdOfBattingTeam);
          GlobalObjects.playerObject.addPlayerRecord(teamIdOfBowlingTeam);

           //----------------------------Match Started-----------------
            startMatch(teamIdOfBattingTeam, teamIdOfBowlingTeam);
            System.out.println(GlobalObjects.matchObject.getWinnerOfTheMatch(seriesId,matchId));
            if(winningPointOfTeam1==winnerPoint)
            {
                winnerOfSeries=teamIdOfBattingTeam;
                break;
            }
            if(winningPointOfTeam2==winnerPoint)
            {
                winnerOfSeries=teamIdOfBowlingTeam;
                break;
            }
        }
        GlobalObjects.seriesObject.insertSeriesRecord(numberOfMatches,selectedTeams.get(0),selectedTeams.get(1),winnerOfSeries);
       System.out.println(GlobalObjects.seriesObject.getWinnerOfSeries(seriesId));
    }

    public void startMatch(int team1, int team2) throws Exception
    {
        System.out.println("----------Match Started---------------");
        System.out.println("---------Team " + GlobalObjects.teamObject.getTeamName(team1) + " Ready for Batting----------------");
        team1Score = GlobalObjects.matchObject.calculateScore(team1);
        wickets1 = GlobalObjects.matchObject.getTotalWickets(team1);
        System.out.println("Total Wickets Of Team : " + wickets1);
        System.out.println();
        System.out.println("---------Now Team " + team2 + " Ready for Batting----------------");
        team2Score = GlobalObjects.matchObject.calculateScore(team2);
        wickets2 = GlobalObjects.matchObject.getTotalWickets(team2);
        System.out.println("Total Wickets Of Team : " + wickets2);
        System.out.println();
        System.out.println();
        System.out.println("Team1 Score : " + team1Score + " " + "Total Wickets : " + wickets1);
        System.out.println("Team2 Score : " + team2Score + " " + "Total Wickets : " + wickets2);
        int  winnerTeam = 0;
        if (team1Score > team2Score)
        {
            winnerTeam=team1;
            System.out.println("---------------------Team 1 Won--------------------");
        }
        else if (team1Score < team2Score)
        {
            winnerTeam=team2;
            System.out.println("---------------------Team 2 Won--------------------");
        }
        else
            System.out.println("---------------------Match Tie--------------------");
        GlobalObjects.matchObject.insertMatchRecord(team1,team2,team1Score,wickets1,team2Score,wickets2,winnerTeam);
    }
}
