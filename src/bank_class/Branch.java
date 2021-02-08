package bank_class;

public class Branch {

    // attributs privates :
    private final String transit; //numero de transit du compte ( et de sa succursalle)
    private BankAccount[] bankAccounts ; //tableau de refference des compte liée a cette succursale
    private double closed_amount;
    private int nbClosedAccount;


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
        if(verificationNumber(number)){
            return;
        }
        BankAccount[] copy = new BankAccount[this.bankAccounts.length+1];
        for(int i = 0;i< this.bankAccounts.length;i++){
            copy[i] = this.bankAccounts[i];
        }
        copy[this.bankAccounts.length] = new BankAccount(this.transit,number);
        this.bankAccounts = copy;

    }


    public void close(String number) //ferme le compte de numero number
    {
        if (verificationNumber(number)){
            if (this.bankAccounts.length <1) {return ;} // cas ou il n'y a pas de compte.

            if (this.bankAccounts.length == 1) {        //cas ou il n'y a q'une seule compte.
                if (this.bankAccounts[0].getNumber().equals(number)){
                    this.closed_amount += this.bankAccounts[0].getAmount();
                    this.nbClosedAccount += 1;
                    this.bankAccounts = new BankAccount[0];
                    return;
                }
            }
        }


        int position = -1;//position represente l'index de bankAccounts qu'on veux suprimmé
        BankAccount[] copy = new BankAccount[this.bankAccounts.length-1];
        for(int i=0;i<bankAccounts.length;i++){
            if(bankAccounts[i].getNumber().equals(number)){
                position = i;
            }
        }
        if (position == -1){ //dans le cas ou le numéro de compte n'existe pas
            return;
        }
        if (position != 0 && position != bankAccounts.length-1){
            for(int i=0;i<position;i++){
                copy[i] = bankAccounts[i];
            }
            for (int i = position+1;i<bankAccounts.length;i++){
                copy[i-1] = bankAccounts[i];
            }
        }
        if (position == 0 ){
            for(int i=1;i<this.bankAccounts.length;i++){
                copy[i-1] = bankAccounts[i];
            }
        }
        if (position == this.bankAccounts.length-1 ){
            for(int i=0;i<this.bankAccounts.length-1;i++){
                copy[i] = bankAccounts[i];
            }
        }
        this.closed_amount += this.bankAccounts[position].getAmount();
        this.nbClosedAccount += 1;
        this.bankAccounts = copy;

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

    private boolean verificationNumber(String number)
    //verification d'un numéro de compte fourni en argument.
    {
        //deux comptes ne peuvent avoir le meme attribut number dans une meme branche
        for(BankAccount account : this.bankAccounts){
            if (account.getNumber().equals(number))
            {
                return true;
            }
        }
        //un numéro de compte ne peux etre negatif ou nul
        if(Integer.parseInt(number)<=0){
            return true;
        }
        return false;
    }



    public int getNbAccount()
    {
        return this.bankAccounts.length;
    }

    public BankAccount[] getBankAccounts() {
        return bankAccounts.clone();
    }

    public double getClosed_amount() {
        return closed_amount;
    }

    public int getNbClosedAccount() {
        return nbClosedAccount;
    }

}
