import java.io.*;
import java.util.*;

class Solve {
    public static void main (String args[]) {
        //testing the wrapper class of Integer Data type
        //(i) basic data type to object auto boxing
        int i = 10;
        Integer obj = i;
        System.out.println ("Basic Data Type to Integer :- " + obj);

        //(ii)object to basic data type auto unboxing
        i = obj;
        System.out.println ("Integer object to basic data type :- " + i);
        //(iii) basic data type to string 
        String s = Integer.toString(i); //making use of the static method toString present in integer wrapper class
        System.out.println ("Basic Data type to String :- " + s);
        
        //(iv)String to numeric object
        String s1 = "1020";
        Integer obj1 = Integer.parseInt(s1); 
        obj1 += 4; //to test whether conversion has taken place properly or not
        System.out.println ("String to basic data type via wrapper class function :- " + obj1);

        //(v) object to string
        s1 = obj1.toString();
        s1 += "21";
        System.out.println ("Object to String Conversion :- " + s1);
    }   
}
