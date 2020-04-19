package yal.arbre.declarations;


import yal.arbre.ArbreAbstrait;
import yal.arbre.BlocDInstructions;
import yal.arbre.instructions.Boucle;
import yal.arbre.instructions.Condition;
import yal.arbre.instructions.Retourne;
import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeFonction;
import yal.tableSymboles.SymboleFonction;
import yal.tableSymboles.TDS;

public class DeclarationFonction extends Declaration {
    private String idf;
    private BlocParametres parametres;
    private BlocDeclarations declarations;
    private BlocDInstructions instructions;
    private int numeroBloc;


    public DeclarationFonction(String idf, int numeroBloc, BlocParametres parametres, BlocDeclarations declarations, BlocDInstructions instructions, int noLigne) {
        super(noLigne);

        this.idf = idf;
        this.numeroBloc = numeroBloc;
        this.parametres = parametres;
        this.declarations = declarations;
        this.instructions = instructions;

        gererBlocsInexistants();

        /* Il faut donner aux bloc de déclarations leurs numéros de bloc pour qu'ils ajoutent dans la bonne TDS. */
        this.declarations.setNumeroBloc(numeroBloc);
        this.parametres.setNumeroBloc(numeroBloc);

        // On ajoute la déclaration de cette fonction dans la TDS du PP; on y crée l'entrée et le symbole.
        ajouterTDS(0);

        /* Ajout des variables et des paramètres dans la TDS. */
        System.out.println("DeclFonc ajouterTDS : "+numeroBloc);
        this.declarations.ajouterTDS();
        this.parametres.ajouterTDS();

        // Set du nb de variables locales dans le retourne
        setRetourne();
    }

    /**
     * Il peut arriver qu'il n'y ait ni déclarations, ni paramètres.
     * Dans ce cas, on va les instancier tout de même pour simplifier le code de vérifications.
     */
    private void gererBlocsInexistants() {
        /* S'il n'y a pas de bloc de déclarations... */
        if (declarations == null) declarations = new BlocDeclarations(noLigne);

        /* S'il n'y a pas de paramètres... */
        if (parametres == null) parametres = new BlocParametres(noLigne);


    }


    /**
     * On ajoute la fonction dans les déclarations du PP.
     *
     * On configure l'entrée et le symbole avec le nombre de paramètres.
     * Configurer l'entrée sert à distinguer l'idf de cette fonction de l'idf d'une variable ou d'une fonction à plusieurs
     * paramètres.
     * Configurer le symbole permet de choisir la bonne fonction selon le nombre de paramètres.
     *
     * @param numeroBloc : 0 car une déclaration de fonction ne peut que se trouver dans le PP.
     */
    @Override
    public void ajouterTDS(int numeroBloc){
        TDS tds = TDS.getInstance();

        int nbParametres = parametres.getNbParametres();

        entree = new EntreeFonction(idf, noLigne, nbParametres);
        symbole = new SymboleFonction(idf, nbParametres);
        ((SymboleFonction)symbole).setNumBloc(numeroBloc);

        // Ajout du symbole dans la TDS
        tds.ajouter(numeroBloc, entree, symbole);
        tds.ajouterNouvelleTDS();

    }

    /**
     * Set le nombre de variables locales dans le retourne de la fonction.
     */
    private void setRetourne(){
        int nbVariablesLocales = declarations.getNbConstantes();
        int nbParametres = parametres.getNbParametres();
        for (ArbreAbstrait i : instructions) {
            String type = i.getType();
            //Il faut mettre le nb de variables locales dans les retournes imbriqués aussi
            // c'est-à-dire dans Boucle et Condition.
            switch (type) {
                case "Retourne" :
                    ((Retourne) i).setNbVariablesLocales(nbVariablesLocales);
                    ((Retourne) i).setNbParametres(nbParametres);
                    break;
                case "Condition" :
                    Condition c = (Condition) i;
                    ArbreAbstrait blocSi = c.getBlocSi();
                    ArbreAbstrait blocSinon = c.getBlocSinon();
                    if (c.contientRetourne()) {
                        if ((blocSi.contientRetourne())) {
                            if (blocSi.getType().equals("BlocDInstructions")) {
                                BlocDInstructions b = (BlocDInstructions)blocSi;
                                for (ArbreAbstrait k : b) {
                                    if (k.getType().equals("Retourne")) {
                                        ((Retourne) k).setNbVariablesLocales(nbVariablesLocales);
                                        ((Retourne) k).setNbParametres(nbParametres);
                                    }
                                }
                            }
                        }
                        if (blocSinon != null) {
                            if ((blocSinon.contientRetourne())) {
                                if (blocSinon.getType().equals("BlocDInstructions")) {
                                    BlocDInstructions b = (BlocDInstructions) blocSinon;
                                    for (ArbreAbstrait k : b) {
                                        if (k.getType().equals("Retourne")) {
                                            ((Retourne) k).setNbVariablesLocales(nbVariablesLocales);
                                            ((Retourne) k).setNbParametres(nbParametres);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "Boucle":
                    Boucle boucle = (Boucle)i;
                    ArbreAbstrait bloc = boucle.getBloc();
                    if (bloc.contientRetourne()){
                        if (bloc.getType().equals("BlocDInstructions")){
                            for (ArbreAbstrait k : (BlocDInstructions)bloc){
                                if (k.getType().equals("Retourne")){
                                    ((Retourne) k).setNbVariablesLocales(nbVariablesLocales);
                                    ((Retourne) k).setNbParametres(nbParametres);
                                }
                            }
                        }
                    }
                    break;

            }
        }
    }



    @Override
    public void verifier() {

        if (instructions==null){
            String messageExplicite = "Une fonction doit comporter des instructions.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        }

        /* On vérifie les variables du bloc de déclarations. */
        declarations.verifier();

        /* On vérifie les instructions à l'intérieur du bloc de la fonction (== on vérifie les variables). */
        TDS.getInstance().entreeBloc(numeroBloc);
        instructions.verifier();
        TDS.getInstance().sortieBloc();

        /* Si la fonction ne contient pas retourne, ce n'est pas OK. */
        if (!contientRetourne()){
            String messageExplicite = "La fonction doit retourner un entier.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        }

    }

    @Override
    /**
     * On y génère l'étiquette et le code MIPS des instructions à l'intérieur de la fonction.
     * Le idf de l'étiquette est dans le symbole (nécessaire pour AppelFonction).
     *
     * Empile l'adresse de retour et les variables locales de la fonction.
     */
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        // Génère l'étiquette
        mips.append( ((SymboleFonction)symbole).getNomEtiquette());
        mips.append(" : \n");

        // Code pour empiler toutes les informations du bloc
        mips.append(toMIPSEntree());

        mips.append(instructions.toMIPS());

        // Code pour sortir de la fonction
        // C'est en fait la classe Retourne qui s'en occupe
        return mips.toString();
    }

    private String toMIPSEntree(){
        StringBuilder mips = new StringBuilder();

        // Paramètres empilés précédemment par AppelFonction avant le branchement.

        // Adresse de retour empilée.
        mips.append("\t # On empile l'adresse de retour pour retourner à l'endroit de l'appel. \n");
        mips.append("\t move $v0, $ra \n");
        mips.append(toMIPSEmpiler());
        mips.append("\n");

        // TODO : gérer les tableaux.
        int nbVariables = declarations.getNbConstantes();

        if (nbVariables != 0) {
            mips.append(toMIPSVariablesLocales());
        }

        return mips.toString();
    }

    private String toMIPSVariablesLocales(){
        StringBuilder mips = new StringBuilder();

        int placeAReserver = declarations.getPlaceAReserver();
        mips.append("\t # Réservation de place pour les variables locales \n");
        mips.append("\t add $sp, $sp, -");
        mips.append(placeAReserver);
        mips.append("\n ");
        mips.append("\n");
        return mips.toString();
    }


    /**
     * La valeur à empiler doit être dans $v0.
     * @return empiler en MIPS.
     */
    private String toMIPSEmpiler(){
        StringBuilder mips = new StringBuilder();

        mips.append("\t sw $v0, 0($sp) \n");
        mips.append("\t add $sp, $sp, -4 \n");

        return mips.toString();
    }

    @Override
    public String getType() {
        return "DeclarationFonction";
    }

    @Override
    public boolean contientRetourne() {
        return instructions.contientRetourne();
    }


    /**
     * Pour vérifier qu'il y a bien les instructions écrites dans la fonction
     */
    public void toSoutDebug(){
        System.out.println("Ces sout EXCLUSIFS vous sont proposés par la classe Fonction de yal.arbre !");
        System.out.println(idf);
        System.out.println(instructions.toMIPS());
    }

}
