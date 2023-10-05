package SAE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Graphe.AffectationUtil;

/**
 * Permet d'administrer une plateforme d'association entre Host et Guest
 * 
 * La classe Platform est responsable de la gestion des associations entre les hôtes (hosts) et les invités (guests) sur une plateforme.
 * Elle permet d'ajouter et de supprimer des hôtes et des invités, d'associer des hôtes à des invités, de supprimer des associations,
 * d'imprimer la liste des associations, de rechercher des hôtes et des invités, d'importer et d'exporter des données, et de générer
 * des associations automatiquement en utilisant des historiques et des contraintes.
 * 
 * Les hôtes et les invités sont représentés par des objets de la classe Teenager.
 * 
 * Les associations entre les hôtes et les invités sont stockées dans une HashMap<Teenager, Teenager> appelée hostWithGuest.
 * Chaque association est représentée par une clé de type Teenager (hôte) associée à une valeur de type Teenager (invité).
 * 
 * Les constantes SEPARATOR et CSV_HEADER sont utilisées pour l'exportation des associations dans un fichier CSV.
 * 
 * Les méthodes principales de la classe Platform sont les suivantes :
 * - Le constructeur Platform(ArrayList<Teenager> hosts, ArrayList<Teenager> guests) permet de créer une nouvelle instance de Platform
 *   en spécifiant la liste des hôtes et des invités.
 * - Les méthodes addGuest(Teenager teen) et addHost(Teenager teen) permettent d'ajouter un invité ou un hôte à la plateforme.
 * - La méthode associate(Teenager host, Teenager guest) permet d'associer un hôte à un invité.
 * - Les méthodes delGuest(Teenager teen) et delHost(Teenager teen) permettent de supprimer un invité ou un hôte de la plateforme.
 * - La méthode delAssociation(Teenager host, Teenager guest) permet de supprimer une association entre un hôte et un invité.
 * - La méthode printHostWithGuest() affiche la liste des associations entre les hôtes et les invités.
 * - Les méthodes findHost(String idS) et findGuest(String idS) permettent de rechercher un hôte ou un invité par son identifiant.
 * - La méthode importationList(File file) permet d'importer une liste d'objets Teenager à partir d'un fichier.
 * - La méthode exportHostWithGuestCsv(String path) permet d'exporter les associations entre les hôtes et les invités dans un fichier CSV.
 * - Les méthodes static serial(HashMap<Teenager, Teenager> history, String path) et static deserial(String path) permettent de sérialiser
 *   et désérialiser la HashMap hostWithGuest dans un fichier.
 * - Les méthodes setHostsAndGuests(File path, Country hostCountry, Country guestCountry) et setHostsAndGuests(File path, String hostCountry, String guestCountry)
 *   permettent de définir les hôtes et les invités à partir d'un fichier en spécifiant les pays d'accueil des hôtes et des invités.
 * - Les méthodes generateHostWithGuest(HashMap<Teenager, Teenager> history) et generateHostWithGuest(HashMap<Teenager, Teenager> history, HashMap<Teenager, Teenager> forced)
 *   permettent de générer les associations entre les hôtes et les invités en utilisant des historiques et des contraintes.
 * 
 * @author CANCEL Paul
 * @author KELLER Arthur
 * @author SOULIEZ Gaspard
 */

public class Platform {
    private ArrayList<Teenager> hosts;
    private ArrayList<Teenager> guests;

    private HashMap<Teenager, Teenager> hostWithGuest;

    private static final String SEPARATOR = ";";
    private static final String CSV_HEADER = "HOSTS;GUESTS";

    /**
     * Constructeur de la classe Platform.
     * Crée une nouvelle instance de Platform avec les listes d'hôtes et d'invités spécifiées.
     * @param hosts La liste des hôtes.
     * @param guests La liste des invités.
     */

    public Platform(ArrayList<Teenager> hosts, ArrayList<Teenager> guests) {
        this.hosts = hosts;
        this.guests = guests;
        this.hostWithGuest = new HashMap<Teenager,Teenager>();
    }

    /**
     * Constructeur par défaut de la classe Platform.
     * Crée une nouvelle instance de Platform avec des listes d'hôtes et d'invités vides.
     */
    public Platform(){
        this(new ArrayList<Teenager>(), new ArrayList<Teenager>());
    }

    /**
     * Retourne la liste des hôtes.
     * @return La liste des hôtes.
     */
    public ArrayList<Teenager> getHosts() {
        return hosts;
    }

    /**
     * Retourne la liste des invités.
     * @return La liste des invités.
     */
    public ArrayList<Teenager> getguests() {
        return guests;
    }

    /**
     * Retourne la HashMap contenant les associations entre les hôtes et les invités.
     * @return La HashMap des associations hôte-invité.
     */
    public HashMap<Teenager, Teenager> getHostWithGuest() {
        return hostWithGuest;
    }

    /**
     * Ajoute un invité à la plateforme.
     * @param teen L'invité à ajouter.
     */
    public void addGuest(Teenager teen){
        this.guests.add(teen);
    }

    /**
     * Ajoute un hôte à la plateforme.
     * @param teen L'hôte à ajouter.
     */
    public void addHost(Teenager teen){
        this.hosts.add(teen);
    }

    /**
     * Associe un hôte à un invité sur la plateforme.
     * @param host L'hôte à associer.
     * @param guest L'invité à associer.
     */
    public void associate(Teenager host, Teenager guest){
        if (this.guests.contains(guest) && this.hosts.contains(host)){
            this.hostWithGuest.put(host, guest);
        }
    }

    /**
     * Supprime un invité de la plateforme.
     * @param teen L'invité à supprimer.
     */
    public void delGuest(Teenager teen){
        this.guests.remove(teen);
    }

    /**
     * Supprime un hôte de la plateforme.
     * @param teen L'hôte à supprimer.
     */
    public void delHost(Teenager teen){
        this.hosts.remove(teen);
    }

    /**
     * Supprime une association entre un hôte et un invité de la plateforme.
     * @param host L'hôte de l'association.
     * @param guest L'invité de l'association.
     */
    public void delAssociation(Teenager host, Teenager guest){
        this.hostWithGuest.remove(host, guest);
    }

    /**
     * Affiche la liste des associations entre les hôtes et les invités de la plateforme.
     */
    public void printHostWithGuest(){
        for(Teenager teen : this.hostWithGuest.keySet()){
            System.out.println(teen + " => " + this.hostWithGuest.get(teen));
        }
    }

    /**
     * Recherche un hôte dans la plateforme à partir de son identifiant.
     * @param idS L'identifiant de l'hôte sous forme de chaîne de caractères.
     * @return L'hôte correspondant à l'identifiant, ou null si aucun hôte n'est trouvé.
     */
    public Teenager findHost(String idS){
        try{
            int id = Integer.parseInt(idS);
            for(Teenager t : this.hosts){
                if (t.getId() == id) return t;
            }
        }catch(NumberFormatException e){
            return null;
        }
        return null;
    }

    /**
     * Recherche un invité dans la plateforme à partir de son identifiant.
     * @param idS L'identifiant de l'invité sous forme de chaîne de caractères.
     * @return L'invité correspondant à l'identifiant, ou null si aucun invité n'est trouvé.
     */
    public Teenager findGuest(String idS){
        try{
            int id = Integer.parseInt(idS);
            for(Teenager t : this.guests){
                if (t.getId() == id) return t;
            }
        }catch(NumberFormatException e){
            return null;
        }
        return null;
    }

    /**
     * Importe une liste d'objets Teenager à partir d'un fichier.
     * @param file Le fichier contenant la liste d'objets à importer.
     * @return La liste d'objets Teenager importée à partir du fichier.
     */
    public static ArrayList<Teenager> importationList(File file){
        Teenager.cpt = 1;
        ArrayList<Teenager> res = new ArrayList<Teenager>();
        try (Scanner sc = new Scanner(file)) {
            String line;
            sc.nextLine(); // pour skip le HEADER;
            while(sc.hasNextLine()){
                line = sc.nextLine();
                res.add(Teenager.importation(line));
            }
            return res;
        } catch (FileNotFoundException e) {
            System.out.println(file + " : fichier introuvable");;
            return null;
        }
    }

    /**
     * Exporte les associations entre les hôtes et les invités dans un fichier CSV.
     * @param path Le chemin du fichier CSV à exporter.
     */
    public void exportHostWithGuestCsv(String path){
        try {
            PrintWriter pw = new PrintWriter(new File(path));
            pw.println(CSV_HEADER);
            String ch, ch1;
            for(Teenager host : this.hostWithGuest.keySet()){
                ch = host.getId() + " " + host.getName() + " " + host.getForename();
                ch1 = this.hostWithGuest.get(host).getId() + " " + this.hostWithGuest.get(host).getName() + " " + this.hostWithGuest.get(host).getForename();
                pw.println(ch + SEPARATOR + ch1);
            }
            pw.flush();
            pw.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sérialise la HashMap hostWithGuest dans un fichier spécifié.
     * @param history La HashMap à sérialiser.
     * @param path Le chemin du fichier de sérialisation.
     */
    public static void serial(HashMap<Teenager, Teenager> history, String path){
        try(ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(path))){
            fos.writeObject(history);
            fos.flush();
            fos.close();
        }catch(IOException e){
            System.out.println("IOException");
        }catch(IllegalArgumentException e){
            System.out.println("Illegal Argument");
        }
    }

    /**
     * Désérialise la HashMap hostWithGuest à partir d'un fichier spécifié.
     * @param path Le chemin du fichier de désérialisation.
     * @return La HashMap désérialisée.
     */
    public static HashMap<Teenager, Teenager> deserial(String path){
        HashMap<Teenager, Teenager> res = new HashMap<Teenager, Teenager>();
        try(ObjectInputStream fis = new ObjectInputStream(new FileInputStream(path))){
            res = (HashMap<Teenager, Teenager>) fis.readObject();
            fis.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("Class not found");
        }
        return res;
    }

    /**
     * Définit les hôtes et les invités à partir d'un fichier en spécifiant les pays d'accueil.
     * @param path Le chemin du fichier contenant les données des hôtes et des invités.
     * @param hostCountry Le pays d'accueil des hôtes.
     * @param guestCountry Le pays d'accueil des invités.
     */
    public void setHostsAndGuests(File path, Country hostCountry, Country guestCountry){
        this.hosts = new ArrayList<Teenager>();
        this.guests = new ArrayList<Teenager>();
        ArrayList<Teenager> teens = Platform.importationList(path);
        for(Teenager t : teens){
            if(t.getCountry() == hostCountry){
                this.addHost(t);
            }else if (t.getCountry() == guestCountry){
                this.addGuest(t);
            }
        }
    }

    /**
     * Définit les hôtes et les invités à partir d'un fichier en spécifiant les pays d'accueil.
     * @param path Le chemin du fichier contenant les données des hôtes et des invités.
     * @param hostCountry Le nom du pays d'accueil des hôtes.
     * @param guestCountry Le nom du pays d'accueil des invités.
     */
    public void setHostsAndGuests(File path, String hostCountry, String guestCountry){
        this.setHostsAndGuests(path, Country.valueOf(hostCountry), Country.valueOf(guestCountry));
    }

    /**
     * Génère les associations entre les hôtes et les invités en utilisant des historiques et des contraintes.
     * @param history L'historique des associations précédentes.
     */
    public void generateHostWithGuest(HashMap<Teenager, Teenager> history, HashMap<Teenager, Teenager> forced){
        this.hostWithGuest = AffectationUtil.generatePair(history, this.hosts, this.guests, forced);
    }

    /**
     * Génère les associations entre les hôtes et les invités en utilisant des historiques et des contraintes.
     * @param history L'historique des associations précédentes.
     * @param forced Les associations forcées qui doivent être respectées.
     */
    public void generateHostWithGuest(HashMap<Teenager, Teenager> history){
        this.generateHostWithGuest(history, new HashMap<Teenager, Teenager>());
    }
}
