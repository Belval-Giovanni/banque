package bank_class;

public class BankAccount {

    // attributs privées:
    private final String transit;
    private final String number;
    private double amount;
    private String lastOperation;

    //constructeur :
    public BankAccount(String transit,String number){
        this.transit = transit;
        this.number = number;
        this.amount = Bank.getBONUS();
    }


    //methode :

    public void deposit(double amount){
        if (amount<0){
            return;
        }
        this.amount+=amount;
        this.lastOperation = "deposit "+amount+"$";

    }

    public void withDraw(double amount){
        if (amount<0 || amount>this.getAmount()){
            return;
        }
        this.amount-=amount;
        this.lastOperation = "withdraw "+amount+"$";
    }

    public String getNumber() {
        return number;
    }

    public double getAmount() {
        return amount;
    }

    public String getLastOperation() {
        return lastOperation;
    }

}
