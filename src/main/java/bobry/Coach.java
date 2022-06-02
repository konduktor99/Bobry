package bobry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Coach extends Person implements ICoach, Serializable {


    private List<TeamCoaching> teamCoachings;


    public Coach(String firstName, String lastName, LocalDate birthDate, Boolean isDisabled, int salary) throws WrongInputException {
        super(firstName, lastName, birthDate, isDisabled, salary);
        teamCoachings = new ArrayList<>();


    }

    public Coach(Person person) throws WrongInputException {

        super(person.firstName, person.lastName, person.birthDate, person.isDisabled, person.salary);

        super.Person_Extent.remove(person);


    }


    public List<TeamCoaching> getTeamCoachings() {
        return teamCoachings;
    }

    @Override
    public void computeBonus() {
        if (teamCoachings.size() > 3)
            bonus = 5000;
    }


    public void addCoaching(TeamCoaching coaching) {
        teamCoachings.add(coaching);

    }

    public TeamCoaching getCurrentTeamCoaching(){

        TeamCoaching teamcoaching = teamCoachings.stream().filter(tm->tm.till==null).findFirst().orElse(null);
        if(teamcoaching!=null)
            return teamcoaching;
        return null;

    }

    public void finishCoaching () throws WrongInputException {
        TeamCoaching teamCoaching = getCurrentTeamCoaching();
        if(teamCoaching == null)
            throw new WrongInputException("This coach is not assigned to any team currently.");
        teamCoaching.till = LocalDate.now();

    }

    public static List<Coach> getCoachesList(){
        List<Coach> coaches = new ArrayList<>();
        Person.Person_Extent.forEach(p ->{
            if(p instanceof Coach )
                coaches.add((Coach) p);
        });

        return coaches;
    }

}
