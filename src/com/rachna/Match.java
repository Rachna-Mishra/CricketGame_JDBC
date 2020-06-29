package com.rachna;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

class Match
{
    private static Match matchObject;
    private Match(){}
    public static Match getInstance()
    {
        if(matchObject==null)
        {
            matchObject=new Match();
        }
        return matchObject;
    }
    private PreparedStatement ps=null;
    private ResultSet resultSet=null;
    private int score=0;
    private int sequenceNo=0;
    private  int totalWicketsOfTeam;
    private  int currentPlayerOnStrike;
    private  int currentPlayerOnPitch;
    private  int totalScoreOfPLayerOnStrike=0;
    private  int totalScoreOfPlayerOnPitch=0;
    private  int totalBallsByPlayerOnStrike=0;
    private  int totalBallsByPlayerOnPitch=0;

    public int calculateScore(int teamId) throws Exception{

        initializeValues(teamId);
        List<Integer> playerList = GlobalObjects.playerObject.getPlayerList(teamId);
        sequenceNo=0;
        currentPlayerOnStrike = playerList.get(sequenceNo++);
        currentPlayerOnPitch = playerList.get(sequenceNo);
        for (int ball = 1; ball <= GlobalObjects.matchController.numberOfOvers * 6; ball++)
        {
            Random r = GeneralUtils.getRandomFunction();
            int run = r.nextInt(8);
            if(run!=7)
            GlobalObjects.scoreBoard.updateScoreBoard(teamId,ball,run,score,currentPlayerOnPitch,currentPlayerOnStrike);
            else
                GlobalObjects.scoreBoard.updateScoreBoard(teamId,ball,-1,score,currentPlayerOnPitch,currentPlayerOnStrike);
            if (run!=1 && run !=3 && run!=7) {
                System.out.println(GlobalObjects.playerObject.getPlayerName(currentPlayerOnStrike) + " Player is on Strike");
                totalScoreOfPLayerOnStrike+=run;
                totalBallsByPlayerOnStrike+=1;
                score += run;
            }
            else if(run==1 || run ==3){
                totalScoreOfPLayerOnStrike+=run;
                totalBallsByPlayerOnStrike+=1;
                score += run;
                swapPlayersPosition();
                System.out.println(GlobalObjects.playerObject.getPlayerName(currentPlayerOnStrike) + " Player is on Strike");
            }
            else{
                System.out.println("!!!!!!!!!!!!--------------Wicket---------------!!!!!!!!!!! ");
                if(run==7)
                {
                    // System.out.println(currentPlayerOnStrike+" total score of Striker "+totalScoreOfPLayerOnStrike+" "+totalBallsByPlayerOnStrike);
                    GlobalObjects.playerObject.updatePlayerRecord(teamId,currentPlayerOnStrike,totalScoreOfPLayerOnStrike,0,totalBallsByPlayerOnStrike);
                    totalScoreOfPLayerOnStrike=0;
                    totalBallsByPlayerOnStrike=0;
                    sequenceNo++;
                }
                totalWicketsOfTeam++;
                if (sequenceNo == playerList.size()) {
                    System.out.println("Inning Over : No remaining players are left in the team");
                    break;
                }
                if (sequenceNo<playerList.size())
                {
                    currentPlayerOnStrike = playerList.get(sequenceNo);
                }
            }
            System.out.println("Current Score : "+score);
        }
        GlobalObjects.playerObject.updatePlayerRecord(teamId,currentPlayerOnStrike,totalScoreOfPLayerOnStrike,0,totalBallsByPlayerOnStrike);
        GlobalObjects.playerObject.updatePlayerRecord(teamId,currentPlayerOnPitch,totalScoreOfPlayerOnPitch,0,totalBallsByPlayerOnPitch);
        return score;
    }

    void initializeValues(int teamPlaying)
    {
        score = 0;
        sequenceNo = 0;
        totalWicketsOfTeam = 0;
        totalScoreOfPLayerOnStrike=0;
        totalScoreOfPlayerOnPitch=0;
        totalBallsByPlayerOnStrike=0;
        totalBallsByPlayerOnPitch=0;
    }

    public int getTotalWickets(int teamId)
    {
        return totalWicketsOfTeam;
    }

    public void swapPlayersPosition() {
        int temp = currentPlayerOnStrike;
        currentPlayerOnStrike=currentPlayerOnPitch;
        currentPlayerOnPitch= temp;

         temp=totalScoreOfPLayerOnStrike;
        totalScoreOfPLayerOnStrike=totalScoreOfPlayerOnPitch;
        totalScoreOfPlayerOnPitch=temp;

        temp=totalBallsByPlayerOnStrike;
        totalBallsByPlayerOnStrike=totalBallsByPlayerOnPitch;
        totalBallsByPlayerOnPitch=temp;
    }

    public void insertMatchRecord(int BattingTeam,int BowlingTeam,int team1Score,int team1Wickets,int team2Score,int team2Wickets,int winnerTeamOfMatch) throws SQLException {
        ps=ConnectionUtil.connection.prepareStatement("insert into MatchRecord values (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1,GlobalObjects.matchController.seriesId);
        ps.setInt(2,GlobalObjects.matchController.matchId);
        ps.setInt(3,BattingTeam);
        ps.setInt(4,BowlingTeam);
        ps.setInt(5,team1Score);
        ps.setInt(6,team1Wickets);
        ps.setInt(7,team2Score);
        ps.setInt(8,team2Wickets);
        ps.setInt(9,winnerTeamOfMatch);
        ps.executeUpdate();
//        System.out.println("Updated MatchRecord");
    }

    public void viewMatchRecord(int seriesId,int matchId) throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("select * from MatchRecord where SeriesId=? and MatchId=?");
        ps.setInt(1,seriesId);
        ps.setInt(2,matchId);
        resultSet=ps.executeQuery();
        while (resultSet.next())
        {
            System.out.println(" Team1 Score : "+resultSet.getInt(4)+
                                "\n Team1 Wickets : "+resultSet.getInt(5)+
                                "\n Team2 Score : "+resultSet.getInt(6)+
                                "\n Team2 Wickets : "+resultSet.getInt(7)+
                    "\n Winner Of Match :"+GlobalObjects.teamObject.getTeamName(resultSet.getInt(8)));
        }
    }

    public String getWinnerOfTheMatch(int seriesId,int matchId)throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("select WinnerTeamId from MatchRecord where SeriesId=? and MatchId=?");
        ps.setInt(1,seriesId);
        ps.setInt(2,matchId);
        resultSet=ps.executeQuery();
        resultSet.next();
        if(resultSet.getInt("WinnerTeamId")==0)
            return "--- Match Tie -----";
        else
        return GlobalObjects.teamObject.getTeamName(resultSet.getInt("WinnerTeamId"));
    }
}