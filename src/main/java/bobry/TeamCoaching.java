package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TeamCoaching implements Serializable {



    private ICoach coach;
    private CoachingPlayer coachingPlayer;
    private Team team;


    private static List<TeamCoaching> Coaching_Extent = new ArrayList<>();

    LocalDate since;
    LocalDate till;


    public TeamCoaching(ICoach coach, Team team){


        if(coach!=null && team!=null) {

            since = LocalDate.now();
            this.coach = coach;
            this.team = team;
            this.team.addCoaching(this);
            this.coach.addCoaching(this);
            Coaching_Extent.add(this);
        }
    }


    public TeamCoaching(ICoach coach, Team team, LocalDate since){

        this(coach, team);
        this.since=since;

    }
    public TeamCoaching(ICoach coach, Team team, LocalDate since,LocalDate till){

        this(coach, team,since);
        this.till=till;

    }

//    public TeamCoaching(Person coachingPlayer, Team team1) {
//    }

//    //GETTERS
//    public ICoach getCoach() {
//        return coach;
//    }
//
//    public Team getTeam() {
//        return team;
//    }
//
//    public LocalDate getSince() {
//        return since;
//    }
//
//    public LocalDate getTill() {
//        return till;
//    }
//
//    //SETTERS
//    public void setSince(LocalDate since) {
//        this.since = since;
//    }
//
//    public void setTill(LocalDate till) {
//        this.till = till;
//    }
//    //--

    public static List<TeamCoaching> getCoachingsList() {
        return Coaching_Extent;
    }

    public static void writeCoachingExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Coaching_Extent);
    }

    public static void readCoachingExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Coaching_Extent=(ArrayList<TeamCoaching>) stream.readObject();

    }


    @Override
    public String toString() {
        return
                "Coach: " + coach +
                        " Team: " + team +
                        " Since: " + since +
                        " Till: " + (till!=null?till: "now");
    }
}
