package cu.edu.cujae;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Identification {
    private final int[] id = new int[11];
    private final String idString;

    public Identification(String identification){
        final int ID_LENGTH = 11;
        if(identification.length() != ID_LENGTH)
            throw new InvalidParameterException("identification: " + identification + " -must have " + ID_LENGTH + " digits");
        char[] aux = identification.toCharArray();
        for(int i = 0; i < ID_LENGTH; i++) {
            id[i] = Character.getNumericValue(aux[i]);
        }
        if(!validDate())
            throw new InvalidParameterException("identification: " + identification + " -incorrect");
        idString = identification;
    }

    private boolean validDate(){
        return validNumbers() && validMonth() && validDay() && isBorn();
    }
    private boolean validNumbers(){
        for(int i : id){
            if(i < 0 || i > 9){
                return false;
            }
        }
        return true;
    }
    private boolean validMonth(){
        return getMonth() <= 12 && getMonth() >= 1;
    }
    private boolean validDay(){
        boolean correctDay;
        int birthMonth = getMonth();
        int birthDay = getDay();
        if(birthDay <= 0)
            correctDay = false;
        else if(birthMonth == 2){
            if(leapYear())
                correctDay = birthDay <= 29;
            else
                correctDay = birthDay <= 28;
        }
        else if(birthMonth <= 7){
            if(birthMonth % 2 != 0)
                correctDay = birthDay <= 31;
            else
                correctDay = birthDay <= 30;
        }
        else {
            if(birthMonth % 2 == 0)
                correctDay = birthDay <= 31;
            else
                correctDay = birthDay <= 30;
        }
        return correctDay;
    }
    private boolean isBorn(){
        Calendar actualDate = new GregorianCalendar();
        return actualDate.after(birthday());
    }
    public boolean leapYear() {
        return getYear() % 4 == 0;
    }
    public int getYear(){
        int birthYear = (this.id[0] * 10) + (this.id[1]);
        switch (century()) {
            case 21 -> birthYear += 2000;
            case 20 -> birthYear += 1900;
            case 19 -> birthYear += 1800;
        }
        return birthYear;
    }
    public int century(){
        int century = 0;
        if(this.id[6] >= 6 && this.id[6] <= 8)
            century = 21;
        else if(this.id[6] >= 0 && this.id[6] <= 5)
            century = 20;
        else if(this.id[6] == 9)
            century = 19;
        return century;
    }
    public int getMonth(){
        return (this.id[2] * 10) + (this.id[3]);
    }
    public int getDay(){
        return (this.id[4] * 10) + (this.id[5]);
    }
    public Calendar birthday(){
        return new GregorianCalendar(getYear(), getMonth() - 1, getDay());
    }
    public enum Gender{
        MALE, FEMALE
    }
    public Gender gender() {
        if(this.id[9] % 2 == 0)
            return Gender.MALE;
        return Gender.FEMALE;
    }
    public int age() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int birthYear = getYear();
        int age = year - birthYear;
        int birthMonth = getMonth();
        if(month < birthMonth)
            age--;
        else if(birthMonth == month) {
            int day = date.getDayOfMonth();
            if(day < getDay())
                age--;
        }
        return age;
    }
    @Override
    public String toString() {
        return idString;
    }

}
