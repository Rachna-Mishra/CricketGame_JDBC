package com.rachna;

import java.util.Scanner;

public class CricketGame
{
    public static void main(String[] args)
    {
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter number of Matches do you want to organise");
            int numberOfMatches=sc.nextInt();
            MatchController matchController=new MatchController(numberOfMatches);
            matchController.organizeMatches();
        }
        catch (Exception e)
        {}
    }
}
