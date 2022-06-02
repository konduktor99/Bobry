package bobry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CoachingPlayer extends Player implements ICoach, Serializable {


   // private static List<CoachingPlayer> Coaching_Player_Extent = new ArrayList<>();
    private List<TeamCoaching> teamCoachings;

    public CoachingPlayer(String firstName, String lastName, LocalDate birthDate, int salary, int height, int weight, int bonus) throws WrongInputException {
        super(firstName, lastName, birthDate, salary, height, weight, bonus);
        teamCoachings= new ArrayList<>();
        //Coaching_Player_Extent.add(this);

    }


    public static List<CoachingPlayer> getCoachingPlayersList(){
        List<CoachingPlayer> coachingPlayers = new ArrayList<>();
        Person.Person_Extent.forEach(p ->{
            if(p instanceof Player && p instanceof ICoach )
                coachingPlayers.add((CoachingPlayer) p);
        });

        return coachingPlayers;
    }


        @Override
        public void addCoaching(TeamCoaching coaching){
            teamCoachings.add(coaching);

        }

    @Override
    public List<TeamCoaching> getTeamCoachings() {
       return teamCoachings;
    }


}


