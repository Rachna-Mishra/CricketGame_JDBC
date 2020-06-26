package com.rachna;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class ImmutableMap {

    private static Set<String> teamList;
    public static Set<String> getTeamList() throws Exception
    {
        teamList=new HashSet<>();
        ResultSet resultSet=ConnectionUtil.statement.executeQuery("select TeamCode from TeamList");
        while(resultSet.next())
        {
            teamList.add(resultSet.getString(1));
        }
        return teamList;
    }

    public static void createTeamDB(String teamCode) throws Exception
    {
        for(String team:teamList)
        {
            if(team.equalsIgnoreCase(teamCode))
                ConnectionUtil.statement.executeQuery("create table " +teamCode+" TeamCode varchar(5),JerseyNumber int,PlayerName varchar(20),PlayerType varchar(10)");
        }
    }

    public static void insertTeamDetail(String teamCode,int JerseyNumber,String PlayerName,String PLayerType) throws Exception
    {
        ConnectionUtil.statement.executeQuery("insert into "+teamCode+" values("+teamCode+","+JerseyNumber+","+PlayerName+","+PLayerType+")");
    }

   public static final Map<Integer, String> teamCSK = new LinkedHashMap<Integer, String>() {
        {
            put(1,"dhoni");
            put(2,"virat");
            put(3,"monu");
            put(6,"jadeja");
            put(10,"parthiv");
        }
    };

    public static final Map<Integer, String> teamKKR = new LinkedHashMap<Integer, String>()
    {
        {
            put(1,"saurabh");
            put(2,"sachin");
            put(6,"saurabh");
            put(7,"haribhajan");
            put(13,"John");
        }
    };
    public static final Map<Integer, String> teamMI = new LinkedHashMap<Integer, String>()
    {
        {
            put(1,"dhoni");
            put(2,"virat");
            put(5,"Lynn");
            put(210,"Aman");
            put(22,"virat");
        }
    };
    public static final Map<Integer, String> teamRBC = new LinkedHashMap<Integer, String>()
    {
        {
            put(1,"dhoni");
            put(3,"virat");
            put(10,"dhoni");
            put(111,"virat");
            put(16,"virat");
        }
    };
}
