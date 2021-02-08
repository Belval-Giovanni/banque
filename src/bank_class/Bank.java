package bank_class;

import java.util.Arrays;

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
        Branch[] copy = new Branch[this.branches.length+1];
        for(int i = 0;i< this.branches.length;i++){
            copy[i] = this.branches[i];
        }
        copy[this.branches.length] = new Branch(transit);
        this.branches = copy;
        this.n++;
    }

    public void dismantle(String transit){ //permet de supprimé une succursale(branche)

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

    public int getN() {
        return n;
    }


    public Branch[] getBranches() {
        return branches;
    }

    public Branch getBranche(String transit) {
        for(int i = 0;i<this.branches.length;i++){
            if (this.branches[i].getTransit().equals(transit)){
                return branches[i];
            }
        }
        return null;
    }

    public void processTransaction(String line){
        String[] order = order(line);
        System.out.println(Arrays.toString(order));

        boucle:switch (order[0]){
            case "build":
                if (verificationBank(order[1])){
                    break boucle;
                }
               this.build(order[1]);
               break;

            case "dismantle":
                this.dismantle(order[1]);
                break;

            case "open":
                this.getBranche(order[1]).open(order[2]);
                break;

            case "close":
                this.getBranche(order[1]).close(order[2]);
                break;

            case "deposit":
                this.getBranche(order[1]).getBankAccount(order[2]).deposit(Double.parseDouble(order[3]));
                break;

            case "withdraw":
                this.getBranche(order[1]).getBankAccount(order[2]).withDraw(Double.parseDouble(order[3]));
                break;

            case "bonus":
                this.bonus(order[1]);
                break;

            case "report":
                this.report();
                break;

            //cas de test


            case "amount": // pour le debug , sert a voir le montant dans un compte
                this.getBranche(order[1]).getBankAccount(order[2]).affichSolde();
                break;


        }
    }

    private void bonus(String amount){
        Bank.BONUS = Double.parseDouble(amount);
    }

    private void report(){
        System.out.println("+++ Bank Report +++");
        for(int j = 0;j<this.branches.length;j++){
            System.out.println("### Branch "+this.branches[j].getTransit()+" ###");
            System.out.println("    "+this.branches[j].getNbAccount()+" active accounts.");
            for(int i=0;i<this.branches[j].getNbAccount();i++){
                System.out.println("*** Account "
                        +this.branches[j].getTransit()+":"+this.branches[j].getBankAccounts()[i].getNumber());
                System.out.println("    Balance = "+this.branches[j].getBankAccounts()[i].getAmount()+"$");
                System.out.println("    Last operation "+this.branches[j].getBankAccounts()[i].getLastOperation());
            }
            System.out.println("    Total deposits = "+this.branches[j].totalDeposit()+'$');
            System.out.println("    "+this.branches[j].getNbClosedAccount()+" closed accounts.");
            System.out.println("    Total closed accounts = "+this.branches[j].getClosed_amount()+'$');
            System.out.println("####################");
        }
        System.out.println("Bank total deposits = "+this.bankTotalDeposit());
    }

    private boolean verificationBank(String transit) //vont contenir toute les verifications de Bank et
                                                     //de meme pour les autres.
    {
        if (this.branches.length == 0){
            return false;
        }
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
        };
        return somme;
    }

    public static double getBONUS(){
        return Bank.BONUS;
    }


    public String[] order(String line){  // a optimisé
        String[] order = {"","","",""};
        int i = 0;
        for(int j = 0;j<4;j++){
            while((i<line.length())&&(line.charAt(i) != ' ')){
                order[j] += line.charAt(i);
                i++;
            }
            i++;
        }
        return order;
    }
}
