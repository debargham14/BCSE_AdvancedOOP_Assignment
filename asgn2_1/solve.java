//importing the necessary packages
package java_assignment.asgn2_1;

import java.util.*;
import java.io.*;

//designing the class BankAccount
class BankAccount {
    //necessary attributes
    protected int accountNumber;
    protected double balance;
    protected static double interestRate;

    //constructor to initialize the BankAccount
    public BankAccount () {
        accountNumber = 0;
        balance = 0;
        interestRate = 0;
    }

    //overloaded construtor to initialise during object creation
    public BankAccount(int accNo, double bal, double itr) {
        accountNumber = accNo;
        balance = bal;
        interestRate = itr;
    }

    //getData method to get the relevant account details
    public void getData () {
        Scanner sc = new Scanner(System.in);
        System.out.print ("Enter the Account Number :- ");
        accountNumber = sc.nextInt(); //taking the account number as input

        System.out.print ("Enter the current Balance :- ");
        balance = sc.nextDouble(); //taking the balance as input
        System.out.println ("Data Entered Successfully !");
    }

    //method to initialise the interest rate
    static public void initInterestRate () { //defined static method because it needs to be modified for the entire class 
        Scanner sc = new Scanner (System.in);
        System.out.print ("Enter the interest Rate :- ");
        interestRate = sc.nextDouble();
        System.out.println ("Interest Rate initialised successfully !");
    }

    //method to modify the interest Rate
    static public void modifyInterestRate () {
        Scanner sc = new Scanner (System.in);
        System.out.print ("Enter the new interest Rate :- ");
        interestRate = sc.nextDouble();
        System.out.println ("Interest Rate modified successfully !");
    }

    //method to display the interest rate 
    static public void displayInterestRate () {System.out.printf ("Interest Rate :- %f\n", interestRate);}

    //method to return balance 
    public double returnBalance (int year, int months) {
        double time = year + (float)months / (float)12; //calculating the time required
        double interestAmount = ( balance * time * interestRate ) / 100; //calculating the interest
        System.out.println ("Interest Amount over the total time period :- " + interestAmount);
        return interestAmount + balance; //return the amount
    }
}

//designing the test class to implement the functionalities
class Solve {
    public static void main (String args []) {
        BankAccount obj = new BankAccount();
        Scanner sc = new Scanner (System.in);
        while(true) {
            int ch;
            System.out.print("1. Enter Account Details\n2. Initialise Interest Rate\n3. Modify Interest Rate\n4. Display Interest Rate\n5. Calculate Balance and Interest Amount\n6. exit\nEnter Choice :- ");
            ch = sc.nextInt();

            switch (ch) {
                case 1: obj.getData(); //to get the relevant bank account details
                        break;
                case 2: BankAccount.initInterestRate(); // to initialise the interest rate
                        break;
                case 3: BankAccount.modifyInterestRate(); //to modify the interest rate
                        break;
                case 4: BankAccount.displayInterestRate(); //to display the interest rate 
                        break;
                case 5: System.out.print("Enter Number of Years :- ");
                        int year = sc.nextInt();
                        System.out.print ("Enter Number of months :- ");
                        int months = sc.nextInt();
                        double balance = obj.returnBalance(year, months);
                        System.out.println ("Total Amount :- " + balance); //to find balance and interest amount
                        break;
                case 6: System.exit(0); //exit out of the system
            }
        }   
    }
}
