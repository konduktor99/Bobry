package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Player extends Person implements Serializable {



    //private int id; //atr. pochodny
    private final int MIN_AGE=16;

    private int height;
    private int weight;
//    private int seasonPoints;
//    private int totalPoints;
    private int number;

    private Team captainTeam;



    //private static List<Player> Players_Extent = new ArrayList<>();


    //STATIC PROPERTIES FOR INNER CLASS - TREATMENT
    private static List<Treatment> Treatment_Extent = new ArrayList<>();
    private static int Treatment_Counter=1;


   // private static Set<Treatment> allTreatments = new HashSet<>();



//    private Map<Integer,Treatment> treatments;
     Treatment treatment;  //atr. Złożony  //Strona 1 kompozycji zawodnik-rehabilitacja
    private List<TeamMembership> teamMemberships;



    public Player(String firstName, String lastName, LocalDate birthDate,  int salary, int height, int weight) throws WrongInputException {




        super(firstName, lastName, birthDate, false,salary);

        if (Period.between(birthDate, LocalDate.now()).getYears() < MIN_AGE)
            throw new WrongInputException("Player cannot be younger than "+ MIN_AGE+ " years old");


//        treatments =  new TreeMap<>();

        teamMemberships= new ArrayList<>();

        this.height = height;
        this.weight = weight;


        //Players_Extent.add(this);



    }

    public Player(String firstName, String lastName,LocalDate birthDate,int salary, int height, int weight, int bonus) throws WrongInputException {

        this(firstName, lastName, birthDate, salary,height,weight);
//        this.seasonPoints=seasonPoints;
        super.bonus=bonus;



    }
    public Player(Person person, int height, int weight) throws WrongInputException {


          super(person.firstName, person.lastName, person.birthDate,false, person.salary);
          this.weight=weight;
          this.height=height;
        super.Person_Extent.remove(person);




    }

    @Override
    public void computeBonus() {
        double bmi = compueBmi();
        if (bmi < 28 && bmi > 20)
            bonus = 3000;
    }


    public void assignCaptainTeam(Team team) throws WrongInputException {

        if(team==null)
            throw new WrongInputException("Wrong team provided ");

        Team curTeam = getCurrentTeam();


        //subset

        if(curTeam==null)
            throw new WrongInputException("Player is not assigned to any team");
        if(curTeam!=team)
            throw new WrongInputException("Player is not in this team currently");




        //zapewnienie ograniczenia XOR miedzy asocjacjami
        if(treatment!=null)
            throw new WrongInputException("Player cannot be captain during the treatment");



        if(captainTeam!=team) {
            captainTeam = team;
            team.assignCaptain(this);
        }
    }

    public Team getCurrentTeam(){

        TeamMembership teamMembership = teamMemberships.stream().filter(tm->tm.till==null).findFirst().orElse(null);
        if(teamMembership!=null)
            return teamMembership.getTeam();
        return null;

    }

    public Treatment createTreatment (String description, InjuryCategory injuryCategory){

        //XOR usuwanie asocjacji "jest kapitanem" przy zapisywaniu na rehabilitacje
        if(captainTeam!=null) {
            captainTeam.setCaptain(null);
            captainTeam = null;
        }

        this.treatment = new Treatment(description,injuryCategory);

        return treatment;
    }

    public void finishTreatment(){
        if(treatment!=null) {
            treatment.till=LocalDate.now();
            if(treatment.physiotherapist!=null)
            treatment.physiotherapist.getTreatments().remove(treatment);
            treatment = null;
        }
    }







    public double compueBmi(){
        return (double)weight/((height/100)*(height/100));
    }

    //do asocjacji z atrybutem zawodnik-druzyna - wyszukiwanie druzyn w jakich grał zawodnik
    public List<String> getPlayerTeams(){
        List<String> teams
               = getTeamMemberships().stream().sorted(Comparator.comparing(TeamMembership::getSince)).map( m -> m.getTeam().toString()+" "+ m.getSince() +" - "+(m.getTill()!=null?m.getTill():"now"))
                .collect(Collectors.toList());
        teams.forEach((s)-> System.out.println(s));
        return teams;
    }











//    public static void writePlayersExtent(ObjectOutputStream stream) throws IOException {
//        stream.writeObject(Players_Extent);
//    }
//
//    public static void readPlayersExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
//        Players_Extent = (ArrayList<Player>) stream.readObject();
//    }


    public static List<Player> getPlayersList() {

        List<Player> players = new ArrayList<>();
        Person.Person_Extent.forEach(p ->{
            if(p instanceof Player && !(p instanceof ICoach) )
                players.add((Player) p);
        });

        return players;
    }


    @Override
    public String toString() {
        return  firstName + ' ' + lastName;

    }


    public String getName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }
    public List<TeamMembership> getTeamMemberships(){

        return teamMemberships;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setChampionshipBonus(int championshipBonus) {
        this.bonus = championshipBonus;
    }



    public void setBirthDate(LocalDate birthDate) throws WrongInputException {

        if (Period.between(birthDate, LocalDate.now()).getYears() < MIN_AGE)
            throw new WrongInputException("Player cannot be younger than "+ MIN_AGE+ " years old");

        super.birthDate = birthDate;
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Treatment getTreatment(){
        return treatment;
    }

    public void addMembership(TeamMembership membership){
        teamMemberships.add(membership);

    }







    public static void readTreatmentExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Treatment_Extent = (ArrayList<Treatment>) stream.readObject();
    }
    public static void writeTreatmentExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Treatment_Extent);
    }


    public static List<Treatment> getTreatmentsList() {
        return Treatment_Extent;
    }




    //klasa wewnetrzna bedaca "częścia" kompozycji zawodnik-rehabilitacja
    public class Treatment implements Serializable {

        int id;
        LocalDate since;
        LocalDate till;
        Physiotherapist physiotherapist;      //Strona 1 asocjacji zwykłej fizjoterapeuta-rehabilitacja

        String description;
        Player player;
        InjuryCategory injuryCategory;



        private Treatment( String description, InjuryCategory injuryCategory){

            player = Player.this;
            this.description = description;
            this.since = LocalDate.now();
            id=Treatment_Counter;

            this.injuryCategory=injuryCategory;

            Treatment_Extent.add(this);

            Treatment_Counter++;

    }



        //stworzenie powiazania fizjoterapeuta-rehabilitacja od strony rehabilitacji
        public void assignPhysiotherapist(Physiotherapist physio) throws WrongInputException {

            if(physio==null)
                return;

            if(!physio.getSpecializations().contains(this.injuryCategory))
                throw new WrongInputException("Physiotherapist has different specialization.");

            if(physiotherapist!=physio) {
                physiotherapist = physio;
                physio.assignToTreatment(this);
            }
        }






        public int getId() {
            return id;
        }

        public LocalDate getSince() {
            return since;
        }

        public LocalDate getTill() {
            return till;
        }

        public Physiotherapist getPhysiotherapist() {
            return physiotherapist;
        }

        public String getDescription() {
            return description;
        }

        public Player getPlayer() {
            return player;
        }

        public InjuryCategory getInjuryCategory() {
            return injuryCategory;
        }


        @Override
        public String toString() {
            return
                    id +
                            ". since:" + since +
                            ", till: " + (till!=null?till: "now") +
                            ", player: " + player.getName()+ ' ' + player.getLastName() +
//                            ", physiotherapist: " + (physiotherapist!=null?physiotherapist: "not assigned") +
                            ", description: " + description;
        }
    }


}
