package com.rachna;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Player {
    private static Player playerObject;
    private PreparedStatement ps;

    private Player() {
    }

    public static Player getInstance() {
        if (playerObject == null) {
            playerObject = new Player();
        }
        return playerObject;
    }

    public List<Integer> getPlayerList(int teamId) throws Exception
    {
        List<Integer> playerList=new ArrayList<>();
        ResultSet resultSet=ConnectionUtil.statement.executeQuery("select PlayerId from PlayerDetail where TeamId="+teamId);
        while(resultSet.next())
            playerList.add(resultSet.getInt(1));
//        System.out.println(playerList);
        return playerList;
    }

    public String getPlayerName(int playerId) throws Exception{
        String playerName=null;
        ResultSet resultSet=ConnectionUtil.statement.executeQuery("select PlayerName from PlayerDetail where PlayerId="+playerId);
        while(resultSet.next())
            playerName=resultSet.getString(1);
        return playerName;
    }

    public ResultSet getPlayerDetail(int playerId)throws Exception
    {
        ResultSet resultSet=ConnectionUtil.statement.executeQuery("select * from PlayerDetail where PlayerId="+playerId);
        return resultSet;
    }

    public void updatePlayerDetail(int playerId,int teamId) throws Exception
    {
        int teamSize=getPlayerList(teamId).size();
        if(teamSize>=5)
        {
            System.out.println("Limit Exceeded !! We can't add new player");
            System.out.println("Do you want to swap players from Teams!! If Yes type Y otherwise N ");
            Scanner sc=new Scanner(System.in);
            String choice=sc.next();
            if(choice.equalsIgnoreCase("Yes") || choice.equalsIgnoreCase("y"))
            {
                System.out.println("---- Enter Player 1 Detail (TeamId , PlayerId) --------");
                int team1Id=sc.nextInt();
                int player1Id=sc.nextInt();
                System.out.println("---- Enter Player 2 Detail (TeamId , PlayerId) --------");
                int team2Id=sc.nextInt();
                int player2Id=sc.nextInt();
                swapPlayersInTeam(team1Id,player1Id,team2Id,player2Id);
            }
            else
                System.out.println("!!!!! Okay Continue !!!!!!!");
        }
        else {
            ps = ConnectionUtil.connection.prepareStatement("update PlayerDetail set TeamId =? where PlayerId=?");
            ps.setInt(1, teamId);
            ps.setInt(2, playerId);
            ps.executeUpdate();
        }
    }

    private void swapPlayersInTeam(int team1Id, int player1Id, int team2Id, int player2Id) throws Exception
    {
        ps = ConnectionUtil.connection.prepareStatement("update PlayerDetail set TeamId =? where PlayerId=?");
        ps.setInt(1, team2Id);
        ps.setInt(2, player1Id);
        ps.executeUpdate();
        ps = ConnectionUtil.connection.prepareStatement("update PlayerDetail set TeamId =? where PlayerId=?");
        ps.setInt(1, team1Id);
        ps.setInt(2, player2Id);
        ps.executeUpdate();
        System.out.println("PLAYER SWAPED IN TEAM");
    }

    public void addPlayerRecord(int teamId) throws Exception
    {
        ResultSet resultSet=ConnectionUtil.statement.executeQuery("select PlayerId from PlayerDetail where TeamId ="+teamId);
        while(resultSet.next())
        {
            ps=ConnectionUtil.connection.prepareStatement("insert into PlayerRecord (SeriesId,MatchId,TeamId,PlayerId) values(?,?,?,?)");
            ps.setInt(1,GlobalObjects.matchController.seriesId);
            ps.setInt(2,GlobalObjects.matchController.matchId);
            ps.setInt(3,teamId);
            ps.setInt(4,resultSet.getInt(1));
            ps.execute();
        }
//        System.out.println("Updated PlayerRecord");
    }

    public void updatePlayerRecord(int TeamId,int playerId,int TotalRuns,int TotalWickets,int TotalBallsPlayed) throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("update PlayerRecord set TotalRuns=?,TotalWickets=?,TotalBallsPlayed=? where PlayerId = "+playerId+" and TeamId= "+TeamId+" and MatchId= "+GlobalObjects.matchController.matchId+" and SeriesId = "+GlobalObjects.matchController.seriesId);
        ps.setInt(1,TotalRuns);
        ps.setInt(2,TotalWickets);
        ps.setInt(3,TotalBallsPlayed);
        ps.execute();
    }
}