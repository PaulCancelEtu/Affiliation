package SAE;

import java.time.LocalDate;
import java.util.HashMap;

public class SerialTestMain {
    public static void main(String[] args){  
        Criterion c1,c2,c3,c4,c5,c7,c8,c11,c12;
        HashMap<CriterionName,Criterion> map1 = new HashMap<>();
        HashMap<CriterionName,Criterion> map2 = new HashMap<>();
        c1=new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY);
        c2=new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY);
        c3=new Criterion("yes", CriterionName.HOST_HAS_ANIMAL);
        c4=new Criterion("no", CriterionName.HOST_HAS_ANIMAL);
        c5=new Criterion("same", CriterionName.HISTORY);
        c7=new Criterion("nonuts", CriterionName.HOST_FOOD);
        c8=new Criterion("nonuts", CriterionName.GUEST_FOOD);
        c11=new Criterion("laughing", CriterionName.HOBBIES);
        c12=new Criterion("playing", CriterionName.HOBBIES);
        
        map1.put(CriterionName.GUEST_ANIMAL_ALLERGY,c1);
        map1.put(CriterionName.HOST_HAS_ANIMAL,c4);
        map1.put(CriterionName.HISTORY,c5);
        map1.put(CriterionName.HOST_FOOD,c7);
        map1.put(CriterionName.GUEST_FOOD,c8);
        map1.put(CriterionName.HOBBIES,c11);
        map1.put(CriterionName.HOBBIES,c12);

        map2.put(CriterionName.GUEST_ANIMAL_ALLERGY,c2);
        map2.put(CriterionName.HOST_HAS_ANIMAL,c3);
        map2.put(CriterionName.HISTORY,c5);
        map2.put(CriterionName.HOST_FOOD,c7);
        map2.put(CriterionName.GUEST_FOOD,c8);
        map2.put(CriterionName.HOBBIES,c11);
        map2.put(CriterionName.HOBBIES,c12);

        Teenager teen1 = new Teenager("Crane", "Mave", LocalDate.parse("2007-09-09") , Country.GERMANY);
        Teenager teen2 = new Teenager("Mane", "Crave", LocalDate.parse("2015-04-01") , Country.GERMANY);
        
        HashMap<Teenager, Teenager> teens = new HashMap<>();
        teens.put(teen1, teen2);
         

        Platform.serial(teens, "res/serialTest.bin");
        System.out.println(Platform.deserial("res/serialTest.bin"));
    }
}
