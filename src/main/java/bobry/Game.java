package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {


    private LocalDate date;
    private String opponent;
    private Byte ourScore;
    private Byte opScore;

    private static List<Game> Game_Extent = new ArrayList<Game>();



    private Team team;  //strona 1 asocjacji kwalifikowanej druzyna-mecz


    public Game(LocalDate date, String opponent) throws WrongInputException {

        for(Game g: Game_Extent){
            if(g.getDate().equals(date))
                throw new WrongInputException("There is already the game scheduled for this date");
        }

        this.date=date;
        this.opponent=opponent;
        Game_Extent.add(this);
    }

    public Game(LocalDate date, String opponent,byte ourScore, byte opScore) throws WrongInputException {
        this(date,opponent);
    }


    //stworzenie powiazania mecz-drużyna od strony meczu
    public void addTeam(Team team){

        if(team==null)
            return;

        if(this.team!=team) {
            this.team=team;
            team.addGame(this);
        }


    }

    public static void writeGameExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Game_Extent);
    }

    public static void readGameExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Game_Extent=(ArrayList<Game>) stream.readObject();

    }


    public static List<Game> getGamesList(){
        return Game_Extent;
    }


    //GETTERS
    public LocalDate getDate() {
        return date;
    }

    public String getOpponent() {
        return opponent;
    }

    public byte getOurScore() {
        return ourScore;
    }

    public byte getOpScore() {
        return opScore;
    }
    //SETTERS
    public void setScore(byte ourScore, byte opScore){
        this.ourScore=ourScore;
        this.opScore=opScore;
    }
    public Team getTeam() {
        return team;
    }
    //--


    @Override
    public String toString() {
        return
                "Date: " + date +
                " Opponent: " + opponent +
                        (ourScore!=null ? "  Score: " + ourScore +'-'+opScore : "");
    }
}
