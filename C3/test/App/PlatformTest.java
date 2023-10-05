package App;

import org.junit.jupiter.api.Test;
import SAE.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;

public class PlatformTest {

    Teenager teen, teen2, teen3, teen4;
    ArrayList<Teenager> list;
    String exportPath = "/home/infoetu/paul.cancel.etu/SAE_S2/SAE_DEV/C3/res/exportTest.csv";
    String serialPath = "/home/infoetu/paul.cancel.etu/SAE_S2/SAE_DEV/C3/res/serialTest.bin";
    File fichierCsv = new File("/home/infoetu/paul.cancel.etu/SAE_S2/SAE_DEV/C3/res/testTeenager.csv");
    
    File adoAleatoireFile = new File("/home/infoetu/paul.cancel.etu/SAE_S2/SAE_DEV/C3/res/adosAleatoiresAvecIncompatiblesFranceItalie.csv");


    @BeforeEach
    public void initialisation(){
        Teenager.cpt = 1;
        list = new ArrayList<Teenager>();
        teen = new Teenager("Crane", "Mave", LocalDate.parse("2007-09-09") , Country.GERMANY);
        teen.addCriterion("HOBBIES", "sports");
        teen.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen.addCriterion("HOST_HAS_ANIMAL", "no");
        teen.addCriterion("GUEST_FOOD", "");
        teen.addCriterion("HOST_FOOD", "nonuts,vegetarian");
        teen.addCriterion("GENDER", "male");
        teen.addCriterion("PAIR_GENDER", "male");
        teen.addCriterion("HISTORY", "same");

        teen2 = new Teenager("Tanjin", "Dalthu", LocalDate.parse("2009-06-22") , Country.ITALY);
        teen2.addCriterion("HOBBIES", "");
        teen2.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen2.addCriterion("HOST_HAS_ANIMAL", "no");
        teen2.addCriterion("GUEST_FOOD", "vegetarian");
        teen2.addCriterion("HOST_FOOD", "vegetarian,nonuts");
        teen2.addCriterion("GENDER", "female");
        teen2.addCriterion("PAIR_GENDER", "female");
        teen2.addCriterion("HISTORY", "same");

        teen3 = new Teenager("Rex", "Laris", LocalDate.parse("2006-03-17") , Country.GERMANY);
        teen3.addCriterion("HOBBIES", "");
        teen3.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen3.addCriterion("HOST_HAS_ANIMAL", "no");
        teen3.addCriterion("GUEST_FOOD", "");
        teen3.addCriterion("HOST_FOOD", "vegetarian");
        teen3.addCriterion("GENDER", "male");
        teen3.addCriterion("PAIR_GENDER", "");
        teen3.addCriterion("HISTORY", "");

        teen4 = new Teenager("Ekey", "Jensmebur", LocalDate.parse("2007-03-11") , Country.ITALY);
        teen4.addCriterion("HOBBIES", "");
        teen4.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen4.addCriterion("HOST_HAS_ANIMAL", "no");
        teen4.addCriterion("GUEST_FOOD", "nonuts");
        teen4.addCriterion("HOST_FOOD", "vegetarian");
        teen4.addCriterion("GENDER", "female");
        teen4.addCriterion("PAIR_GENDER", "male");
        teen4.addCriterion("HISTORY", "same");

        list.add(teen);
        list.add(teen2);
        list.add(teen3);
        list.add(teen4);
    }


    @Test
    public void importationListTest(){
        assertEquals(list, Platform.importationList(fichierCsv));
    }

    @Test
    public void findTest(){
        Platform p = new Platform();
        p.addHost(teen);;
        p.addHost(teen2);
        p.addGuest(teen3);

        assertEquals(teen, p.findHost("1"));
        assertEquals(null, p.findHost("18"));
        assertEquals(teen2, p.findHost("2"));
        assertEquals(teen3, p.findGuest("3"));
        assertEquals(null, p.findGuest("1"));
    }

    @Test
    public void setHostsAndGuestsTest(){
        Platform p = new Platform();
        p.setHostsAndGuests(fichierCsv, "ITALY", "GERMANY");
        assertEquals(teen2, p.getHosts().get(0));
        assertEquals(teen4, p.getHosts().get(1));
        assertEquals(teen, p.getguests().get(0));
        assertEquals(teen3, p.getguests().get(1));
    }

    @Test
    public void generateHostWithGuestTest(){
        Platform p = new Platform();
        p.setHostsAndGuests(fichierCsv, "ITALY", "GERMANY");

        HashMap<Teenager, Teenager> map = new HashMap<Teenager, Teenager>();
        map.put(teen2, teen);
        map.put(teen4, teen3);
        p.generateHostWithGuest(new HashMap<Teenager, Teenager>());
        assertEquals(map, p.getHostWithGuest());
    }

    public static void main(String[] args) {
        System.out.println(Platform.importationList(new File("res/testTeenager.csv")));
        
        
        ArrayList<Teenager>list = new ArrayList<Teenager>();
        Teenager teen = new Teenager("Crane", "Mave", LocalDate.parse("2007-09-09") , Country.GERMANY);
        teen.addCriterion("HOBBIES", "sports");
        teen.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen.addCriterion("HOST_HAS_ANIMAL", "no");
        teen.addCriterion("GUEST_FOOD", "");
        teen.addCriterion("HOST_FOOD", "nonuts,vegetarian");
        teen.addCriterion("GENDER", "male");
        teen.addCriterion("PAIR_GENDER", "male");
        teen.addCriterion("HISTORY", "same");

        Teenager teen2 = new Teenager("Tanjin", "Dalthu", LocalDate.parse("2009-06-22") , Country.ITALY);
        teen2.addCriterion("HOBBIES", "");
        teen2.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen2.addCriterion("HOST_HAS_ANIMAL", "no");
        teen2.addCriterion("GUEST_FOOD", "vegetarian");
        teen2.addCriterion("HOST_FOOD", "vegetarian,nonuts");
        teen2.addCriterion("GENDER", "female");
        teen2.addCriterion("PAIR_GENDER", "female");
        teen2.addCriterion("HISTORY", "same");

        Teenager teen3 = new Teenager("Rex", "Laris", LocalDate.parse("2006-03-17") , Country.GERMANY);
        teen3.addCriterion("HOBBIES", "");
        teen3.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen3.addCriterion("HOST_HAS_ANIMAL", "no");
        teen3.addCriterion("GUEST_FOOD", "");
        teen3.addCriterion("HOST_FOOD", "vegetarian");
        teen3.addCriterion("GENDER", "male");
        teen3.addCriterion("PAIR_GENDER", "");
        teen3.addCriterion("HISTORY", "");

        list.add(teen);
        list.add(teen2);
        list.add(teen3);
        
        
        // Platform.serial(list, "res/serialTest.bin");
        
        
        Platform p = new Platform(list, list);
        p.associate(teen, teen2);
        p.associate(teen3, teen2);
        p.exportHostWithGuestCsv("res/exportTest.csv");
        
        Platform p2 = new Platform();
        p2.setHostsAndGuests(new File("res/adosAleatoires.csv"), Country.FRANCE, Country.ITALY);
        p2.generateHostWithGuest(new HashMap<Teenager, Teenager>());
        System.out.println(p2.getHostWithGuest());
    }
}
