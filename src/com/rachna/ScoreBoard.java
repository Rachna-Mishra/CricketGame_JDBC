package com.rachna;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ScoreBoard {
    private PreparedStatement ps = null;
    private ResultSet resultSet=null;
    private static ScoreBoard scoreBoard;

    private ScoreBoard() {
    }

    public static ScoreBoard getInstance() {
        if (scoreBoard == null) {
            scoreBoard = new ScoreBoard();
        }
        return scoreBoard;
    }

    public void updateScoreBoard(int teamId, int ball, int run, int score, int currentPlayerOnPitch, int currentPlayerOnStrike) throws Exception {
        ps = ConnectionUtil.connection.prepareStatement("insert into ScoreBoard values (?,?,?,?,?,?,?,?)");
        ps.setInt(1, GlobalObjects.matchController.seriesId);
        ps.setInt(2, GlobalObjects.matchController.matchId);
        ps.setInt(3, teamId);
        ps.setInt(4, ball);
        ps.setInt(5, run);
        ps.setInt(6, score);
        ps.setInt(7, currentPlayerOnPitch);
        ps.setInt(8, currentPlayerOnStrike);
        ps.execute();
//        System.out.println("Updated ScoreBoardd");
    }

    public int getScore(int seriesId,int matchId,int teamId,int ball )throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("select * from ScoreBoard where SeriesId=?,MatchId=?,TeamId=?,BallNumber=?");
        ps.setInt(1,seriesId);
        ps.setInt(2,matchId);
        ps.setInt(3,teamId);
        ps.setInt(4,ball);
        resultSet=ps.executeQuery();
        resultSet.next();
        return resultSet.getInt("CurrentScore");
    }

    public void getScoreBoardForInningData(int seriesId,int matchId,int inning,int ball)throws Exception
    {
        int teamId;
        if(inning==1)
        {
            ps=ConnectionUtil.connection.prepareStatement("select BattingTeam from MatchRecord where SeriesId=? and MatchId=?");
            ps.setInt(1,seriesId);
            ps.setInt(2,matchId);
            resultSet=ps.executeQuery();
            resultSet.next();
            teamId=resultSet.getInt("BattingTeam");
        }
        else
        {
            ps=ConnectionUtil.connection.prepareStatement("select BowlingTeam from MatchRecord where SeriesId=? and MatchId=?");
            ps.setInt(1,seriesId);
            ps.setInt(2,matchId);
            resultSet=ps.executeQuery();
            resultSet.next();
            teamId=resultSet.getInt("BowlingTeam");
        }
        ps=ConnectionUtil.connection.prepareStatement("select * from ScoreBoard where SeriesId=? and MatchId=? and TeamId=? and BallNumber=?");
        ps.setInt(1,seriesId);
        ps.setInt(2,matchId);
        ps.setInt(3,teamId);
        ps.setInt(4,ball);
        resultSet=ps.executeQuery();
        while (resultSet.next())
        {
            System.out.println(" SeriesId : "+resultSet.getInt(1)+
                                "\n MatchId : "+resultSet.getInt(2)+
                                "\n Inning : "+inning+
                                "\n BallNumber : "+resultSet.getInt(4)+
                                "\n Run : "+resultSet.getInt(5)+
                                "\n CurrentScore: "+resultSet.getInt(6)+
                                "\n Player On Strike : "+GlobalObjects.playerObject.getPlayerName(resultSet.getInt(8))+
                                "\n Player On Pitch : "+GlobalObjects.playerObject.getPlayerName(resultSet.getInt(7)));
        }
    }
}