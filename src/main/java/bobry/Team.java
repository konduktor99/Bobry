package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Team implements Serializable {


    private List<Short> championships;
    private League league;
    private TeamAdvancement advancement;


    private static List<Team> Team_Extent = new ArrayList<Team>();

    private List<TeamMembership> teamMemberships;
    private List<TeamCoaching> teamCoaching;
    private Map<LocalDate, Game> games = new TreeMap<>(); //strona * asocjacji kwalifikowanej mecz-drużyna


    private Player captain;

    private Team(TeamAdvancement advancement, League league) {

        teamMemberships = new ArrayList<>();
        teamCoaching = new ArrayList<TeamCoaching>();

        this.championships = new ArrayList<>();
        this.advancement = advancement;
        this.league = league;

        Team_Extent.add(this);


    }

    public static Team createTeam(League league) throws WrongInputException {

        if (Team_Extent.size() == 2) {
            return new Team(TeamAdvancement.III, league);
        }
        if (Team_Extent.size() == 1) {
            return new Team(TeamAdvancement.II, league);
        }
        if (Team_Extent.size() == 0) {
            return new Team(TeamAdvancement.I, league);
        }

        throw new WrongInputException("4th team is not allowed");
    }

    public void assignCaptain(Player player) throws WrongInputException {

        if (player == null)
            throw new WrongInputException("Wrong player provided ");

        TeamMembership teamMembership = teamMemberships.stream().filter(tm -> player.equals(tm.getPlayer())).findFirst().orElse(null);

        if (teamMembership == null)
            throw new WrongInputException("Player was never in this team");
        if (!(teamMembership.getSince().isBefore(LocalDate.now()) && teamMembership.getTill() == null))
            throw new WrongInputException("Player is not in this team currently");

        if (player.treatment != null)
            throw new WrongInputException("Player cannot be captain during the treatment");


        if (captain != player) {
            captain = player;
            player.assignCaptainTeam(this);
        }
    }


    //do asocjacji z atrybutem zawodnik-druzyna - wyszukiwanie zawodnikow z drużyny
    public List<String> getHistoryPlayers() {
        List<String> players = new ArrayList<>();
        players = teamMemberships.stream()
                .sorted(Comparator.comparing(TeamMembership::getSince))
                .map(
                        m -> m.getPlayer() + " " + m.getSince() + " - " + (m.getTill() != null ? m.getTill() : "now")
                ).collect(Collectors.toList());

        return players;
    }

    public List<String> getCurrentPlayers() {
        List<String> players = new ArrayList<String>();
        players = teamMemberships.stream().filter(p -> p.getTill() == null)
                .sorted(Comparator.comparing(TeamMembership::getSince))
                .map(
                        m -> m.getPlayer() + " " + m.getSince() + " -now"
                ).collect(Collectors.toList());

        return players;
    }


    //stworzenie powiazania mecz-drużyna od strony druzyny
    public void addGame(Game game) {

        if (game == null)
            return;

        if (!games.containsKey(game.getDate())) {
            games.put(game.getDate(), game);
            game.addTeam(this);
        }


    }

    // szukanie meczu o okreslonej dacie na podstawie asocjacji kwalifikowanej mecz-drużyna
    public Game findGameByDate(LocalDate date) throws WrongInputException {
        if (!games.containsKey(date)) {
            throw new WrongInputException("Unable to find the game with such date");
        }

        return games.get(date);
    }

    public void addMembership(TeamMembership membership) {
        teamMemberships.add(membership);
    }

    public void addCoaching(TeamCoaching coaching) {
        teamCoaching.add(coaching);
    }


    public static void writeTeamExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Team_Extent);
    }

    public static void readTeamExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Team_Extent = (ArrayList<Team>) stream.readObject();

    }

    public static List<Team> getTeamsList() {
        return Team_Extent;
    }


    public List<Short> getChampionships() {
        return championships;
    }

    public void addChampionships(short year) {
        this.championships.add(year);
    }

    public void setCaptain(Player captain) {
        this.captain = captain;
    }


    public String toString() {
        return "Team " + advancement.name();
        //+" League: "+league.name()+(championships.size()>0 ?" Championships: "+ championships.stream().map(i->i.toString()).collect(Collectors.joining(", ")) : "");
    }
}
