package devoir1_p1212021;

public class Branch {

    // attributs privates :
    private final String transit; //numero de transit du compte ( et de sa succursalle)
    private BankAccount[] bankAccounts ; //tableau de refference des compte liée a cette succursale
    private double closed_amount; //montant des comptes fermés
    private int nbClosedAccount; //nombre de compte fermé.


    // constructeur :
    public Branch(String transit){
        this.transit = transit;
        this.bankAccounts = new BankAccount[0];
        this.closed_amount = 0;
        this.nbClosedAccount = 0;

    }

    //methode de la classe :
    public void open(String number) //ouvre un nouveau compte de numero number
    {
        if(verificationNumber(number)) //si le numero existe deja alors on ne fait rien
        {
            return;
        }

        //sinon on cree un nouveau tableau qui va contenir toute les compte deja existante
        //et la derniere instance qui va être créé.
        BankAccount[] copy = new BankAccount[this.bankAccounts.length+1];

        //on copie tout les comptes dans ce nouveau tableau
        for(int i = 0;i< this.bankAccounts.length;i++)
        {
            copy[i] = this.bankAccounts[i];
        }
        //et je rajoute un dernier élément dans le tableau qui est le compte que
        //je viens d'ouvrir.
        copy[this.bankAccounts.length] = new BankAccount(this.transit,number);
        this.bankAccounts = copy;

    }


    public void close(String number) //ferme le compte de numero number
    {
        if (verificationNumber(number)) {
            if (this.bankAccounts.length < 1) {
                return;
            } // cas ou il n'y a pas de compte.

            if (this.bankAccounts.length == 1) //cas ou il n'y a q'un seule compte.
            {
                if (this.bankAccounts[0].getNumber().equals(number)) {
                    this.closed_amount += this.bankAccounts[0].getAmount();
                    this.nbClosedAccount += 1; //on augmente le nombre de compte fermé
                    this.bankAccounts = new BankAccount[0];//on cree un tableau de compte vide
                    return;
                }
            }


            int position = -1;//position represente l'index de bankAccounts qu'on veux suprimmé
            BankAccount[] copy = new BankAccount[this.bankAccounts.length - 1];

            //on cherche la position du compte que l'on veut supprimé dans bankAccounts
            for (int i = 0; i < bankAccounts.length; i++)
            {
                if (bankAccounts[i].getNumber().equals(number)) {
                    position = i;
                }
            }
            if (position == -1) { //dans le cas ou le numéro de compte n'existe pas
                return;
            }

            //on va copier dans un tableau toute les éléments de bankAccounts sauf
            // cellui a la position précedemment trouvé.

            if (position != 0 && position != bankAccounts.length - 1) {
                for (int i = 0; i < position; i++) {
                    copy[i] = bankAccounts[i];
                }
                for (int i = position + 1; i < bankAccounts.length; i++) {
                    copy[i - 1] = bankAccounts[i];
                }
            }
            if (position == 0) {
                for (int i = 1; i < this.bankAccounts.length; i++) {
                    copy[i - 1] = bankAccounts[i];
                }
            }
            if (position == this.bankAccounts.length - 1) {
                for (int i = 0; i < this.bankAccounts.length - 1; i++) {
                    copy[i] = bankAccounts[i];
                }
            }
            //on rajoute le montant du compte supprimé dans closed_amount
            this.closed_amount += this.bankAccounts[position].getAmount();

            this.nbClosedAccount += 1;//on augmente le nombre de compte supprimé
            this.bankAccounts = copy;
        }

    }

    public String getTransit() //renvoi le numero de transit de la branche
    {
        return transit;
    }

    public BankAccount getBankAccount(String number) //renvoi l'instance du compte de numero number
    {
        for(int i = 0;i<this.bankAccounts.length;i++){
            if (this.bankAccounts[i].getNumber().equals(number)){
                return bankAccounts[i];
            }
        }
        return null;
    }

    public String totalDeposit() //argent total des compte ouvert
    {
        double somme = 0;
        for(int i = 0; i<this.bankAccounts.length;i++){
            somme += this.bankAccounts[i].getAmount();
        }
        return ""+somme;
    }

    public boolean verificationNumber(String number)
    //verification d'un numéro de compte fourni en argument.
            //renvoi true si le compte de numéro donné en argument existe deja.
    {
        for(BankAccount account : this.bankAccounts){
            if (account.getNumber().equals(number))
            {
                return true;
            }
        }
        return false;
    }



    public int getNbAccount() //renvoi le nombre de compte ouvert de la branche
    {
        return this.bankAccounts.length;
    }

    public BankAccount[] getBankAccounts() //renvoi le tableau de compte d'une branche
    {
        return bankAccounts.clone();
    }

    public double getClosed_amount()  //retourne le montant total dans les comptes fermé
    {
        return closed_amount;
    }

    public int getNbClosedAccount() //renvoi le nombre de compte fermé dans la branche.
    {
        return nbClosedAccount;
    }

}
