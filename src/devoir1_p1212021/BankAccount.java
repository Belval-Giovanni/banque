package devoir1_p1212021;

public class BankAccount {

    // attributs privées:
    private final String transit; //numero de transit
    private final String number; //numero de compte
    private double amount; //montant disponible
    private String lastOperation;//derniere oppération effectué et son montant

    //constructeur :
    public BankAccount(String transit,String number){
        this.transit = transit;
        this.number = number;
        this.amount = Bank.getBONUS();
    }


    //methode :

    protected void deposit(double amount) //on depose le montant amount sur le compte
    {
        if (amount<0)//on ne peux pas déposé un montant negatif
        {
            return;
        }
        this.amount+=amount;
        this.lastOperation = "deposit "+amount+"$"; //on met a jour la derniere opération

    }

    protected void withDraw(double amount) //on retire de l'argent sur le compte
    {
        if (amount<0 || amount>this.getAmount())//on ne peux pas retiré un montant négatif
                                                //ou que l'on ne possede pas sur son compte
        {
            return;
        }
        this.amount-=amount;
        this.lastOperation = "withdraw "+amount+"$";//on met a jour la derniere opération
    }

    public String getNumber() {
        return number;
    }

    public double getAmount() {
        return amount;
    }

    protected String getLastOperation() {
        return lastOperation;
    }

}
