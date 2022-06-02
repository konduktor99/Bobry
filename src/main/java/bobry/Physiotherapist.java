package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Physiotherapist extends Person implements Serializable {



    private EnumSet specializations; //atr powtarzalny



    private List<Player.Treatment> treatments ; //strona * asocjacji zwykłej fizjoterapeuta-rehabilitacja

    //private static List<Physiotherapist> Physiotherapist_Extent = new ArrayList<>();




//    public Physiotherapist(){physiotherapistExtent.add(this);}

    public Physiotherapist(String firstName, String lastName, LocalDate birthDate, boolean isDisabled, int salary) throws WrongInputException {

        super(firstName,lastName,birthDate,isDisabled, salary);
        treatments = new ArrayList<Player.Treatment>();

        this.specializations=specializations;
        //Physiotherapist_Extent.add(this);

    }


    public Physiotherapist(String firstName, String lastName, LocalDate birthDate,boolean isDisabled, int salary,EnumSet specializations) throws WrongInputException {

            this(firstName,lastName, birthDate,isDisabled, salary);
            this.specializations=specializations;
//            this.treatments=treatments;


    }

    public  Physiotherapist(Person person, EnumSet specializations) throws WrongInputException {
        super(person.firstName,person.lastName,person.birthDate,person.isDisabled, person.salary);

        super.Person_Extent.remove(person);

    }

    @Override
    public void computeBonus() {
        if(specializations.size()>2)
            bonus = 2000;
    }

    //stworzenie powiazania fizjoterapeuta-rehabilitacja od strony rehabilitacji(leczenia)
    public void assignToTreatment(Player.Treatment treatment) throws WrongInputException {

        if(!specializations.contains(treatment.injuryCategory))
            throw new WrongInputException("Physiotherapist has different specialization");


        if(treatment==null)
            return;

        if(!treatments.contains(treatment)) {
            treatments.add(treatment);
            treatment.assignPhysiotherapist(this);
        }


    }

    public  List<Player.Treatment> getTreatmentsList(){
        return treatments;
    }




//   public static void writePhysiotherapistExtent(ObjectOutputStream stream) throws IOException {
//        stream.writeObject(Physiotherapist_Extent);
//    }
//
//    public static void readPhysiotherapistExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
//        Physiotherapist_Extent = (ArrayList<Physiotherapist>) stream.readObject();
//    }


    public static List<Physiotherapist> getPhysiotherapistsList() {

        List<Physiotherapist> physiotherapists = new ArrayList<>();
        Person.Person_Extent.forEach(p ->{
            if(p instanceof Physiotherapist)
                physiotherapists.add((Physiotherapist) p);
        });

        return physiotherapists;
    }



    public List<Player.Treatment> getTreatments() {
        return treatments;
    }

    public EnumSet getSpecializations() {
        return specializations;
    }

    @Override
    public String toString() {
        return  firstName +' '+ lastName;
//        + (specializations.size()>0 ?" Specializations: "+ specializations.stream().map(i->i.toString()).collect(Collectors.joining(", ")) : "");
    }
}
