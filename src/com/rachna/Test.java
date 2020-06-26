package com.rachna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Test
{
    public static void main(String[] args)
    {
        try{
            ConnectionUtil.getConnection();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter number of Matches do you want to organise");
            int numberOfMatches=sc.nextInt();
            MatchController matchController=new MatchController(numberOfMatches);
            matchController.organizeMatches();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
