package com.rachna;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Teams
{
    private PreparedStatement ps=null;
    private ResultSet resultSet=null;
    private static Teams teamObject;
    private Teams(){}
    public static Teams getInstance()
    {
        if(teamObject==null)
        {
            teamObject=new Teams();
        }
        return teamObject;
    }

    public String getTeamName(int teamId) throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("select TeamName from TeamDetails where TeamId = ?");
        ps.setInt(1,teamId);
        resultSet=ps.executeQuery();
        resultSet.next();
        return resultSet.getString("TeamName");
    }

    public void insertNewTeamData(int teamId,String teamName)throws Exception
    {
        ps=ConnectionUtil.connection.prepareStatement("insert into TeamDetails values (?,?)");
        ps.setInt(1,teamId);
        ps.setString(2,teamName);
        ps.execute();
    }
}