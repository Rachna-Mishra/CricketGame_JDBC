package com.rachna;

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
                System.out.println("Enter number of Matches do you want to organise");
                int numberOfMatches=sc.nextInt();
                System.out.println("Enter number of overs for this match");
                int numberOfOvers = sc.nextInt();
                System.out.println("Number of teams participating for this match range upto 4");
                int numberOfTeams = sc.nextInt();
                GlobalObjects.matchController.organizeMatches(seriesCount,numberOfMatches,numberOfOvers,numberOfTeams);
                seriesCount++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

