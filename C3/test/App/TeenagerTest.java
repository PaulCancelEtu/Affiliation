package App;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


import SAE.Country;
import SAE.Teenager;

public class TeenagerTest {
    private Teenager arthur;
    private Teenager gaspard;
    private Teenager paul;

    @BeforeEach
    public void initialisation(){
        arthur = new Teenager("Keller", "arthur", LocalDate.of(2004, 2, 10), Country.SPAIN);
        gaspard = new Teenager("Souliez", "gaspard", LocalDate.of(2004, 7, 28), Country.GERMANY);
        paul = new Teenager("Cancel", "Paul", LocalDate.of(2004, 12, 26), Country.ITALY);

        arthur.addCriterion("GUEST_ANIMAL_ALLERGY", "yes");
        arthur.addCriterion("HOST_HAS_ANIMAL", "yes");
        arthur.addCriterion("GUEST_FOOD", "nonuts");

        
        gaspard.addCriterion("HOST_HAS_ANIMAL", "no");
        gaspard.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        gaspard.addCriterion("GUEST_FOOD", "vegetarian");

        paul.addCriterion("HOST_FOOD", "nonuts,vegetarian");
        paul.addCriterion("HOST_HAS_ANIMAL", "yes");


        gaspard.addCriterion("HOBBIES", "murder,phishing,beingbad");
        paul.addCriterion("HOBBIES", "beingbad");
        arthur.addCriterion("HOBBIES", "murder");
    }

    @Test
    public void testCompatibleWithGuest() {
        // Vérifier que la méthode animalCompatible fonctionne correctement
        assertTrue(gaspard.animalCompatible(arthur));
        assertTrue(arthur.animalCompatible(paul));
        assertFalse(paul.animalCompatible(arthur));
        // Vérifier que la méthode foodCompatible fonctionne correctement
        assertTrue(paul.foodCompatible(arthur));
        assertTrue(paul.foodCompatible(gaspard));
        assertFalse(gaspard.foodCompatible(arthur));
        // Vérifier que la méthode compatibleWithGuest fonctionne correctement
        assertFalse(paul.compatibleWithGuest(arthur));
        assertTrue(paul.compatibleWithGuest(gaspard));
        assertFalse(gaspard.compatibleWithGuest(arthur));
    }
    @Test
    public void testSameHobbies(){
        assertTrue(gaspard.sameHobbies(paul));
        assertTrue(gaspard.sameHobbies(arthur));
        assertFalse(paul.sameHobbies(arthur));
    }
    @Test
    public void testPurgeInvalidRequirement() {
        arthur.addCriterion("PAIR_GENDER", "yes");
        arthur.addCriterion("GENDER", "yes");
        gaspard.addCriterion("GENDER", "male");
        gaspard.addCriterion("PAIR_GENDER", "male");
        assertEquals(6 , arthur.getRequirements().size());
        assertEquals(6 , gaspard.getRequirements().size());
        arthur.purgeInvalidRequirement();
        gaspard.purgeInvalidRequirement();
        assertEquals(2 , arthur.getRequirements().size());
        assertEquals(6 , gaspard.getRequirements().size());
    }
    @Test
    public void testLineImport(){
        String line = "Mave;Crane;GERMANY;2007-09-09;sports;no;no;;nonuts,vegetarian;male;male;same";
        Teenager teen = new Teenager("Crane", "Mave", LocalDate.parse("2007-09-09") , Country.GERMANY);
        teen.addCriterion("HOBBIES", "sports");
        teen.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        teen.addCriterion("HOST_HAS_ANIMAL", "no");
        teen.addCriterion("GUEST_FOOD", "");
        teen.addCriterion("HOST_FOOD", "nonuts,vegetarian");
        teen.addCriterion("GENDER", "male");
        teen.addCriterion("PAIR_GENDER", "male");
        teen.addCriterion("HISTORY", "same");
        assertTrue(Teenager.importation(line).equals(teen));
    }
}