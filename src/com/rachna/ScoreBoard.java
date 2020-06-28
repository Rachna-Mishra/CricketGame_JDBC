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
        System.out.println("Updated ScoreBoardd");
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
        if(inning==1)
            ps=ConnectionUtil.connection.prepareStatement("select BattingTeam from MatchRecord where SeriesId=?,MatchId=?");
        else
            ps=ConnectionUtil.connection.prepareStatement("select BowlingTeam from MatchRecord where SeriesId=?,MatchId=?");
        ps.setInt(1,seriesId);
        ps.setInt(2,matchId);
        resultSet=ps.executeQuery();
        resultSet.next();
        int teamId=resultSet.getInt(1);

        ps=ConnectionUtil.connection.prepareStatement("select * from ScoreBoard where SeriesId=?,MatchId=?,TeamId=?,BallNumber=?");
        ps.setInt(1,seriesId);
        ps.setInt(2,matchId);
        ps.setInt(3,teamId);
        ps.setInt(4,ball);
        resultSet=ps.executeQuery();
        while (resultSet.next())
        {
            System.out.println("BallNumber : "+resultSet.getInt(4)+
                                "Run : "+resultSet.getInt(5)+
                                "CurrentScore: "+resultSet.getInt(6)+
                                "Player On Strike : "+GlobalObjects.playerObject.getPlayerName(resultSet.getInt(8))+
                                "Player On Pitch : "+GlobalObjects.playerObject.getPlayerName(resultSet.getInt(7)));
        }
    }
}