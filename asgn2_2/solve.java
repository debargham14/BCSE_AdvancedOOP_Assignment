package java_assignment.asgn2_2;

import java.io.*;
import java.util.*;

//wrapper class to hold the value in kilometer
class Kilometer {
    protected double value; //to hold the value in Kilometers
    public Kilometer (Double v) {value = v;} //construtor to initialise 
    public double returnValue () {return value;} //public method to acess the value
    public String toString () {return Double.toString(value);} //to convert the value to string
}

//wrapper class to hold the value in miles
class Mile {
    protected double value; //to hold the value in Kilometers
    public Mile (Double v) {value = v;} //construtor to initialise 
    public double returnValue () {return value;} //public method to acess the value
    public String toString () {return Double.toString(value);} //to convert the value to string
}

//designing the class metrics
class Metrics {
    //necessary attributes
    protected Kilometer km = null;
    protected Mile mi = null;

    //constructor to initialise the Metrics object with kilometer value
    public  Metrics (Kilometer obj) { km = new Kilometer(obj.returnValue());}
    //constructor to initialise the Metrics object with Miles value
    public  Metrics (Mile obj) {mi = new Mile (obj.returnValue());}
    
    //method for conversion
    public void convert () {//type driven conversion
        if(km == null) {
            km = new Kilometer (mi.returnValue() * 1.5);
            System.out.println ("Value in Kilometers :- " + km); 
        }
        else {
            mi = new Mile (km.returnValue() / 1.5);
            System.out.println ("Value in Miles :- " + mi);
        }
    }
}

//class to test the functionality
class Solve {
    public static void main (String args []) {
        Scanner sc = new Scanner (System.in);
        while(true) {
            int ch;
            System.out.print("1. Kilometers to Miles\n2. Miles to Kilometers\n3. exit\nEnter Choice :- ");
            ch = sc.nextInt();
            switch (ch) {
                case 1: System.out.print("Enter the value in kilometers :- ");
                        double d = sc.nextDouble(); 
                        Kilometer km = new Kilometer(d);
                        Metrics obj = new Metrics (km);
                        obj.convert();
                        break;
                case 2: System.out.print("Enter the value in Miles :- ");
                        double d1 = sc.nextDouble(); 
                        Mile mi = new Mile (d1);
                        Metrics obj1 = new Metrics (mi);
                        obj1.convert();
                        break;
                default: System.exit(0);
            }
        }
    }
}