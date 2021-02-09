import devoir1_p1212021.Bank;

import java.util.Scanner;

// class to manage bank transactions as defined in Devoir 1 IFT1025
public class Main {

    public static void main( String[] args ) {

       // Bank bank = new Bank();
        // bank.order("deposit 12 123 43");



        // Scanner connected to the System.in
        Scanner myScanner = new Scanner( System.in );

        // String for each transaction line
        String line = "Go!";

        // Bank instance to be implemented
        Bank bank = new Bank();

        // while there are transactions,
        //     read the line
        //     process the corresponding transaction
        while( ! line.equals( "end" ) ) {
            line = myScanner.nextLine();
            bank.processTransaction( line );
        }
    }
}

/* principal du probleme terminé , il manque juste a redigé les rapport et optimisé les fonctions pour
* qu'elle considere les cas d'erreurs (retrain negatif etc)*/
