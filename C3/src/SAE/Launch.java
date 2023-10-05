package SAE;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import Graphe.AffectationUtil;

public class Launch {

    private static boolean validCountry(String answer){
        return answer.equals("FRANCE") || answer.equals("ITALY") || answer.equals("GERMANY") || answer.equals("SPAIN");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String answer, countryHost = "", countryGuest = "", menu = "", ponderation;
        File csv;
        Platform p = new Platform();
        Teenager h = null, g = null;
        HashMap<Teenager, Teenager> forced, history;
        if(new File("res" + File.separator + "serialHistory.bin").exists()){
            history = Platform.deserial("res" + File.separator + "serialHistory.bin");
        }else{
            history = new HashMap<Teenager, Teenager>();
        }


        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Veuillez insérer le nom de votre fichier CSV (sans le '.csv' et présents dans le dossier 'res')");
        answer = sc.nextLine();
        System.out.println();
        csv = new File("res" + File.separator + answer + ".csv");

        while(!csv.exists()){
            System.out.println("Fichier introuvable veuillez entrer à nouveau : ");
            answer = sc.nextLine();
            System.out.println();
            csv = new File("res" + File.separator + answer + ".csv");
        } 

        while(!validCountry(countryHost)){
            System.out.println("Veuillez entrez le pays hôte parmi : " + Country.affichage());
            countryHost = sc.nextLine();
            System.out.println();
        }

        while(!validCountry(countryGuest)){
            System.out.println("Veuillez entrez le pays invité parmi : " + Country.affichage());
            countryGuest = sc.nextLine();
            System.out.println();
        }

        p.setHostsAndGuests(csv, countryHost, countryGuest);
        p.generateHostWithGuest(history);


        while(!menu.equals("0")){
            forced = new HashMap<Teenager, Teenager>();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Gestion des appariemments\n");

            System.out.println("1 : Insérer un fichier CSV");
            System.out.println("2 : Modifier les pondérations");
            System.out.println("3 : Forcer des appariements");
            System.out.println("4 : Afficher les appariements");
            System.out.println("5 : Générer le fichier csv de résultat et l'historique pour l'année prochaine et (A FAIRE UNIQUEMENT QUAND TOUT EST FINI !)");
            System.out.println();
            System.out.println("0 : Quitter");
            menu = sc.nextLine();


            if(menu.equals("1")){
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("Veuillez insérer le nom de votre fichier CSV (sans le '.csv')");
                answer = sc.nextLine();
                System.out.println();
                csv = new File("res" + File.separator + answer + ".csv");

                while(!csv.exists()){
                    System.out.println("Fichier introuvable veuillez entrer à nouveau : ");
                    answer = sc.nextLine();
                    System.out.println();
                    csv = new File("res" + File.separator + answer + ".csv");
                } 

                while(!validCountry(countryHost)){
                    System.out.println("Veuillez entrez le pays hôte parmi : " + Country.affichage());
                    countryHost = sc.nextLine();
                    System.out.println();
                }
                
                while(!validCountry(countryHost)){
                    System.out.println("Veuillez entrez le pays invité parmi : " + Country.affichage());
                    countryGuest = sc.nextLine();
                    System.out.println();
                }

                p.setHostsAndGuests(csv, countryHost, countryGuest);
                p.generateHostWithGuest(history);
            }
            while(menu.equals("2")){
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("1 : Weight hobbies : (" + (AffectationUtil.getWEIGHT_ADD()) + ")");
                System.out.println("2 : Limite Hobbies : (" + (AffectationUtil.getWEIGHT_HOBBY_LIMIT()) + ")");
                System.out.println("3 : Historique : (" + AffectationUtil.getWEIGHT_HISTORY() + ")");
                System.out.println("4 : Incompatibilité : (" + (AffectationUtil.getWEIGHT_IS_NOT_COMPATIBLE()) + ")");
                System.out.println("5 : Paire de genres (" + AffectationUtil.getWEIGHT_PAIR_GENDER() + ")");
                System.out.println("6 : Réinitialiser");
                System.out.println();
                System.out.println("0 : Quitter");
                ponderation = sc.nextLine();
                if(ponderation.equals("1")){
                    System.out.println("Entrez un nouveau poids (poids actuel : " + (AffectationUtil.getWEIGHT_ADD()) + ")");
                    AffectationUtil.setWEIGHT_ADD(Integer.parseInt(sc.nextLine()));
                }else if (ponderation.equals("2")){
                    System.out.println("Entrez un nouveau poids (poids actuel : " + (AffectationUtil.getWEIGHT_HOBBY_LIMIT()) + ")");
                    AffectationUtil.setWEIGHT_HOBBY_LIMIT(Integer.parseInt(sc.nextLine()));
                }else if(ponderation.equals("3")){
                    System.out.println("Entrez un nouveau poids (poids actuel : " + (AffectationUtil.getWEIGHT_HISTORY()) + ")");
                    AffectationUtil.setWEIGHT_HISTORY(Integer.parseInt(sc.nextLine()));
                }else if(ponderation.equals("4")){
                    System.out.println("Entrez un nouveau poids (poids actuel : " + (AffectationUtil.getWEIGHT_IS_NOT_COMPATIBLE()) + ")");
                    AffectationUtil.setWEIGHT_IS_NOT_COMPATIBLE(Integer.parseInt(sc.nextLine()));
                }else if (ponderation.equals("5")){
                    System.out.println("Entrez un nouveau poids (poids actuel : " + (AffectationUtil.getWEIGHT_PAIR_GENDER()) + ")");
                    AffectationUtil.setWEIGHT_PAIR_GENDER(Integer.parseInt(sc.nextLine()));
                }else if (ponderation.equals("6")){
                    AffectationUtil.setWEIGHT_ADD(-1);
                    AffectationUtil.setWEIGHT_HOBBY_LIMIT(-10);
                    AffectationUtil.setWEIGHT_HISTORY(-25);
                    AffectationUtil.setWEIGHT_IS_NOT_COMPATIBLE(100);
                }else if(ponderation.equals("0")){
                    menu = "";
                    ponderation = "";
                    p.generateHostWithGuest(history);
                }
            }
            while (menu.equals("3")){
                System.out.println("Entrez un id pour l'hôte (entrez 0 pour quitter)");
                answer = sc.nextLine();

                if(answer.equals("0")){
                    p.generateHostWithGuest(history, forced);
                    menu = "";
                }else{
                    h = p.findHost(answer);
                    System.out.println(h);
                    System.out.println();
                    System.out.println("Entrez un id pour l'invité (entrez 0 pour quitter)");
                    answer = sc.nextLine();

                    if(answer.equals("0")){
                        menu = "";
                        p.generateHostWithGuest(history, forced);
                    }else{
                        g = p.findGuest(answer);
                        System.out.println(g);
                        System.out.println();
                        if(h!= null && g!= null){
                            forced.put(h, g);
                        }
                    }
                }
            }
            if(menu.equals("4")){
                p.printHostWithGuest();
                System.out.println();
                System.out.println("Appuyez sur entrée pour quitter l'affichage");
                sc.nextLine();
            }
            if(menu.equals("5")){
                Platform.serial(p.getHostWithGuest(), "res" + File.separator + "serialHistory.bin");
                p.exportHostWithGuestCsv("res" + File.separator + "exportation.csv");
            }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}