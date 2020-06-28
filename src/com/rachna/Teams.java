package com.rachna;

import java.util.*;

public enum Teams {
    //CSK(Arrays.asList("DHONI", "RAINA", "VIJAY", "MONU", "JADEJA")),
    //KKR(Arrays.asList("DINESH", "ROBIN", "LYNN", "SUNIL", "PIYUSH")),
    //MI(Arrays.asList("ROHIT", "SURYA", "RAHUL", "ADITYA", "ADAM")),
    //RBC(Arrays.asList("VIRAT", "PARTHIV", "UMESH", "PAWAN", "SIRAJ"));

    CSK(ImmutableMap.teamCSK),
   KKR(ImmutableMap.teamKKR),
   MI(ImmutableMap.teamMI),
   RBC(ImmutableMap.teamRBC);

    private  Map<Integer,String > playerList;

    private Teams(Map<Integer, String> playerList)
    {
        this.playerList=playerList;
    }
    public Map<Integer, String> getPlayerList()
    {
        return this.playerList;
    }

    public static Map<Integer, String> retrivePlayerList(Teams teamPlaying) {
        Teams[] teams = Teams.values();
        for (Teams team : teams) {
            if (team.equals(teamPlaying))
                return team.getPlayerList();
        }
        return null;
    }

    public static List<String> fetchPlayerList(Teams teamPlaying)
    {
        Teams[] teams = Teams.values();
        for (Teams team : teams) {
            if(team.equals(teamPlaying))
            {
                ArrayList<String> playerList=new ArrayList<>(team.getPlayerList().values());
                return playerList;
            }
        }
        return null;
    }

}
