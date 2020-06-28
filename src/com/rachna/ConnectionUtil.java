package com.rachna;

import java.sql.*;

public class ConnectionUtil {

        public static Connection connection;
        public static Statement statement;

        public static void getConnection()
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CricketGame",System.getenv("userId"),System.getenv("password"));
                statement=connection.createStatement();
            }
            catch (ClassNotFoundException | SQLException ex)
            {
                System.out.println("Unable to load the class.");
                System.exit(-1);
            }
        }
    }
