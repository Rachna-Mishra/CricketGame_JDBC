package com.rachna;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Series {

    private PreparedStatement ps=null;
    private ResultSet resultSet=null;

    private static Series seriesObject;
    private Series(){}
    public static Series getInstance()
    {
        if(seriesObject==null)
        {
            seriesObject=new Series();
        }
        return seriesObject;
    }


    public void insertSeriesRecord(int seriesId,int numberOfMatches,int team1,int team2,int winnerOfSeries) throws SQLException {
        ps=ConnectionUtil.connection.prepareStatement("insert into SeriesRecord values (?,?,?,?,?)");
        ps.setInt(1,seriesId);
        ps.setInt(2,numberOfMatches);
        ps.setInt(3,team1);
        ps.setInt(4,team2);
        ps.setInt(5,winnerOfSeries);
        ps.execute();
//        System.out.println("Series Updated");
    }

    public void viewSeriesRecord(int seriesId) throws Exception {
        ps=ConnectionUtil.connection.prepareStatement("select * from SeriesRecord where SeriesId=?");
        ps.setInt(1,seriesId);
        resultSet=ps.executeQuery();
        while (resultSet.next())
        {
            System.out.println(" Series Id : " +seriesId+
                                "\n Number Of Matches : "+resultSet.getInt(2)+
                                "\n Team 1 :"+GlobalObjects.teamObject.getTeamName(resultSet.getInt(3))+
                                "\n Team 2 :"+GlobalObjects.teamObject.getTeamName(resultSet.getInt(4))+
                            "\n Winner Of Series :"+GlobalObjects.teamObject.getTeamName(resultSet.getInt(4)));
        }
    }

    public String getWinnerOfSeries(int seriesId)throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("select WinnerSeriesTeamId from SeriesRecord where SeriesId=?");
        ps.setInt(1,seriesId);
        resultSet=ps.executeQuery();
        resultSet.next();
        if(resultSet.getInt("WinnerSeriesTeamId")==0)
            return "--Series Tie--";
        else
            return GlobalObjects.teamObject.getTeamName(resultSet.getInt("WinnerSeriesTeamId"));
    }
}
