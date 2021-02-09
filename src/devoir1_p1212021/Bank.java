package devoir1_p1212021;


public class Bank {
    //champs de la classe :

    private static double BONUS = 0;

    // attribut privates:
    private Branch[] branches;
    private int n ; // nombre de succursale

    // constructeur :
    public Bank(){
        this.branches = new Branch[0];
        this.n = 0;
    }

    //methode de la classe:
    public void build(String transit){
        if(verificationBank(transit)){
            return;
        }
        Branch[] copy = new Branch[this.branches.length+1];
        for(int i = 0;i< this.branches.length;i++){
            copy[i] = this.branches[i];
        }
        copy[this.branches.length] = new Branch(transit);
        this.branches = copy;
        this.n++;
    }

    public void dismantle(String transit){ //permet de supprimé une succursale(branche)

        if (verificationBank(transit)){
            if (this.n <1) {return ;} // cas ou il n'y a pas de branche.

            if (this.n == 1) {        //cas ou il n'y a q'une seule branche.
                if (this.branches[0].getTransit().equals(transit)){
                    this.branches = new Branch[0];
                    this.n--;
                    return;
                }
                else{               //si le nnuméro de transit est faux on ne fait rien
                    return;
                }
            }

            int position = -1;//position represente l'index de branches qu'on veux suprimmé
            Branch[] copy = new Branch[this.branches.length-1];
            for(int i=0;i<branches.length;i++){
                if(branches[i].getTransit().equals(transit)){
                    position = i;
                }
            }
            if (position == -1){ //dans le cas ou le numéro de transit n'existe pas
                return;
            }
            if (position != 0 && position != branches.length-1){
                for(int i=0;i<position;i++){
                    copy[i] = branches[i];
                }
                for (int i = position+1;i<branches.length;i++){
                    copy[i-1] = branches[i];
                }
            }
            if (position == 0 ){
                for(int i=1;i<this.branches.length;i++){
                    copy[i-1] = branches[i];
                }
            }
            if (position == this.branches.length-1 ){
                for(int i=0;i<this.branches.length-1;i++){
                    copy[i] = branches[i];
                }
            }
            this.branches = copy;
            this.n--;
        }

    }

    public Branch getBranche(String transit)
    //retourne la refference de la branche de numero de transit donné en argument.
    {
        for(int i = 0;i<this.branches.length;i++){
            if (this.branches[i].getTransit().equals(transit)){
                return branches[i];
            }
        }
        return null;
    }

    public void processTransaction(String line){
        String[] order = order(line);

        boucle:switch (order[0]){
            case "build": //ouvrir une branche
               this.build(order[1]);
               break;

            case "dismantle": //on ferme une branche
                this.dismantle(order[1]);
                break;

            case "open": //on ouvre un compte
                if (verificationBank(order[1])) //on verifit que le numero de transit est valide
                {
                    this.getBranche(order[1]).open(order[2]);
                }
                break;

            case "close": //on ferme un compte
                if (verificationBank(order[1]))//on verifit que le numero de transit est valide
                {
                    this.getBranche(order[1]).close(order[2]);
                }
                break;

            case "deposit"://on depose de l'argent sur un compte
                if (verificationBank(order[1]))//on verifit que le numero de transit est valide
                {
                    if(this.getBranche(order[1]).verificationNumber(order[2]))
                    {
                        this.getBranche(order[1]).getBankAccount(order[2]).deposit(Double.parseDouble(order[3]));
                    }
                }
                break;

            case "withdraw"://on retire de l'argent d'un compte
                if (verificationBank(order[1]))//on verifie que le numero de transit est valide
                {
                    if(this.getBranche(order[1]).verificationNumber(order[2])){
                        this.getBranche(order[1]).getBankAccount(order[2]).withDraw(Double.parseDouble(order[3]));
                    }
                }
                break;

            case "bonus":// on modifie la valeur du bonus
                this.bonus(order[1]);
                break;

            case "report":// on affiche toutes les informations de la banque.
                this.report();
                break;

            case "short-report"://affiche une version courte de report
                this.shortReport();
                break ;

            default: // cas ou la commande rentré dans la console n'est pas identifié
                break ;


        }
    }

    private void bonus(String amount){
        //sert à changer la valeur du bonus si celle-ci est valable.
        if (Double.parseDouble(amount)>=0){
            Bank.BONUS = Double.parseDouble(amount);
        }
    }

    private void report()
    //imprime dans la console toute les informations de la banque.
    {
        System.out.println("+++ Bank Report +++");
        for(int j = 0;j<this.branches.length;j++)
        //pour chaque branche on imprime des information particuliere :
        {
            // on imprime le numero de transit de la branche
            System.out.println("### Branch "+this.branches[j].getTransit()+" ###");

            //on imprime le nombres de comptes actifs de a branche
            System.out.println("    "+this.branches[j].getNbAccount()+" active accounts.");

            //puis on imprime les informations de chaque compte liée à la branche :
            for(int i=0;i<this.branches[j].getNbAccount();i++)
            {
                //on affiche le numero du compte precédé de son numero de transit
                System.out.println("*** Account "
                        +this.branches[j].getTransit()+":"+this.branches[j].getBankAccounts()[i].getNumber());

                //on affiche le solde disponible dans le compte
                System.out.println("    Balance = "+this.branches[j].getBankAccounts()[i].getAmount()+"$");

                //on affiche le type de la derniere opération et son montant
                System.out.println("    Last operation "+this.branches[j].getBankAccounts()[i].getLastOperation());
            }
            //nous n'avons plus d'information a affiché sur les comptes
            //on continue donc en affichant d'autre information sur la branche :

            //on imprime la somme total des montant disponible dans la branche
            System.out.println("    Total deposits = "+this.branches[j].totalDeposit()+'$');

            //on imprime le nombre compte fermé dans la branche
            System.out.println("    "+this.branches[j].getNbClosedAccount()+" closed accounts.");

            //on imprime la somme total des montant des compte fermé dans la branche
            System.out.println("    Total closed accounts = "+this.branches[j].getClosed_amount()+'$');
            System.out.println("####################");
        }
        //on imprime maintenant des information relative a la banque elle meme:
        ////on imprime la somme total des montant disponible dans la banque
        System.out.println("Bank total deposits = "+this.bankTotalDeposit()+'$');
        System.out.println("-------------------");
    }

    private void shortReport()
    //imprime sur la console la somme total des montant disponible dans la banque.
    {
        System.out.println("+++ Bank Report +++");
        ////on imprime la somme total des montant disponible dans la banque
        System.out.println("Bank total deposits = "+this.bankTotalDeposit()+'$');
        System.out.println("-------------------");

    }

    private boolean verificationBank(String transit) //vont contenir toute les verifications de Bank.
            //renvoi false si la branche de numero de transit fourni en argument
            //n'existe pas , et renvoi true dans le cas contraire.
    {
        //cas ou il n'y a pas de branche
        if (this.branches.length == 0){
            return false;
        }
        //boucle pour verifié si un numero de transit existe deja
        for(int i = 0;i<this.branches.length;i++){
            if(this.branches[i].getTransit().equals(transit)){
                return true;
            }
        }
        return false;
    }


    private double bankTotalDeposit() // renvoi la somme totale détenu par la banque dans les comptes ouvert.
    {
        double somme = 0;  //represente cette somme totale
        for(Branch branche : this.branches){
            somme += Double.parseDouble(branche.totalDeposit()); //on augmente somme des montant totaux de chaque
                                                                 //branches ( uniquement pour les comptes ouverts)
        }
        return somme;
    }

    public static double getBONUS(){
        return Bank.BONUS;
    }


    private String[] order(String line)
        //recupere les argument donné en ligne de commande et les insèrent dans un tableau
        //l'element a la premiere place est la requete ( build , open , etc)
        //et les argument au places suivante sont les argument respectif de chaque requete
        //qui vont être donnée en ligne de commande.
    {
        String[] order = {"","","",""};
        int i = 0;
        for(int j = 0;j<4;j++){
            while((i<line.length())&&(line.charAt(i) != ' '))
            //lorqu'il y a un espace dans l'argument reçu de la console on
            //change la position de la prochaine valeur.
            {
                order[j] += line.charAt(i);
                i++;
            }
            i++;
        }
        return order;//cette fonction renvoi un tableau dont les élement sont les requete et leur parametres.
    }
}
