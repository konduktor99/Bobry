package bobry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.EnumSet;

public class Main extends Application {

    public static void main(String[] args) {


//        Person emp3=null;
//                    try {
//
//        EnumSet specializations = EnumSet.noneOf(InjuryCategory.class);
//        specializations.add(InjuryCategory.FRACTURE);
//            specializations.add(InjuryCategory.SPRAIN);
//
//
//        FanAnimator fanAnimator2 = new FanAnimator("Konrad","kusza", LocalDate.parse("1949-06-05"),false,4000,"konduktor");
//        Player player1 = new Player("Lebron", "James",LocalDate.parse("2000-05-25"),2000000, 208, 113, 4000 );
//        Person ebobry = new Player("Kyrie", "Irving",LocalDate.parse("1991-05-25"),1000000, 198, 88 );
//        Person emp2 = new Coach("Greg", "Popovich",LocalDate.parse("1949-05-25"),false,500000);
//         emp3 = new Physiotherapist("Jan", "Kowalski",LocalDate.parse("1959-05-25") ,false, 4500, specializations);
//        ICoach coachingPlayer = new CoachingPlayer("Marian", "Waszczuk",LocalDate.parse("1991-05-25"),20000, 208, 101, 3000);
//        Physiotherapist physiotherapist1 = new Physiotherapist("Adam", "Kowalski",LocalDate.parse("1999-05-25") ,false,4100, specializations);
//        ICoach coach1= new Coach("Adam","Małysz", LocalDate.parse("1888-05-25"),false,2000 );
//        Player player6 = new Player("Marcin", "Gortat",LocalDate.parse("1987-05-25"),2000000, 208, 113, 4000 );
//
//
////        emp3=fanAnimator2;
////        ebobry= new Coach(ebobry);
//
//
//        Team team1 = Team.createTeam(League.I);
//        Team team2 = Team.createTeam(League.III);
//        Team team3 = Team.createTeam(League.III);
//
//        TeamCoaching coaching2 = new TeamCoaching(coach1,team1);
//        TeamCoaching coaching1 = new TeamCoaching(coachingPlayer,team1);
//
//
//        Game game1 = new Game(LocalDate.parse("2019-05-25"), "Turów Zgorzelec");
////        Game game2 = new Game(LocalDate.parse("2019-05-25"), "Minesota Timbervolwes");
//
//
//        Player.Treatment treatment1 = player1.createTreatment( "nose fracture", InjuryCategory.FRACTURE);
//        Player.Treatment treatment2 = ((Player) ebobry).createTreatment( "recovery after covid", InjuryCategory.COVID);
//        Player.Treatment treatment3 = player1.createTreatment("recovery after covid",InjuryCategory.COVID);
//        Player.Treatment treatment4 = player1.createTreatment("sprained ankle joint",InjuryCategory.SPRAIN);
//        Player.Treatment treatment5 = player6.createTreatment("sprained shoulder joint",InjuryCategory.SPRAIN);
//        physiotherapist1.assignToTreatment(treatment1);
//        //((Physiotherapist)emp3).assignToTreatment(treatment3);
//        ((Player) ebobry).finishTreatment();
//
//        TeamMembership membership2 =new TeamMembership(player1,team2,LocalDate.parse("2019-05-25"), LocalDate.parse("2021-05-25"));
//        TeamMembership membership1 =new TeamMembership(player1,team1,LocalDate.parse("2011-05-25"));
//        TeamMembership membership3 =new TeamMembership(player1,team1,LocalDate.parse("2033-05-25"));
//
////            player1.finishTreatment();
////        System.out.println(player1.getTreatment());
////
////            player1.assignCaptainTeam(team1);
////        team1.assignCaptain(player1);
////
//            treatment3.assignPhysiotherapist(physiotherapist1);
//
////        System.out.println(physiotherapist1.getTreatmentsList());
////        System.out.println(player1.getPlayerTeams());
//
//
//            System.out.println(player1.getPlayerTeams());
//            System.out.println(team1.getCurrentPlayers());
//
//
//            team3.addGame(game1);
//            System.out.println(team3.findGameByDate(LocalDate.parse("2019-05-25")));
//            System.out.println(game1.getTeam());
//            System.out.println("BMI " + player1.compueBmi());
//
//            team1.addChampionships((short) 2022);
//            System.out.println(team1.getChampionships());
//
//
//
//        }catch (WrongInputException e) {
//            System.out.println(e.getMessage());
//        }



        final String playersExtentFile = "serialization.ser";
        try {
            //ODCZYT
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(playersExtentFile));
            Person.readPersonExtent(in);
            Player.readTreatmentExtent(in);
            TeamMembership.readMembershipExtent(in);
            TeamCoaching.readCoachingExtent(in);
            FanAnimator.readFanAnimatorExtent(in);
            Team.readTeamExtent(in);
            Game.readGameExtent(in);

            in.close();

            launch(args);

            //ZAPIS
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(playersExtentFile));
            Person.writePersonExtent(out);
            Player.writeTreatmentExtent(out);
            TeamMembership.writeMembershipExtent(out);
            TeamCoaching.writeCoachingExtent(out);
            FanAnimator.writeFanAnimatorExtent(out);
            Team.writeTeamExtent(out);
            Game.writeGameExtent(out);

            out.close();



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("__PERSON LIST__\n"+Person.getPersonList());
        System.out.println("__TREATMENT LIST__\n"+ Player.getTreatmentsList());
        System.out.println("__TEAM MEMBERSHIP LIST__\n"+TeamMembership.getMembershipsList());
        System.out.println("__TEAM COACHING LIST__\n"+TeamCoaching.getCoachingsList());
        System.out.println("__FAN ANIMATOR LIST__\n"+FanAnimator.getFanAnimatorsList());
        System.out.println("__TEAM LIST__\n"+Team.getTeamsList());
        System.out.println("__GAME LIST__\n"+Game.getGamesList());

        System.out.println("__PLAYER LIST__\n"+Player.getPlayersList());
        System.out.println("__PHYSIOTHERAPIST LIST__\n"+Physiotherapist.getPhysiotherapistsList());
        System.out.println("__COACH LIST__\n"+Coach.getCoachesList());
        System.out.println("__COACHING PLAYER LIST__\n"+CoachingPlayer.getCoachingPlayersList());



    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TreatmentListView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOpacity(0.96);
        primaryStage.setResizable(false);

        primaryStage.show();

//        AnchorPane anchorPane2 = FXMLLoader.load(getClass().getResource("/PhysiotherapistListView.fxml"));
//        scene = new Scene(anchorPane2);
//        primaryStage.setScene(scene);
//        primaryStage.show();



    }
}
