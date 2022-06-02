package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FanAnimator extends Person implements Serializable {




    private static List<FanAnimator> Fan_Animator_Extent = new ArrayList<>();
    public FanAnimator(String firstName, String lastName, LocalDate birthDate,boolean isDisabled, int salary,String nickname) throws WrongInputException {



        super(firstName, lastName, birthDate,isDisabled);

        super.makeFan(nickname);
        super.makeOrganizationMember(salary);
        Fan_Animator_Extent.add(this);


        //animatedGames= new ArrayList<>();
    }

    public FanAnimator(Person person, String nickname) throws WrongInputException {

        //super(person.firstName, person.lastName, person.birthDate,person.isDisabled, person.salary);
        this(person.firstName, person.lastName, person.birthDate,person.isDisabled, person.salary,nickname);

        super.Person_Extent.remove(person);


    }

    public static void writeFanAnimatorExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Fan_Animator_Extent);
    }

    public static void readFanAnimatorExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Fan_Animator_Extent=(ArrayList<FanAnimator>) stream.readObject();

    }

    public static List<FanAnimator> getFanAnimatorsList() {
        return Fan_Animator_Extent;
    }

    @Override
    public void computeBonus() {
        this.bonus=0;
    }

    @Override
    public String toString() {
        return firstName+ " " + lastName + " - " + super.nickname;
    }
}