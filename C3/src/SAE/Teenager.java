package SAE;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Permet de créer un objet Teenager
 * @author CANCEL Paul
 * @author KELLER Arthur
 * @author SOULIEZ Gaspard
 */

public class Teenager implements Serializable{
    private int id;
    private String name;
    private String forename;
    private LocalDate birthday;
    private Country country;
    private Map<String, Criterion> requirements;

    public static int cpt = 1;
    private static final String SEPARATOR = ";";

    /**
     * Créer un Criterion completement specifique
     * @param name Nom du Teenager
     * @param forename Prenom du Teenager
     * @param birthday Date d'anniversaire du Teenager 
     * @param country Pays du Teenager
     * @param requirements Exigences du Teenager
     */
    public Teenager(String name, String forename, LocalDate birthday, Country country,
        HashMap<String, Criterion> requirements) {
        this.id = cpt;
        cpt ++;
        this.name = name;
        this.forename = forename;
        this.birthday = birthday;
        this.country = country;
        this.requirements = requirements;
    }

    public Teenager(String name, String forename, LocalDate birthday, Country country) {
        this(name, forename, birthday, country, new HashMap<String, Criterion>());
    }

    /**
     * Verifie si le Teenager est compatible avec un autre Teenager 
     * @param teen  Le Teenager pour verifier la compatibilité entre les deux
     * @return true s'ils sont compatibles ou false s'ils ne le sont pas
     */
    public boolean compatibleWithGuest(Teenager teen){
        if (!this.animalCompatible(teen)){
            return false;
        }else if(!this.foodCompatible(teen)){
            return false;
        }else if(this.country == Country.FRANCE || teen.getCountry() == Country.FRANCE){
            return this.sameHobbies(teen);
        }
        return true;
    }

    /**
     * Verifie la compatibilité d'un Teenager avec un autre Teenager au niveau des alergies des animaux
     * @param teen Le Teenager 
     * @return true si les Teenagers sont compatible ou false s'ils ne le sont pas
     */
    public boolean animalCompatible(Teenager teen){
        if (!this.getRequirements().containsKey("HOST_HAS_ANIMAL")){
            this.addCriterion("HOST_HAS_ANIMAL", "no");
        }
        if(!teen.getRequirements().containsKey("GUEST_ANIMAL_ALLERGY")){
            teen.addCriterion("GUEST_ANIMAL_ALLERGY", "no");
        }
        return !(this.requirements.get("HOST_HAS_ANIMAL").equals("yes") && teen.requirements.get("GUEST_ANIMAL_ALLERGY").equals("yes"));
    }


    /**
     * Verifie la compatibilité d'un Teenager avec un autre Teenager au niveau des alergies d'alimentation
     * @param teen 
     * @return  true si les Teenagers sont compatible ou false s'ils ne le sont pas
     */
    public boolean foodCompatible(Teenager teen){
        if(!this.getRequirements().containsKey("HOST_FOOD")){
            this.addCriterion("HOST_FOOD", "");
        }
        if(!teen.requirements.containsKey("GUEST_FOOD")){
            teen.addCriterion("GUEST_FOOD", "");
        }
        if(teen.getRequirements().get("GUEST_FOOD").getValue().equals("")){
            return true;
        }
        ArrayList<String> ask = new ArrayList<String>();
        ArrayList<String> give = new ArrayList<String>();
        if(!teen.requirements.get("GUEST_FOOD").getValue().equals("")){
            for (String s : teen.requirements.get("GUEST_FOOD").getValue().split(",")) {
                ask.add(s);
            }
        }
        if(!this.requirements.get("HOST_FOOD").getValue().equals("")){
            for (String s : this.requirements.get("HOST_FOOD").getValue().split(",")) {
                give.add(s);
            }
        }

        for (String s : ask) {
            if (!give.contains(s)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Verifie la compatibilité d'un Teenager avec un autre Teenager au niveau des loisirs
     * @param teen
     * @return true si les Teenagers sont compatible ou false s'ils ne le sont pas
     */
    public boolean sameHobbies(Teenager teen){
        if(!teen.requirements.containsKey("HOBBIES") || !this.requirements.containsKey("HOBBIES")){
            return false;
        }
        ArrayList<String> ask = new ArrayList<>();
        ArrayList<String> give = new ArrayList<>();
        if(!teen.requirements.get("HOBBIES").getValue().equals("")){
            for (String s : teen.requirements.get("HOBBIES").getValue().split(",")) {
                ask.add(s);
            }
        }
        if(!this.requirements.get("HOBBIES").getValue().equals("")){
            for (String s : this.requirements.get("HOBBIES").getValue().split(",")) {
                give.add(s);
            }
        }
        for (String s : ask) {
            if (give.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne l'identifiant du Teenager
     * @return l'identifiant
     */
    public int getId() {
        return id;
    }
    
    /**
     * Retourne le nom du Teenager
     * @return Le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le prénom du Teenager
     * @return Le prénom
     */
    public String getForename() {
        return forename;
    }

    /**
     * Retourne la date d'anniversaire du Teenager
     * @return La date d'anniversaire
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Retourne le pays du Teenager
     * @return Le pays
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Retourne l'exigence du Teenager 
     * @return L'exigence
     */
    public Map<String, Criterion> getRequirements() {
        return requirements;
    }

    /**
     * Ajoute une exigence au Teenager avec le nom du critère et sa valeur.
     * 
     * @param criterionName Le nom du critère.
     * @param value         La valeur du critère.
     * @return true si l'exigence a été ajoutée avec succès, sinon false.
     */
    public boolean addCriterion(String criterionName, String value) {
        if (!this.getRequirements().containsKey(criterionName)){
            this.requirements.put(criterionName, new Criterion(value, CriterionName.valueOf(criterionName)));
            return true;
        }else if (this.getRequirements().get(criterionName).equals("")) {
            this.requirements.put(criterionName, new Criterion(value, CriterionName.valueOf(criterionName)));
            return true;
        }
        return false;
    }

    /** 
     * Vérifie si les valeurs des critères est valide et si celle ci sont vrais alors elles sont supprimées 
     */ 
    public void purgeInvalidRequirement(){
        if(requirements.get("HOST_HAS_ANIMAL").equals("yes") && requirements.get("GUEST_ANIMAL_ALLERGY").equals("yes")){
            requirements.remove("HOST_HAS_ANIMAL");
            requirements.remove("GUEST_ANIMAL_ALLERGY");
        }
        ArrayList<String> supp = new ArrayList<String>();
        for(String crit : requirements.keySet()){
            if(!requirements.get(crit).isValid()){
                supp.add(crit);
            }
        }
        for(String crit : supp){
            requirements.remove(crit);
        }
    }

    /**
     * Retourne la représentation textuelle d'un  Teenager, contenant son id, son nom, son prénom, son genre et son pays
     * @return  "Teenager [id="id", name="name", forename="forename", country="country"]"
     */
    @Override
    public String toString() {
        return "[" + id + " " + name + " " + forename + " " + country + "]";
    }

    /**
     * Importe un objet Teenager à partir d'une ligne de texte.
     * 
     * @param line La ligne de texte contenant les informations du Teenager.
     * @return L'objet Teenager importé, ou null si une exception s'est produite.
     */
    public static Teenager importation(String line){
        try (Scanner sc = new Scanner(line).useDelimiter(SEPARATOR)) {
            String forename = sc.next(); 
            String name = sc.next();
            String country = sc.next();
            String date = sc.next();
            String hobbies = sc.next();
            String guestAnimalAllergy = sc.next();
            String hostHasAnimal = sc.next();
            String guestFood = sc.next();
            String hostFood = sc.next();
            String gender = sc.next();
            String genderPair = sc.next();
            String history;
            if(sc.hasNext()){
                history = sc.next();
            }else{
                history = "";
            }

            Teenager teen = new Teenager(name, forename, LocalDate.parse(date), Country.valueOf(country));
            teen.addCriterion("HOBBIES", hobbies);
            teen.addCriterion("GUEST_ANIMAL_ALLERGY", guestAnimalAllergy);
            teen.addCriterion("HOST_HAS_ANIMAL", hostHasAnimal);
            teen.addCriterion("GUEST_FOOD", guestFood);
            teen.addCriterion("HOST_FOOD", hostFood);
            teen.addCriterion("GENDER", gender);
            teen.addCriterion("PAIR_GENDER", genderPair);
            teen.addCriterion("HISTORY", history);

            return teen;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((forename == null) ? 0 : forename.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        return result;
    }

    /**
     * Détermine si l'objet courant est égal à un autre objet donné.
     *
     * @param obj L'objet à comparer avec l'objet courant.
     * @return {@code true} si les objets sont égaux, {@code false} sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Teenager other = (Teenager) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (forename == null) {
            if (other.forename != null)
                return false;
        } else if (!forename.equals(other.forename))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (country != other.country)
            return false;
        return true;
    }
}


