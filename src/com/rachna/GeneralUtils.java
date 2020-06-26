package com.rachna;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    private static List<String > selectedTeams=new ArrayList<String>();

    public static List<String> getSelectedTeams(int numberOfTeams) throws Exception {
        Random r = GeneralUtils.getRandomFunction();
        int index1 = r.nextInt(numberOfTeams);
        selectedTeams.add(GeneralUtils.getTeamList().get(index1));
        int index2;
        do {
            index2 = r.nextInt(numberOfTeams);
        } while (index1 == index2);
        selectedTeams.add(GeneralUtils.getTeamList().get(index2));
        return selectedTeams;
    }

    public static List<String> getTeamList() throws Exception {
        List<String> listOfTeams=(ImmutableMap.getTeamList()).stream().collect(Collectors.toList());
        return listOfTeams;
    }
}
