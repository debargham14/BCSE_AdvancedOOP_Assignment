package java_assignment.asgn2_3;
import java.util.*;
import java.io.*;

class Solve {
    //method to tokenize the string
    static public void tokenize (String s) {
        StringTokenizer st = new StringTokenizer(s, "@.");
        System.out.println ("Tokens :- ");
        while(st.hasMoreTokens()) {
            System.out.println (st.nextToken());
        }
    }

    //method to convert the string into a character array
    static public void convertToCharacterArray (String s) {
        char array[] = s.toCharArray();
        for(int i = 0; i < array.length; i++) {
            System.out.println (array[i]); //just print the characters of the array in different lines
        }
    }
    //method to check whether the String starts with The or not
    static public void checkstartwithThe (String s) {
        if(s.startsWith("The"))
            System.out.println ("Yes");
        else
            System.out.println ("No");
    }
    //method to count the number of times ansd occurs
    static public void countand (String s) {
        StringTokenizer st = new StringTokenizer(s);
        int count = 0;
        while(st.hasMoreTokens()) {
            String s1 = new String (st.nextToken());
            if(s1.equals("and"))
                count++;
        }
        System.out.println("Number Of 'and' in the sentence :- " + count);
    }
    //method to count the number of a's in the class 
    static public void counta (String s) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i)== 'a')
                count++;
        }
        System.out.println("Number of a's in the string :- " + count);
    }
    public static void main (String args[]) {
        Scanner sc = new Scanner (System.in);
        String s = new String ();
        while(true) {
            int ch;
            System.out.print ("1. Enter a new String\n2. count number of a's\n3. count number of ands\n4. check start with 'The'\n5. Convert to character array\n6. Tokenize\n7. Exit\nEnter Choice :- ");
            ch = sc.nextInt();
            switch (ch) {
                case 1: Scanner sc1 = new Scanner (System.in);
                        System.out.print ("Enter the String :- ");
                        s = sc1.nextLine();
                        break;
                case 2: counta(s);
                        break;
                case 3: countand(s);
                        break;
                case 4: checkstartwithThe(s); 
                        break;
                case 5: convertToCharacterArray(s);
                        break;
                case 6: tokenize(s);
                        break;
                default : System.exit(0);
                        break;             
            }
        }
    }
}