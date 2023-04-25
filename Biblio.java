public class Biblio {
//LES REGLES
    public static void regleJeu() {
        System.out.println("Ceci sont les regle");
    }
//REGLE VARIATION
    public static void regleVariation() {
        System.out.println("\nNiveau normal : aucune variation, le jeu est normal");
        System.out.println("Niveau difficile : nombre de gemme et la taille de la carte sont différente de normal");
        System.out.println("Pas de pitié pour les lâches : il y a une chance que l'esprit attaque en cas de fuite, ce qui ajoute une gemme maudite dans la bourse");
        System.out.println("Cartographe : Les salles déjà fouillé sont affiché sur la carte");
    }

//EMPLACEMENT  (0 Colonne/ 1 Ligne)
    public static int[] emplacement(int colonne, int ligne){
        return new int[]{colonne,ligne};
    }

//CREATION BOURSE ORIGINE
    public static int[] creationBourse(String niveau){ 
        if(niveau.equals("2"))
            return new int[]{7,3,3,3,3};////
        else return new int[]{5,3,3,3,3}; //nombre de gemme de chaque element : Maudite Feu Eau Terre Air
    }
    
//AFFICHAGE DE LA BOURSE
    public static void afficherBourse(int[] bourse) {
        System.out.println(" Gemme maudite : " + bourse[0]);
        System.out.println(" Gemme feu : " + bourse[1]);
        System.out.println(" Gemme eau : " + bourse[2]);
        System.out.println(" Gemme terre : " + bourse[3]);
        System.out.println(" Gemme air : " + bourse[4]);
        System.out.println();
    }

//CARTE
    public static char[][] creationCarte(String niveau) {
        int colonne = 3, ligne =5;
        if(niveau.equals("2")){////
            colonne++;
            ligne++;
        }
        char[][] carte = new char[colonne][ligne];

        for(int i=0;i<ligne;i++)
            for(int j =0; j<colonne;j++)
                carte[j][i] = ' ';
        return carte;
    }

    public static void afficherCarte(char[][] carte, int[] emplacementJoueur) {
        for(int i=carte[0].length-1;i>=0;i--){
            System.out.print("|");
            for(int j =0; j<carte.length;j++){
                if(i==emplacementJoueur[1] && j==emplacementJoueur[0]){
                    System.out.print("x|");
                }else System.out.print(carte[j][i] + "|");
            }
            System.out.println();
        }
    }

// RANDOM
    public static int entierAleatoire(int minimum, int maximum){
        return (int)(Math.random()*((maximum-minimum)+1)+minimum);
    }
    
// DEPLACEMENT   (0 Colonne/ 1 Ligne)
    public static int[] deplacement(char[][] carte, int[] emplacementJoueur) {
        System.out.println();
        afficherCarte(carte,emplacementJoueur);

        boolean H = true,G = true,D = true,B = true;

        System.out.print("Deplacement disponible : ");
        if(emplacementJoueur[1] < carte[0].length-1){ //Cette partie est pour savoir quels propositions seront affiché
            System.out.print("(H)HAUT  ");
        }else H = false;
        
        if(emplacementJoueur[0] > 0){
            System.out.print("(G)GAUCHE  ");
        }else G = false;

        System.out.print("(I)IMMOBILE  ");

        if(emplacementJoueur[0] < carte.length-1){
            System.out.print("(D)DROITE  ");
        }else D = false;

        if(emplacementJoueur[1] > 0){
            System.out.print("(B)BAS  ");
        } else B = false;

        String choixDeplacement;
        boolean valide;

        do{
            valide = true;
            System.out.println("choisir un deplacement valide ");
            choixDeplacement = Lire.S();
            if(choixDeplacement.equals("H") && H) //Les proposition qui ne sont pas afficher sont invalides
                emplacementJoueur[1]+=1;
            else if (choixDeplacement.equals("D") && D)
                emplacementJoueur[0]+=1;
            else if(choixDeplacement.equals("G") && G)
                emplacementJoueur[0]-=1;
            else if (choixDeplacement.equals("B") && B)
                emplacementJoueur[1]-=1;

            else if(!choixDeplacement.equals("I")){
                System.out.println("choix invalide");
                valide = false;
            }
        }while(!valide);


        return emplacementJoueur;
    }

    public static int[] copierBourse(int[] bourseJoueur){
        int[] nouvelleBourse = {bourseJoueur[0], bourseJoueur[1], bourseJoueur[2], bourseJoueur[3], bourseJoueur[4]};    
        return nouvelleBourse;
    }

    public static int tirage(int[] bourseJoueur,String elementMonstre, int niveauMonstre) {
        int positionElementMonstre, positionElementPioche;
        int nombreValide = 0, nombreNonValide = 5, nombreBloque=0;
        int[] bourse = copierBourse(bourseJoueur);

        positionElementMonstre = elementPosition(elementMonstre);

        do{
            System.out.println();
            
            System.out.print("vous piochez : ");
            nombreBloque = 0;
            for(int i = 0; i<nombreNonValide ; i++){
                
                positionElementPioche = piochePosition(bourse);
                bourse[positionElementPioche]--;
                if(positionElementPioche == 0 || positionElementPioche == positionElementMonstre){
                    nombreBloque++;
                }
                if(positionElementPioche == positionElementMonstre){
                    nombreValide++;
                }
                

            }
            nombreNonValide-= nombreBloque;
        }while(nombreNonValide > 0 && nombreValide<niveauMonstre);

        System.out.println();
        if(nombreValide>=niveauMonstre){
            System.out.println("Vous sortez victorieux de ce combat, felicitation ! vous gagnez une gemme d'element !");
        }
        else {
            System.out.println("Vous avez perdu, en espérant que la prochaine fois ça passera, en attendant, vous avez une gemme maudite en plus");
            positionElementMonstre = 0;
        }
        return positionElementMonstre;

    }

    public static int elementPosition(String elementMonstre) {
        int position;
        switch(elementMonstre){
            case "feu" : position = 1;break;
            case "eau" : position = 2;break;
            case "terre" : position = 3;break;
            case "air" : position = 4;break;
            default : position = 666;
        }
        return position;
    }

    public static int piochePosition(int[] bourse) {
        int pioche = entierAleatoire(0, bourse[0]+bourse[1]+bourse[2]+bourse[3]+bourse[4]-1);
        int position;
        if(pioche<bourse[0]){
            System.out.print("maudite ");
            position = 0;
        }else if(pioche<bourse[0]+bourse[1]){
            System.out.print("feu ");
            position = 1;
        }else if(pioche<bourse[0]+bourse[1]+bourse[2]){
            System.out.print("eau ");
            position = 2;
        }else if(pioche<bourse[0]+bourse[1]+bourse[2]+bourse[3]){
            System.out.print("terre ");
            position = 3;
        }else{
            System.out.print("air ");
            position = 4;
        }
        return position;
        
    }


    public static String choixAffronter() {
        String choix;
        do{
            System.out.println("Voulez vous combattre (O)oui (N)non");
            choix = Lire.S();
        }while(!choix.equals("O") && !choix.equals("N"));
        return choix;
    }
}
