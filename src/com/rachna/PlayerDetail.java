package com.rachna;

public class PlayerDetail {

    public enum PlayerType
    {
        BATSMAN,BOWLER;
    }

    private String playerName;
    private int tshirtNumber;
    private Teams teamName;
    private  PlayerType playerType= PlayerType.BATSMAN;
    private int totalBallsPlayed=0;
    private  int totalWicketsTaken=0;
    private int totalRunsScored=0;

    PlayerDetail(int tshirtNumber,String playerName,Teams teamName)
    {
        this.tshirtNumber=tshirtNumber;
        this.playerName=playerName;
        this.teamName=teamName;
    }

    public int getTshirtNumber() {
        return tshirtNumber;
    }

    public void setTshirtNumber(int tshirtNumber) {
        this.tshirtNumber = tshirtNumber;
    }

    public int getTotalRunsScored() {
        return totalRunsScored;
    }

    public PlayerDetail setTotalRunsScored(int totalRunsScored) {
        this.totalRunsScored = totalRunsScored;
        return this;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerDetail setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public Teams getTeamName() {
        return teamName;
    }

    public PlayerDetail setTeamName(Teams teamName) {
        this.teamName = teamName;
        return this;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public PlayerDetail setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
        return this;
    }

    public int getTotalBallsPlayed() {
        return totalBallsPlayed;
    }

    public PlayerDetail setTotalBallsPlayed(int totalBallsPlayed) {
        this.totalBallsPlayed = totalBallsPlayed;
        return this;
    }

    public int getTotalWicketsTaken() {
        return totalWicketsTaken;
    }

    public PlayerDetail setTotalWicketsTaken(int totalWicketsTaken) {
        this.totalWicketsTaken = totalWicketsTaken;
        return this;
    }

    public PlayerDetail newPlayer()
    {
        return this;
    }

    public  String getUniquePlayerKey(PlayerDetail player)
    {
        return player.getTeamName().name()+"_"+player.getPlayerName()+"_"+getTshirtNumber();
    }
}

