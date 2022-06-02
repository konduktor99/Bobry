package bobry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

enum PersonType {PERSON,ORG_MEMBER, FAN};

public abstract class Person implements Serializable {



    //protected static List<Person> Person_Extent= new ArrayList<>();
    protected static TreeSet<Person> Person_Extent = new TreeSet<>(new PersonNameSerializableComparator());


    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;

    protected LocalDate joinDate;


    protected String nickname;


    protected Integer bonus;
    protected Integer salary;

    private EnumSet<PersonType> personType;


    private int taxRelief;

    protected boolean isDisabled;

    //pole z podklasy "Zdrowy"
    private static final int YOUTH_TAX_RELIEF_FACTOR = 50;

    //pole z podklasy "niepełnosprzawny"
    private static final int DISABLED_TAX_RELIEF_FACTOR = 100;



    public Person(String firstName, String lastName, LocalDate birthDate, Boolean isDisabled) {




        this.firstName=firstName;
        this.lastName=lastName;
        this.birthDate=birthDate;
        this.isDisabled=isDisabled;
        personType = EnumSet.<PersonType>of(PersonType.PERSON);

        this.computeTaxRelief(isDisabled);

        Person_Extent.add(this);


    }


    public Person(String firstName, String lastName, LocalDate birthDate, Boolean isDisabled, int salary) throws WrongInputException {
        this(firstName, lastName, birthDate, isDisabled);
        this.salary=salary;
        personType.add(PersonType.ORG_MEMBER);
    }

    public Person(String firstName, String lastName, LocalDate birthDate, boolean isDisabled, int salary, int bonus) throws WrongInputException {
        this(firstName,lastName,birthDate, isDisabled, salary);
        this.bonus=bonus;
        personType.add(PersonType.ORG_MEMBER);
    }

    public void computeTaxRelief(boolean isDisabled){
        if(isDisabled) {
            taxRelief = DISABLED_TAX_RELIEF_FACTOR;
        }else {
            if (Period.between(birthDate, LocalDate.now()).getYears() < 26)
                taxRelief=YOUTH_TAX_RELIEF_FACTOR;
        }
    }

    public void makeOrganizationMember(int salary){
        this.salary=salary;
        personType.add(PersonType.ORG_MEMBER);
    }

    public void removeOrganizationMember(){
        this.salary = null;
        this.bonus = null;
        personType.remove(PersonType.ORG_MEMBER);
    }


    public void makeFan(String nickname) throws WrongInputException {

        for(Person p: Person_Extent) {
            if (p.nickname != null) {
                if (p.nickname.equals(nickname))
                    throw new WrongInputException("There is already a fan animator with such nickname");
            }
        }
        this.joinDate=LocalDate.now();
        personType.add(PersonType.FAN);
    }

    public void removeFan(){
        this.joinDate=null;
        this.nickname=null;
        personType.remove(PersonType.FAN);
    }



    public abstract void computeBonus();


    @Override
    public String toString() {
        return  firstName + ' ' + lastName ;
    }

    public static void writePersonExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(Person_Extent);
    }

    public static void readPersonExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        Person_Extent=(TreeSet<Person>) stream.readObject();

    }

    public static TreeSet<Person> getPersonList() {
        return Person_Extent;
    }

    //GETTERS SETTERS --
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) throws WrongInputException {
        this.birthDate = birthDate;
    }

    public LocalDate getJoinDate() throws WrongInputException {
        if(personType.contains(PersonType.FAN))
        return joinDate;
        throw new WrongInputException("The person is not a fan");
    }

    public Integer getBonus() throws WrongInputException {
        if(personType.contains(PersonType.ORG_MEMBER))
        return bonus;
        throw new WrongInputException("The person is not an organization member");
    }

    public Integer getSalary() throws WrongInputException {
        if(personType.contains(PersonType.ORG_MEMBER))
        return salary;
        throw new WrongInputException("The person is not an organization member");
    }

    public void setSalary(Integer salary) throws WrongInputException {
        if(personType.contains(PersonType.ORG_MEMBER))
            this.salary = salary;
        else
            throw new WrongInputException("The person is not an organization member");

    }

    public String getNickname() {
        return nickname;
    }

    public int getTaxRelief() {
        return taxRelief;
    }

    public boolean isDisabled() {
        return isDisabled;
    }
    //--

    public static class PersonNameSerializableComparator implements Comparator<Person>, Serializable
    {
        @Override
        public int compare(Person p1, Person p2)
        {
            int res =  p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
            if (res != 0)
                return res;
            return p1.getLastName().compareToIgnoreCase(p2.getLastName());

        }
    }
}


