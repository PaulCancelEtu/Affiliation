package SAE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Permet de créer un objet Criterion
 * @author CANCEL Paul
 * @author KELLER Arthur
 * @author SOULIEZ Gaspard
 */
public class Criterion implements Serializable{
    private String value;
    private CriterionName label;


    /**
     * Tableaux correspondants aux valeurs valides pour chachun des types de critères.
     */
    private static String[] FOOD_VALIDS = {"nonuts", "vegetarian", ""};
    private static String[] GENDER_VALIDS = {"male", "female", "other"};
    private static String[] HISTORY_VALIDS = {"same", "other", ""};


    /**
     * Créer un Criterion completement specifique 
     * @param value veleur du critère
     * @param label nom du critère
     */
    public Criterion(String value, CriterionName label) {
        this.value = value;
        this.label = label;
    }

    /**
     * Retourne la veleur du critère
     * @return la veleur
     */
    public String getValue() {
        return value;
    }

    /**
     * Retourne le nom du critère
     * @return le critère
     */
    public CriterionName getLabel() {
        return label;
    }

    /**
    * Modifie la valeur de la propriété "value".
    * @param value La nouvelle valeur à assigner à la propriété "value".
    */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * Vérifie que l'intégralité des valeurs présentes dans le critères sont valides.
     * @return
     */
    public boolean isValid(){
        if (this.label.getType() == 'B'){
            return (this.value.equals("yes") || this.value.equals("no")); 
        }
        ArrayList<String> splittedCriterions = this.splitCriterions();
        try{
            if (this.label == CriterionName.GUEST_FOOD || this.label == CriterionName.HOST_FOOD){
                validCheck(splittedCriterions, FOOD_VALIDS);
            } else if (this.label == CriterionName.GENDER || this.label == CriterionName.PAIR_GENDER){
                validCheck(splittedCriterions, GENDER_VALIDS);
            } else if (this.label == CriterionName.HISTORY){
                validCheck(splittedCriterions, HISTORY_VALIDS);
            }
        }catch(InvalidValueException e){
            return false;
        }
        return true;
    }

    /**
     * Renvoie une List<String> divisant les valeurs associées au critère en les séparant avec les virgules 
     * @param criterions
     * @return
     */
    public ArrayList<String> splitCriterions(){
        ArrayList<String> res = new ArrayList<String>();
        for (String s : this.value.split(",")) {
            res.add(s);
        }
        return res;
    }

    /**
     * Utilise la list découpée et un tableau statique et compare chaque valeur en s'assurant qu'elle est valide.
     * @param list
     * @param tab
     */
    public static void validCheck(ArrayList<String> list, String[] tab) throws InvalidValueException{
        for(String s: list){
            if(!Arrays.asList(tab).contains(s)) throw new InvalidValueException();
        }
    }



    /**
     * Retourne true si un le text est égale à celui passé en paramètre
     * @param text Le texte à verifier
     * @return true si le texte est pareil ou false si celui est différent
     */
    public boolean equals(String text){
        return this.value.equals(text);
    }

    /**
     * Retourne la représentation textuelle d'un Criterion, contenant sa value et son label
     * @return  "Criterion [value="value", label="label"]"
     */
    @Override
    public String toString() {
        return "Criterion [value=" + value + ", label=" + label + "]";
    }
}


