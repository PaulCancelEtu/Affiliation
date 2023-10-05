package SAE;

/**
 * Permet de créer l'enum Country
 * @author CANCEL Paul
 * @author KELLER Arthur
 * @author SOULIEZ Gaspard
 */

public enum Country {

    /**
     * Indique le pays.
     * Le type de données est une chaîne de caractères ("FRANCE").
     */
    FRANCE("FRANCE"),
    
    /**
     * Indique le pays.
     * Le type de données est une chaîne de caractères ("ITALY").
     */
    ITALY("ITALY"),
    
    /**
     * Indique le pays.
     * Le type de données est une chaîne de caractères ("GERMANY").
     */
    GERMANY("GERMANY"),
    
    /**
     * Indique le pays.
     * Le type de données est une chaîne de caractères ("ESPAGNE").
     */
    SPAIN("SPAIN");
    
    /**
     * Le type de données pour chaque pays.
     */
    private final String PAYS;
    
    /**
     * Constructeur privé qui initialise le type de données pour chaque pays.
     * @param pays le type de données pour le PAYS.
     */
    private Country(String pays){
        PAYS = pays;
    }
    
    /**
     * Renvoie le type de données pour le PAYS.
     * @return le type de données pour le PAYS.
     */
    public String getPays(){
        return PAYS;
    }

    public static String affichage(){
        return "FRANCE, ITALY, GERMANY, SPAIN";
    }
}
