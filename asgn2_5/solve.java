package java_assignment.asgn2_5;
import java.util.*;

//designing the class bank account
class BankAccount {
    //placing the necessary attributes
    protected int customerId;
    protected String name;
    protected int currentLoan;
    protected String phoneNumber;
    protected boolean privileged;
    protected ArrayList<Integer> loans = new ArrayList <Integer> ();
    public static  final int creditLimit1 = 100000;
    public static final int creditLimit2 = 200000; //for privileged section
    protected static int last = 1000; //usefull for bank id generation

    BankAccount () {
        currentLoan = -1;
        privileged = false;
    } //relevant constructor
    //public method to get the data
    public int getData () {
        Scanner sc = new Scanner (System.in);
        System.out.print ("Enter Customer Name :- ");
        name = sc.nextLine();
        System.out.print ("Enter phone number :- ");
        phoneNumber = sc.nextLine();
        if(phoneNumber.length() != 10)
        {
            System.out.println ("Enter a valid phone number !");
            return 0;
        }
        last += 1;
        customerId = last;
        System.out.print ("Type of membership :-\n1. normal\n2. privileged\nEnter Choice :- ");
        int ch = Integer.parseInt(sc.nextLine());
        privileged = ch == 1 ? false : true;
        System.out.println ("Bank Account created sucessfully ! Account number :- " + customerId);
        return 1;
    }
    //method to view the credit limits
    public static void viewCreditLimit () {
        System.out.println ("Credit Limit For Normal Customer :- " + creditLimit1);
        System.out.println ("Credit Limit For Privileged Customer :- " + creditLimit2);
    }

    //method to show the loan details
    public void showLoanDetails () {
        int sum = 0;
        System.out.println ("Loans Taken So Far ------------ ");
        for(int i = 0; i < loans.size(); i++) {
            System.out.println (loans.get(i));
            sum += loans.get(i);
        }
        int avail = privileged == true ? creditLimit2 - sum : creditLimit1 - sum;
        if(privileged)
            System.out.println ("Maximum Credit Limit Allowance " + creditLimit2);
        else
            System.out.println ("Maximum Credit Limit Allowance " + creditLimit1);
        if(currentLoan == -1)
            System.out.println ("No Loan issued so far !");
        else
            System.out.println ("Last Loan Issued :- " + currentLoan);
        
        System.out.println ("Total Loan amount taken :- " + sum);
        System.out.println ("Loan amount available for issue :- " + avail);

        if(loans.size() > 0) {
            Scanner sc = new Scanner(System.in);
            System.out.print ("Are you willing to see the loan history (y/n):- ");
            String ch = sc.nextLine();
            if(ch.equals("y") || ch.equals("Y")){
                for(int i = 0; i < loans.size(); i++) {
                    System.out.printf("Loan #%d :- %d\n", i + 1, loans.get(i));
                }
            }
        }
    }

    //method to get a loan
    public void getLoan () {
        int sum = 0;
        Scanner sc = new Scanner (System.in);
        for(int i = 0; i < loans.size(); i++) {
            sum += loans.get(i);
        }        
        int avail = privileged == true ? creditLimit2 - sum : creditLimit1 - sum;
        System.out.println ("Loan amount available for issue :- " + avail);
        System.out.print ("Enter the loan amount :- ");
        currentLoan = Integer.parseInt(sc.nextLine());
        if(currentLoan < 0){
            System.out.println ("Please Enter Valid Amount !");
            return;
        }
        else if(currentLoan <= avail) //loan added sucessfully
        {
            loans.add(currentLoan);
            System.out.println ("Loan Issued Successfully !");
        }
        else
            System.out.println ("Entered amount exceeds available amount !");
        currentLoan = loans.get(loans.size() - 1);
    }

    //method to show the customer details
    public void showData () {
        System.out.println ("Customer Name :- " + name);
        System.out.println ("Customer Id :- " + customerId);
        if(privileged)
            System.out.println ("Membership Type :- privileged ");
        else
            System.out.println ("Membership Type :- Normal ");
        System.out.println ("Phone Number :- " + phoneNumber);
    }

    //public method to return id
    public int returnCustomerId() {return customerId;}
}

//class bank to test the functionalities 
class Bank {
    protected ArrayList<BankAccount> list = new ArrayList<BankAccount>();

    //public method to add a account
    public void addAccount () {
        BankAccount obj = new BankAccount();
        int res = obj.getData();
        if(res == 1) //lse it will unnecessarily keep on increasing the list size
            list.add(obj);
    }

    //public method to show a account based on id
    public void showAccount (int id) {
        boolean flag = false;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).returnCustomerId() == id)
            {
                list.get(i).showData();
                flag = true;
            }
        }
        if(!flag)
            System.out.println ("Customer Not Found !");
    }

    //public method to show  loan details of a customer
    public void showAccountLoanDetails (int id) {
        boolean flag = false;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).returnCustomerId() == id)
            {
                list.get(i).showLoanDetails();
                flag = true;
            }
        }
        if(!flag)
            System.out.println ("Customer Not Found !");
    }

    //method to see the get a loan for a customer based on id
    public void getLoan (int id) {
        boolean flag = false;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).returnCustomerId() == id)
            {
                list.get(i).getLoan();
                flag = true;
            }
        }
        if(!flag)
            System.out.println ("Customer Not Found !");
    }
}

//to test the above designed functionalities
class Solve {
    public static void main (String args []) {
        Bank obj = new Bank ();
        Scanner sc = new Scanner (System.in);
        while(true) {
            System.out.print ("1. Add Account\n2. View Account By Id\n3. Show Account Loan Details\n4. Get Loan\n5. exit\nEnter Choice :- ");
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1: obj.addAccount();
                        break;
                case 2: System.out.print ("Enter Customer Id :- ");
                        int id = Integer.parseInt(sc.nextLine());
                        obj.showAccount(id);
                        break;
                case 3: System.out.print ("Enter Customer Id :- ");
                        id = Integer.parseInt(sc.nextLine());
                        obj.showAccountLoanDetails(id);
                        break;
                case 4: System.out.print ("Enter Customer Id :- ");
                        id = Integer.parseInt(sc.nextLine());
                        obj.getLoan(id);
                        break;
                default: System.exit(0);
            }
        }
    }
}