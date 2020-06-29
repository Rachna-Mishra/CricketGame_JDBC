package com.rachna;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneralUtils {

    private GeneralUtils() {
    }

    private final static Random random = new Random();

    public static Random getRandomFunction() {
        return random;
    }

    //=================After Tossing fetching the Winner=============
    public static int getWinner() {
        Random r = getRandomFunction();
        if (r.nextInt(2) == 0)
            return 0;
        else
            return 1;
    }

    //===============selecting 2 random Teams from number of Teams=============
    public static List<Integer> getSelectedTeams(int numberOfTeams)throws Exception
    {
        List<Integer> selectedTeams=new ArrayList<>();
        Random r = GeneralUtils.getRandomFunction();
        int index1 = r.nextInt(numberOfTeams);
        int index2;
        do {
            index2 = r.nextInt(numberOfTeams);
        } while (index1 == index2);

        selectedTeams.add(++index1);
        selectedTeams.add(++index2);
        return selectedTeams;
    }
}
