package App;

import SAE.Criterion;
import org.junit.jupiter.api.Test;
import SAE.CriterionName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class CriterionTest {
    Criterion c1 = new Criterion("male", CriterionName.GENDER);
    Criterion c2 = new Criterion("apple", CriterionName.GENDER);
    Criterion c3 = new Criterion("", CriterionName.GENDER);

    Criterion c4 = new Criterion("yes", CriterionName.HOST_HAS_ANIMAL);
    Criterion c5 = new Criterion("apple", CriterionName.GUEST_ANIMAL_ALLERGY);

    Criterion c6 = new Criterion("nonuts", CriterionName.HOST_FOOD);
    Criterion c7 = new Criterion("apple", CriterionName.GUEST_FOOD);
    Criterion c8 = new Criterion("", CriterionName.HOST_FOOD);

    Criterion c9 = new Criterion("nonuts,test,yes,yo", CriterionName.GENDER);

    @Test
    public void testIsValid(){
        assertTrue(c1.isValid());
        assertFalse(c2.isValid());
        assertFalse(c3.isValid());
        
        assertTrue(c4.isValid());
        assertFalse(c5.isValid());

        assertTrue(c6.isValid());
        assertFalse(c7.isValid());
        assertTrue(c8.isValid());
    }

    @Test
    public void testEquals(){
        assertTrue(c2.equals("apple"));
        assertFalse(c7.equals("Faux"));
        assertTrue(c8.equals(""));
        assertFalse(c1.equals("malesssSSS"));
    }

    @Test
    public void testSplit(){
        ArrayList<String> aList = new ArrayList<String>();
        aList.add("nonuts");
        aList.add("test");
        aList.add("yes");
        aList.add("yo");

        assertEquals(aList, c9.splitCriterions());
        assertNotEquals(aList, c8.splitCriterions());
    }
}

