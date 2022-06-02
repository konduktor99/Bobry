package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//klasa posredniczaca dla pierwotnej asocjacji Drużyna-Zawodnik
public class TeamMembership implements Serializable {



    private Player player;
    private Team team;

    LocalDate since;
    LocalDate till;



    private static List<TeamMembership> Membership_Extent = new ArrayList<>();


    public TeamMembership(Player player, Team team){


        if(player!=null && team!=null) {

            since = LocalDate.now();
            this.player = player;
            this.team = team;
            this.team.addMembership(this);
            this.player.addMembership(this);
            Membership_Extent.add(this);
        }
    }

    public TeamMembership(Player player, Team team, LocalDate since){

        this(player, team);
        this.since=since;

    }
    public TeamMembership(Player player, Team team, LocalDate since,LocalDate till) throws WrongInputException {

        this(player, team,since);

        if(till.isAfter(LocalDate.now()))
            throw new WrongInputException("End of the membership cannot be future date");

        this.till=till;

    }

    //GETTERS
    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public LocalDate getSince() {
        return since;
    }

    public LocalDate getTill() {
        return till;
    }

    //SETTERS
    public void setSince(LocalDate since) {
        this.since = since;
    }

    public void setTill(LocalDate till) throws WrongInputException {
        if(till.isAfter(LocalDate.now()))
            throw new WrongInputException("End of the membership cannot be future date");

        this.till = till;
    }
    //--


    public static void writeMembershipExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Membership_Extent);
    }

    public static void readMembershipExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Membership_Extent=(ArrayList<TeamMembership>) stream.readObject();

    }

    public static List<TeamMembership> getMembershipsList() {
        return Membership_Extent;
    }

    @Override
    public String toString() {
        return
                "Player: " + player +
                " Team: " + team +
                " Since: " + since +
                " Till: " + (till!=null?till: "now");
    }
}
