package Graphe;

import SAE.*;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Permet de calculer le poids pour les arrêtes entre un host et un guest
 * @author CANCEL Paul
 * @author KELLER Arthur
 * @author SOULIEZ Gaspard
 */
public class AffectationUtil {
    private static int WEIGHT_ADD = -1;
    private static int WEIGHT_IS_NOT_COMPATIBLE = 100;
    private static int WEIGHT_HISTORY = -25;
    private static int WEIGHT_HOBBY_LIMIT = -10;
    private static int WEIGHT_PAIR_GENDER = -10;

    

    public static void setWEIGHT_ADD(int wEIGHT_ADD) {
        WEIGHT_ADD = wEIGHT_ADD;
    }

    public static void setWEIGHT_IS_NOT_COMPATIBLE(int wEIGHT_IS_NOT_COMPATIBLE) {
        WEIGHT_IS_NOT_COMPATIBLE = wEIGHT_IS_NOT_COMPATIBLE;
    }

    public static void setWEIGHT_HISTORY(int wEIGHT_HISTORY) {
        WEIGHT_HISTORY = wEIGHT_HISTORY;
    }

    public static void setWEIGHT_HOBBY_LIMIT(int wEIGHT_HOBBY_LIMIT) {
        WEIGHT_HOBBY_LIMIT = wEIGHT_HOBBY_LIMIT;
    }
    
    public static int getWEIGHT_ADD() {
        return WEIGHT_ADD;
    }

    public static int getWEIGHT_IS_NOT_COMPATIBLE() {
        return WEIGHT_IS_NOT_COMPATIBLE;
    }

    public static int getWEIGHT_HISTORY() {
        return WEIGHT_HISTORY;
    }

    public static int getWEIGHT_HOBBY_LIMIT() {
        return WEIGHT_HOBBY_LIMIT;
    }

    public static int getWEIGHT_PAIR_GENDER() {
        return WEIGHT_PAIR_GENDER;
    }

    public static void setWEIGHT_PAIR_GENDER(int wEIGHT_PAIR_GENDER) {
        WEIGHT_PAIR_GENDER = wEIGHT_PAIR_GENDER;
    }
    

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */
    public static int weight(HashMap<Teenager, Teenager> lastYear, Teenager host, Teenager guest){
        return AffectationUtil.weightCompatible(host, guest) 
            + AffectationUtil.weightHobbies(host, guest) 
            + AffectationUtil.weightHistory(lastYear, host, guest)
            + AffectationUtil.weightPairGender(host, guest);
    }
    // ... ajouter toutes autres méthodes jugées nécessaires

    /**
     * Initialise la comptabilité à 100 si ils sont compatible et à 0 si les adolescents sont incompatibles.
     * @param host
     * @param guest
     * @return
     */
    public static int weightCompatible(Teenager host, Teenager guest){
        if (host.compatibleWithGuest(guest)){
            return 0;
        }else{
            return WEIGHT_IS_NOT_COMPATIBLE;
        }
    }

    /**
     * Compte puis renvoie le nombre de loisirs commun entre un guest et un host.
     * @param host
     * @param guest
     * @return
     */
    public static int weightHobbies(Teenager host, Teenager guest){
        int affinity = 0;
        try{
            ArrayList<String> hostHobbies = host.getRequirements().get("HOBBIES").splitCriterions();
            ArrayList<String> guestHobbies = guest.getRequirements().get("HOBBIES").splitCriterions();
            for(String s : hostHobbies){
                if (guestHobbies.contains(s)) {
                    affinity+= WEIGHT_ADD;
                }
            }
        }catch(NullPointerException e){
            return 0;
        }
        if(affinity<WEIGHT_HOBBY_LIMIT){
            return WEIGHT_HOBBY_LIMIT;
        }else{
            return affinity;
        }
    }

    public static int weightHistory(HashMap<Teenager, Teenager> lastYear, Teenager host, Teenager guest){
        if (lastYear.containsKey(host)){
            if (lastYear.get(host).equals(guest)){
                try{
                    String historyHost = host.getRequirements().get("HISTORY").getValue();
                    String historyGuest = guest.getRequirements().get("HISTORY").getValue();
                    if(historyGuest.equals("same") && historyHost.equals("same")){
                        return WEIGHT_HISTORY;
                    }else if(historyGuest.equals("other") || historyHost.equals("other")){
                        return -WEIGHT_HISTORY;
                    }
                }catch (NullPointerException e){
                    return 0;
                }
            }
        }
        return 0;
    }

    public static int weightPairGender(Teenager host, Teenager guest){
        try{
            if(host.getRequirements().get("GENDER").getValue() == guest.getRequirements().get("PAIR_GENDER").getValue()) return WEIGHT_PAIR_GENDER;
        }catch (NullPointerException e){
            return 0;
        }
        return 0;
    }

    public static HashMap<Teenager, Teenager> generatePair(HashMap<Teenager, Teenager> history, ArrayList<Teenager> hosts, ArrayList<Teenager> guests, HashMap<Teenager, Teenager> forced){
        GrapheNonOrienteValue<Teenager> graphe = new GrapheNonOrienteValue<Teenager>();
        hosts.removeAll(forced.keySet());
        guests.removeAll(forced.values());
        for(Teenager host : hosts){
            graphe.ajouterSommet(host);
        }
        for(Teenager guest : guests){
            graphe.ajouterSommet(guest);
        }
        for(Teenager host : hosts){
            for(Teenager guest : guests){
                graphe.ajouterArete(host, guest, AffectationUtil.weight(history, host, guest));
            }
        }
        HashMap<Teenager, Teenager> res = new HashMap<Teenager, Teenager>();
        res.putAll(forced);
        CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(graphe, hosts, guests);
        List<fr.ulille.but.sae2_02.graphes.Arete<Teenager>> liste = calcul.calculerAffectation();
        for(fr.ulille.but.sae2_02.graphes.Arete<Teenager> a : liste){
            res.put(a.getExtremite1(), a.getExtremite2());
        }
        hosts.addAll(forced.keySet());
        guests.addAll(forced.values());
        return res;
    }

    public static void main(String[] args) {
        Teenager t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 ;
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

        HashMap<Teenager, Teenager> history = new HashMap<Teenager, Teenager>();
        history.put(t11, t15);
        history.put(t13, t16);
        history.put(t12, t17);
        history.put(t14, t18);

        GrapheNonOrienteValue<Teenager> graphe = new GrapheNonOrienteValue<Teenager>();
        List<Teenager> francais = new ArrayList<Teenager>();
        List<Teenager> ITALYns = new ArrayList<Teenager>();
        francais.add(t1);
        francais.add(t2);
        francais.add(t3);
        ITALYns.add(t4);
        ITALYns.add(t5);
        ITALYns.add(t6);
        for(Teenager fr : francais){
            graphe.ajouterSommet(fr);
        }
        for(Teenager it : ITALYns){
            graphe.ajouterSommet(it);
        }
        for(Teenager fr : francais){
            for(Teenager it : ITALYns){
                graphe.ajouterArete(it, fr, AffectationUtil.weight(history, it, fr));
            }
        }

        GrapheNonOrienteValue<Teenager> grapheItalyGermany = new GrapheNonOrienteValue<Teenager>();
        GrapheNonOrienteValue<Teenager> grapheGermanyToItaly = new GrapheNonOrienteValue<Teenager>();
        List<Teenager> ITALYns2 = new ArrayList<Teenager>();
        List<Teenager> allemands = new ArrayList<Teenager>();
        ITALYns2.add(t7);
        ITALYns2.add(t8);
        allemands.add(t9);
        allemands.add(t10);

        for(Teenager it : ITALYns2){
            grapheItalyGermany.ajouterSommet(it);
            grapheGermanyToItaly.ajouterSommet(it);
        }
        for(Teenager al : allemands){
            grapheItalyGermany.ajouterSommet(al);
            grapheGermanyToItaly.ajouterSommet(al);
        }
        for(Teenager al : allemands){
            for(Teenager it : ITALYns2){
                grapheItalyGermany.ajouterArete(it, al, AffectationUtil.weight(history, al, it));
            }
        }
        for(Teenager it : ITALYns2){
            for(Teenager al : allemands){
                grapheGermanyToItaly.ajouterArete(al, it, AffectationUtil.weight(history, it, al));
            }
        }

        CalculAffectation<Teenager> calculEx1 = new CalculAffectation<Teenager>(graphe, ITALYns, francais);
        List<fr.ulille.but.sae2_02.graphes.Arete<Teenager>> listeEx1 = calculEx1.calculerAffectation();
        System.out.println("Francais | ITALYns");
        System.out.println(listeEx1);
        System.out.println(calculEx1.getCout());
        

        System.out.println();
        System.out.println();

        CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(grapheItalyGermany, allemands, ITALYns2);
        List<fr.ulille.but.sae2_02.graphes.Arete<Teenager>> liste = calcul.calculerAffectation();
        System.out.println("ITALYns | Allemands");
        System.out.println(liste);
        System.out.println(calcul.getCout());
        
        System.out.println();
        System.out.println();

        CalculAffectation<Teenager> calcul2 = new CalculAffectation<Teenager>(grapheGermanyToItaly, ITALYns2, allemands);
        List<fr.ulille.but.sae2_02.graphes.Arete<Teenager>> liste2 = calcul2.calculerAffectation();
        System.out.println("Allemands | ITALYns");
        System.out.println(liste2);
        System.out.println(calcul2.getCout());
    }
}

