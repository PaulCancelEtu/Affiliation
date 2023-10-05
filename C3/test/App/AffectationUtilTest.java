package App;

import org.junit.jupiter.api.Test;

import Graphe.AffectationUtil;
import SAE.Teenager;
import SAE.Country;


import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;


public class AffectationUtilTest {
    Teenager t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 ;
    HashMap<Teenager, Teenager> history;

    @BeforeEach
    public void init(){
        t1 = new Teenager("A", "Adonia", LocalDate.now(), Country.FRANCE);
        t2 = new Teenager("B", "Bellatrix", LocalDate.now(), Country.FRANCE);
        t3 = new Teenager("C", "Callista", LocalDate.now(), Country.FRANCE);
        t4 = new Teenager("X", "Xolag", LocalDate.now(), Country.ITALY);
        t5 = new Teenager("Y", "Yak", LocalDate.now(), Country.ITALY);
        t6 = new Teenager("Z", "Zander", LocalDate.now(), Country.ITALY);

        t7 = new Teenager("A", "A", LocalDate.now(), Country.ITALY);
        t8 = new Teenager("B", "B", LocalDate.now(), Country.ITALY);
        t9 = new Teenager("C", "C", LocalDate.now(), Country.GERMANY);
        t10 = new Teenager("D", "D", LocalDate.now(), Country.GERMANY);
    
        t1.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t2.addCriterion("GUEST_ANIMAL_ALLERGY", "yes");
        t3.addCriterion("GUEST_ANIMAL_ALLERGY", "no");

        t7.addCriterion("GUEST_ANIMAL_ALLERGY", "yes");
        t8.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t9.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t10.addCriterion("GUEST_ANIMAL_ALLERGY", "no");

        t4.addCriterion("HOST_HAS_ANIMAL", "no");
        t5.addCriterion("HOST_HAS_ANIMAL", "yes");
        t6.addCriterion("HOST_HAS_ANIMAL", "no");

        t7.addCriterion("HOST_HAS_ANIMAL", "yes");
        t8.addCriterion("HOST_HAS_ANIMAL", "yes");
        t9.addCriterion("HOST_HAS_ANIMAL", "no");
        t10.addCriterion("HOST_HAS_ANIMAL", "yes");

        t1.addCriterion("HOBBIES", "sports,technology");
        t2.addCriterion("HOBBIES", "culture,science");
        t3.addCriterion("HOBBIES", "science,reading");
        t4.addCriterion("HOBBIES", "culture,technology");
        t5.addCriterion("HOBBIES", "science,reading");
        t6.addCriterion("HOBBIES", "technology");

        t7.addCriterion("HOBBIES", "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,aa,bb,cc,dd,ee,ff,gg,hh,ii,jj,kk,ll,mm,nn,oo,pp,qq,rr,ss,tt,uu,vv,ww,xx;yes;yes");
        t8.addCriterion("HOBBIES", "");
        t9.addCriterion("HOBBIES", "");
        t10.addCriterion("HOBBIES", "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,aa,bb,cc,dd,ee,ff,gg,hh,ii,jj,kk,ll,mm,nn,oo,pp,qq,rr,ss,tt,uu,vv,ww,xx;yes;yes");

        t1.addCriterion("GUEST_FOOD", "");
        t2.addCriterion("GUEST_FOOD", "");
        t3.addCriterion("GUEST_FOOD", "");
        t4.addCriterion("HOST_FOOD", "");
        t5.addCriterion("HOST_FOOD", "");
        t6.addCriterion("HOST_FOOD", "");
        t7.addCriterion("GUEST_FOOD", "");
        t8.addCriterion("GUEST_FOOD", "");
        t9.addCriterion("GUEST_FOOD", "");
        t10.addCriterion("GUEST_FOOD", "");
        t7.addCriterion("HOST_FOOD", "");
        t8.addCriterion("HOST_FOOD", "");
        t9.addCriterion("HOST_FOOD", "");
        t10.addCriterion("HOST_FOOD", "");

        /*Version 2 */
        t11 = new Teenager("Alber", "A", LocalDate.now(), Country.FRANCE);
        t12 = new Teenager("Bertrand", "B", LocalDate.now(), Country.FRANCE);
        t13 = new Teenager("Chloe", "C", LocalDate.now(), Country.FRANCE);
        t14 = new Teenager("Daniel", "D", LocalDate.now(), Country.FRANCE);
        t15 = new Teenager("Yakari", "Y", LocalDate.now(), Country.ITALY);
        t16 = new Teenager("Zoe", "Z", LocalDate.now(), Country.ITALY);
        t17 = new Teenager("Xavier", "X", LocalDate.now(), Country.ITALY);
        t18 = new Teenager("Wallyde", "W", LocalDate.now(), Country.ITALY);

        t11.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t12.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t13.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t14.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t15.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t16.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t17.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        t18.addCriterion("GUEST_ANIMAL_ALLERGY", "no");

        t11.addCriterion("HOST_HAS_ANIMAL", "no");
        t12.addCriterion("HOST_HAS_ANIMAL", "no");
        t13.addCriterion("HOST_HAS_ANIMAL", "no");
        t14.addCriterion("HOST_HAS_ANIMAL", "no");
        t15.addCriterion("HOST_HAS_ANIMAL", "no");
        t16.addCriterion("HOST_HAS_ANIMAL", "no");
        t17.addCriterion("HOST_HAS_ANIMAL", "no");
        t18.addCriterion("HOST_HAS_ANIMAL", "no");

        t11.addCriterion("HOBBIES", "");
        t12.addCriterion("HOBBIES", "");
        t13.addCriterion("HOBBIES", "");
        t14.addCriterion("HOBBIES", "");
        t15.addCriterion("HOBBIES", "");
        t16.addCriterion("HOBBIES", "");
        t17.addCriterion("HOBBIES", "");
        t18.addCriterion("HOBBIES", "");

        t11.addCriterion("GUEST_FOOD", "");
        t12.addCriterion("GUEST_FOOD", "");
        t13.addCriterion("GUEST_FOOD", "");
        t14.addCriterion("GUEST_FOOD", "");
        t15.addCriterion("GUEST_FOOD", "");
        t16.addCriterion("GUEST_FOOD", "");
        t17.addCriterion("GUEST_FOOD", "");
        t18.addCriterion("GUEST_FOOD", "");

        t11.addCriterion("HOST_FOOD", "");
        t12.addCriterion("HOST_FOOD", "");
        t13.addCriterion("HOST_FOOD", "");
        t14.addCriterion("HOST_FOOD", "");
        t15.addCriterion("HOST_FOOD", "");
        t16.addCriterion("HOST_FOOD", "");
        t17.addCriterion("HOST_FOOD", "");
        t18.addCriterion("HOST_FOOD", "");
        
        /* ALBERT   |  Yakari
         * CHLOE    |  ZOE
         * BERTRAND |  XAVIER
         * DANIEL   |  WALLYDE
         */

        t11.addCriterion("HISTORY", "same");
        t12.addCriterion("HISTORY", "");
        t13.addCriterion("HISTORY", "other");
        t14.addCriterion("HISTORY", "same");
        t15.addCriterion("HISTORY", "same");
        t16.addCriterion("HISTORY", "other");
        t17.addCriterion("HISTORY", "");
        t18.addCriterion("HISTORY", "other");

        history = new HashMap<Teenager, Teenager>();
        history.put(t11, t15);
        history.put(t13, t16);
        history.put(t12, t17);
        history.put(t14, t18);
    }

    @Test
    public void testWeightHobbies(){
        assertEquals(0, AffectationUtil.weightHobbies(t1, t5));
        assertEquals(-1, AffectationUtil.weightHobbies(t3, t2));
        assertEquals(-1, AffectationUtil.weightHobbies(t4, t6));
        assertEquals(-10, AffectationUtil.weightHobbies(t7, t10));
    }
    @Test
    public void testWeightCompatible(){
        assertEquals(0, AffectationUtil.weightCompatible(t2,t3));
        assertEquals(0, AffectationUtil.weightCompatible(t1, t4));
        assertEquals(100, AffectationUtil.weightCompatible(t5, t7));
    }

    @Test
    public void testWeightHistory(){
        assertEquals(-25, AffectationUtil.weightHistory(history, t11, t15));
        assertEquals(25, AffectationUtil.weightHistory(history, t13, t16));
        assertEquals(0, AffectationUtil.weightHistory(history, t12, t17));
        assertEquals(25, AffectationUtil.weightHistory(history, t14, t18));
    }
    @Test
    public void testWeight(){
        assertEquals(74, AffectationUtil.weight(history, t11, t15));
        assertEquals(99, AffectationUtil.weight(history, t12, t14));
        assertEquals(-1, AffectationUtil.weight(history, t16, t18));
    }
}
