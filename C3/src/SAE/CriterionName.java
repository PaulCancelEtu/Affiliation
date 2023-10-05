package SAE;

/**
 * Permet de créer l'enum CriterionName
 * @author CANCEL Paul
 * @author KELLER Arthur
 * @author SOULIEZ Gaspard
 */

public enum CriterionName{

    /**
     * Indique si l'eleve a une allergie aux animaux.
     * Le type de données est un booléen ('B').
     */
    GUEST_ANIMAL_ALLERGY('B'),
    
    /**
     * Indique si l'hôte a un animal allergene chez lui.
     * Le type de données est un booléen ('B').
     */
    HOST_HAS_ANIMAL('B'),
    
    /**
     * Représente  les spécificités du régime alimentaire de l'eleve.
     * Le type de données est une chaîne de caractères ('T').
     */
    GUEST_FOOD('T'),
    
    /**
     * Représente les regimes alimentaires que l'hôte accepte de servir.
     * Le type de données est une chaîne de caractères ('T').
     */
    HOST_FOOD('T'),
    
    /**
     * Représente les loisirs et intérêts de l'eleve.
     * Le type de données est une chaîne de caractères ('T').
     */
    HOBBIES('T'),
    
    /**
     * Représente le genre de l'eleve.
     * Le type de données est une chaîne de caractères ('T').
     */
    GENDER('T'), 
    
    /**
     * Représente le genre de la personne avec qui l'eleve veut etre jumelé.
     * Le type de données est une chaîne de caractères ('T').
     */
    PAIR_GENDER('T'),
    
    /**
     * Représente la préférence de conservation du même correspondant que la dernière fois.
     * Le type de données est une chaîne de caractères ('T').
     */
    HISTORY('T');
    
    /**
     * Le type de données pour chaque critère.
     */
    private final char TYPE;

    /**
     * Constructeur privé qui initialise le type de données pour chaque critère.
     * @param type le type de données pour le critère.
     */
    private CriterionName(char type) {
        TYPE = type;
    }
    
    /**
     * Renvoie le type de données pour le critère.
     * @return le type de données pour le critère.
     */
    public char getType() {
        return TYPE;
    }
}
