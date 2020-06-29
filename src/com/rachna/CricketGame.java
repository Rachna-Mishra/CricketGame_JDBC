package com.rachna;

import sun.jvm.hotspot.memory.CardGeneration;

import java.util.Scanner;


public class CricketGame
{

    public static void main(String[] args)
    {
        try{
            ConnectionUtil.getConnection();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter number of Series do you want to organise");
            int numberOfSeries=sc.nextInt();
            int seriesCount=1;
            while(seriesCount<=numberOfSeries)
            {
                System.out.println("==============Series 1 Started===================");
                System.out.println("Enter number of Matches do you want to organise");
                int numberOfMatches=sc.nextInt();
                System.out.println("Enter number of overs for this match");
                int numberOfOvers = sc.nextInt();
                System.out.println("Number of teams participating for this match range upto 4");
                int numberOfTeams = sc.nextInt();
                GlobalObjects.matchController.organizeMatches(seriesCount,numberOfMatches,numberOfOvers,numberOfTeams);
                System.out.println("=============Winner Of Series : "+GlobalObjects.seriesObject.getWinnerOfSeries(seriesCount));
                seriesCount++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    // ------------Player Traded from 1 Team to other team--------------
//    public static void main(String[] args)throws Exception
//    {
//        ConnectionUtil.getConnection();
//        int teamId=Integer.parseInt(args[0]);
//        int playerId =Integer.parseInt(args[1]);
//        GlobalObjects.playerObject.updatePlayerDetail(playerId,teamId);
//    }

    // ------------------Winner Of Series----------------------------
//    public static void main(String[] args) throws Exception
//    {
//        ConnectionUtil.getConnection();
//        int seriesId=Integer.parseInt(args[0]);
//        System.out.println(GlobalObjects.seriesObject.getWinnerOfSeries(seriesId));
//    }

    //--------------------Winner of Particular Match-------------------
//   public static void main(String[] args)throws Exception
//    {
//            ConnectionUtil.getConnection();
//            int seriesId=Integer.parseInt(args[0]);
//            int matchId =Integer.parseInt(args[1]);
//            System.out.println(GlobalObjects.matchObject.getWinnerOfTheMatch(seriesId,matchId));
//  }

    // ---------------Get  ScoreBoard at PARTICULAR BALL OF INNING----------
//   public static void main(String[] args)throws Exception
//  {
//     ConnectionUtil.getConnection();
//      int seriesId=Integer.parseInt(args[0]);
//      int matchId=Integer.parseInt(args[1]);
//      int inningNumber=Integer.parseInt(args[2]);
//      int ballNumber=Integer.parseInt(args[3]);
//        GlobalObjects.scoreBoard.getScoreBoardForInningData(seriesId,matchId,inningNumber,ballNumber);
//  }
}

