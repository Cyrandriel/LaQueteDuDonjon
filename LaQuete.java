public class LaQuete{
    public static void main(String[] args) {
        System.out.println("============LA QUETE DU DONJON============");
        System.out.println("Bienvenue dans ce fabuleux jeu, saurez vous trouver le trésor avant la fin ?");
        menu();
        System.out.println("Aurevoir o/");
        
    }

//LE MENU

    public static void menu() {
        String choix;
        
        do{
            System.out.println("\nChoisir une proposition :");
            System.out.println("(1) Jouer       (2) Règle du jeu        (3)Quitter");
            System.out.println("Insérer votre choix : ");
            choix = Lire.S();
            if(choix.equals("2"))
                Biblio.regleJeu();

        }while(!choix.equals("1") && !choix.equals("3") );
        if(choix.equals("1"))
            leJeu(choixVariation());
        else System.out.println("Vous avez raison.. de toute façon, vous auriez échouer :)");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
//LES VARIATIONS
    public static String choixVariation() {
        String choix, valideLeChoix;
        boolean choixValide = false;
        do{
            System.out.println("\nChoix de la variation : ");
            System.out.println("(0)Definition des différentes variations");
            System.out.println("(1)Niveau normal       (2)Niveau difficile     (3)Pas de pitié pour les lâches  (4)Cartographe");
            choix = Lire.S();
            if(choix.equals("0"))
                Biblio.regleVariation();
            if(choix.equals("1") || choix.equals("2") || choix.equals("3") || choix.equals("4")){
                System.out.println("Valider le choix ? (O)Oui");
                valideLeChoix = Lire.S();
                if(valideLeChoix.equals("O"))
                    choixValide=true;
            }
            
        }while(!choixValide);
        return choix;
    }

//LE JEU
    public static void leJeu(String niveau) {
        char[][] carte = Biblio.creationCarte(niveau);
        int[] emplacementJoueur = Biblio.emplacement(1, 0); //création des emplacements

        int[] emplacementTresor = Biblio.emplacement(Biblio.entierAleatoire(0, carte.length-1),Biblio.entierAleatoire(0, carte[0].length-1));
        boolean tresorTrouver = false;
        int[] bourse = Biblio.creationBourse(niveau);
        int tour = 0;
        do{
            tour++;
            System.out.println("Vous etes au tour " + tour);
            System.out.println("\nVoici le contenue de votre bourse :");
            Biblio.afficherBourse(bourse);

            emplacementJoueur = Biblio.deplacement(carte, emplacementJoueur);
            bourse = combat(bourse, emplacementJoueur, emplacementTresor, niveau, carte); 
            
            if(bourse[0] == 0) // Voir si le trésor est trouvé
                tresorTrouver = true;
            
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        }while(!tresorTrouver && tour<24);
        if(tresorTrouver)
            victoire();
        else
            defaite();

    }




//COMBAT
    public static int[] combat(int[] bourse, int[] emplacementJoueur, int[] emplacementTresor, String niveau, char[][] carte) {
        
        int niveauMonstre = Biblio.entierAleatoire(1, 5), position;
        String[] element = new String[]{"feu","eau","terre","air"};
        String elementMonstre = element[Biblio.entierAleatoire(0, 3)];

        System.out.println(" le monstre est d'element " + elementMonstre+" et de niveau " + niveauMonstre); // affichage du monstre

        if(Biblio.choixAffronter().equals("O")){
            position = Biblio.tirage(bourse, elementMonstre, niveauMonstre); //la position dans la bourse où on ajoute une gemme.
            bourse[position]++;
            if(position>0){ // Si la gemme n'est pas une maudite
                System.out.println("Vous fouillez la salle ");
                if(niveau.equals("4"))
                    carte[emplacementJoueur[0]][emplacementJoueur[1]]= 'o';
                if(emplacementJoueur[0]==emplacementTresor[0] && emplacementJoueur[1]==emplacementTresor[1]){ //Si le joueur est à l'emplacement du tresor
                    System.out.println("le tresor a été trouvé");
                    bourse[0] = 0; // il n'y a que si le tresor est trouvé que les maudites sont a 0.
                } else System.out.println("Vous ne trouvez rien, vous continuez donc votre chemin");
            }
        } else { if(niveau.equals("3") && Biblio.entierAleatoire(0, 10)<3){
                    System.out.println("Vous échouez votre fuite, vous prenez une attaque et obtenez une gemme maudite en plus");
                    bourse[0]++;
            } else System.out.println("Vous prenez la fuite");
        }
        
      
        return bourse;
    }

//MOT DE FIN

    public static void victoire() {
        System.out.println("         __________\n        /\\____;;___\\\n       | /         /\n       `. ())oo() .\n        |\\(%()*^^()^\\\n       %| |-%-------|\n      % \\ | %  ))   |\n      %  \\|%________|"); 
        System.out.println("Tu es maintenant couvert de trésor, j'y croyais pas mais félicitation !");
    }

    public static void defaite() {
        System.out.println("Ce n'est qu'une défaite parmis tant d'autres !");
        System.out.println("           _..._\n        ,'     `.\n      ,'         `.\n    ,'    _   ,-.  `.\n    |    (_)  `-'   |\n    |        >      |\n    |     ,----.    |\n    |    /,-'''-.\\  |\n    `.  |/      '' ,'\n      `.         ,'\n        `._____,'");
        System.out.println("Oui je sais que tu es triste..");
    }

}
