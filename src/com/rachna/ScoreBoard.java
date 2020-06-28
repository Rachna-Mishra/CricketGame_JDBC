package com.rachna;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoard {

    public Map<String, PlayerDetail> playerDetailsMap = new LinkedHashMap<>();

    public void displayScoreBoard( Teams team1, Teams team2) {
        System.out.println();
        System.out.println();
        System.out.println("=================TEAM 1 SCOREBOARD================");
        System.out.println("Team_Name Player_Name Jersey_Number Player_Type Total_Run Total_Wickets Total_Balls_Played");
        displayTeamScore(team1);
        System.out.println();
        System.out.println();
        System.out.println("=================TEAM 2 SCOREBOARD================");
        System.out.println("Team_Name Player_Name Jersey_Number Player_Type Total_Run Total_Wickets Total_Balls_Played");
        displayTeamScore(team2);
    }

    public void displayTeamScore(Teams team)
    {
        Map<Integer,String> players=team.getPlayerList();
        List<Integer> jerseyNumberList=new ArrayList<>(players.keySet());
        List<String > playerNameList=new ArrayList<>(players.values());
        for (int i=0;i<jerseyNumberList.size();i++)
        {
            displayPlayerRecord(team.name()+"_"+playerNameList.get(i)+"_"+jerseyNumberList.get(i));
        }
    }

    public void displayPlayerRecord(String uniquePlayerKey)
    {
        PlayerDetail playerDetails = playerDetailsMap.get(uniquePlayerKey);
        System.out.println(playerDetails.getTeamName() + " "
                + playerDetails.getPlayerName() + " "
                +playerDetails.getTshirtNumber()+" "
                + playerDetails.getPlayerType() + " "
                + playerDetails.getTotalRunsScored() + " "
                + playerDetails.getTotalWicketsTaken() + " "
                + playerDetails.getTotalBallsPlayed());
    }

    //======================Java Beans Pattern==========================
    public void updateScoreBoard(int jerseyNumber,String playerName, Teams teamName, PlayerDetail.PlayerType playerType, int totalRunsScored, int totalWicketsTaken, int totalBallsPlayed)
    {
        String playerKey =teamName+"_"+playerName+"_"+jerseyNumber;
        if(playerDetailsMap.containsKey(playerKey)) {
            PlayerDetail player=playerDetailsMap.get(playerKey);
            player.setPlayerType(playerType);
            player.setTotalRunsScored(totalRunsScored);
            player.setTotalWicketsTaken(totalWicketsTaken);
            player.setTotalBallsPlayed(totalBallsPlayed);
        }
        else
        {
            //System.out.println("==================sbdsjfhkjdshfklsdjfkadhkghak=====Rachna=========================");
            PlayerDetail newPlayer=new PlayerDetail(jerseyNumber,playerName,teamName);
            playerDetailsMap.put(playerKey, newPlayer);
        }
    }

    public void displayMatchBoard(ScoreBoard scoreBoard)
    {
       for( Map.Entry<String, PlayerDetail> matchScore:scoreBoard.playerDetailsMap.entrySet())
       {
           PlayerDetail playerDetail=matchScore.getValue();
           System.out.println(playerDetail.getTeamName()+" "
                            +playerDetail.getPlayerName()+" "
                            +playerDetail.getTshirtNumber()+" "
                            +playerDetail.getPlayerType()+" "
                            +playerDetail.getTotalRunsScored()+" "
                            +playerDetail.getTotalWicketsTaken()+" "
                            +playerDetail.getTotalBallsPlayed());
       }
    }
}
