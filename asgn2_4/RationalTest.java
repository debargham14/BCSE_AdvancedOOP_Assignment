import java.util.*;
import java.io.*;

//designing the class rational to hold rational numbers 
class Rational extends Number implements Comparable<Rational>{
    /*
        necessary attributes to define the constants related to Rational type
    */
    public static final Rational POSITIVE_INFINITY = new Rational (1, 0); //constant for positive infinity value of rational type
    public static final Rational NEGATIVE_INFINITY = new Rational (-1, 0); //constant for negaive infinity value of rational type;
    public static final Rational NaN = new Rational (0, 0);//constant to hold a Not a Number Type in rational type
    public static final Rational ZERO = new Rational (0, 1); //constant to hold zero in Rational type

    /*
        attributes to hold numerator and denominator of Rational Number type
    */
    private final int numerator;
    private final int denominator;

    /*public constructor to initialise a rational number type*/
    public Rational (int num, int den) {
        if(den < 0){ //to alter the sign incase someone entrers the denominator negative 
            num = -num;
            den = -den;
        }
        //else convert to necessary reduced form
        if(den == 0 && num > 0) {
            denominator = 0;
            numerator = 1; //+infinity case
        }
        else if(den == 0 && num < 0) {
            denominator = 0;
            numerator = -1; //-infinity case
        }
        else if(den == 0 && num == 0) {
            denominator = 0;
            numerator = 0;
        } else { //gcd method will be implmented in later stage
            int _gcd = gcd (num, den);
            numerator = num / _gcd;
            denominator = den / _gcd;
        }
    }

    //public method to get the numnerator
    public int getNumerator () {return numerator;}

    //public method to get the denominator
    public int getDenominator () {return denominator;}
    
    //public method to check whether the number is NaN or not
    public boolean isNaN () {return (numerator == 0 && denominator == 0);}
    
    //public method to check whether the rational number is infinite or not
    public boolean isInfinite () {return numerator != 0 && denominator == 0;}
    
    //public method to check whether the rational is finite or not
    public boolean inFinite () {return numerator != 0 && denominator != 0;}

    //public method to check whether the number is zero or not
    public boolean isZero () {return numerator == 0 && denominator != 0;}

    //public method to check whether the number is positive infinity or not
    public boolean isPostiveInf () {return denominator == 0 && numerator > 0;}

    //public method to check whether the rational is negative infinity or not
    public boolean isNegativeInf () {return denominator == 0 && numerator < 0;}

    /*
        public method to check whether two rational numbers are equal or not
        two rational numbers are equal iff
        1. both are NaN
        2. both are infinity of the same sign
        3. both are equal in their reduced form

        reduced form of a rational number is usually obtained by dividing the numerator and denominator by their absolute value of GCD
    */
    @Override
    public boolean equals (Object obj) {
        return obj instanceof Rational  && equals((Rational)obj);
    } 
    private boolean equals (Rational obj) {
        return numerator == obj.numerator && denominator == obj.denominator;
    }

    /*public method to convert the rational to a string*/
    @Override
    public String toString() {
        if(isNaN()) return "NaN";
        else if(isPostiveInf()) return "Infinity";
        else if(isNegativeInf()) return "-Infinity";
        else {
            return numerator + "/" + denominator;
        }
    } 

    /*public method convert the rational number to Float*/
    public float toFloat () {
        return floatValue();
    }

    //public method to find the hashcode
    public int hashcode () {
        return -1; //to be implmented later
    }
    
    /*
        public method to implment gcd for the Rational class 
        euclid's algorithm (iterative format) is used to do so
    */
    public static int gcd (int num, int den) {
        int a = Math.abs(num);
        int b = Math.abs(den);

        while(b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    //convert the rational value into a double value
    @Override
    public double doubleValue () {
        double num = numerator;
        double den = denominator;
        return num / den;
    }

    //convert the rational value into a float value
    @Override
    public float floatValue () {
        float num = numerator;
        float den = denominator;
        return num / den;
    }

    //public method to convert the rational into integer value
    @Override
    public int intValue() {
        if(isPostiveInf())    return Integer.MAX_VALUE;
        else if(isNegativeInf())    return Integer.MIN_VALUE;
        else if(isNaN ()) return 0;
        else return numerator / denominator; //finite condition
    }

    //public method to convert the rational into a long value
    @Override
    public long longValue () {
        if(isPostiveInf())    return Long.MAX_VALUE;
        else if(isNegativeInf())    return Long.MIN_VALUE;
        else if(isNaN ()) return 0;
        else return numerator / denominator; //finite condition
    }

    //public method to convert the rational into a short value
    @Override
    public short shortValue () {
        return (short)intValue();
    }

    //public method to compare two rational numbers
    public int compareTo (Rational obj) throws NullPointerException{
        if(obj == null) {
            throw new NullPointerException("Object to be compared with cannot be null");
        }

        if(equals(obj)) return 0;
        else if(isNaN()) //NaN considered to be greater than othe non-NaN Value
            return -1;
        else if(obj.isNaN())   return -1;
        else if(isPostiveInf() || obj.isNegativeInf()) //positive infinity is greater than all non-NaN/non-posInf value
            return 1;
        else if (isNegativeInf() || obj.isPostiveInf()) //negative infinity is smaller than all non-Nan / non-negInf value
            return -1;
        
        //else both the numbers are finite numbers so make the denominators are equal and do the comparison
        long thisnum = ((long)numerator) * obj.denominator;
        long othernum = ((long)obj.numerator) * denominator;

        //so subtarction is not preferred for comparison to avoid underflow
        if(thisnum < othernum)
            return -1;
        else if(thisnum > othernum)
            return 1;
        else return 0; //although the case is handled in equals 
    }

    /*
        we need to provide guard against getting deserialized from illegal values
        for NoN finite values it must be of the form [[0, 1], [0, 0], [1, 0], [-1, 0], [0, -1]]
        for finite values it must be in proper reducible form
        that's why the read Object needs to be 
    */

    private void readObject (java.io.ObjectInputStream obj) throws InvalidObjectException, ClassNotFoundException, IOException{
        obj.defaultReadObject();

        if(numerator == 0) { //either zero or NaN is acceptible
            if(denominator == 1 || denominator == 0)
                return;
            throw new InvalidObjectException("Improper Deserialisation for zero value ");
         }
        else if(denominator == 0) { //either infinte or NaN is acceptible
            if(numerator == 1 || numerator == -1 || numerator == 0)
                return;
            throw new InvalidObjectException ("Improper Deserialisation for infinite value ");
        }
        else {
            if(gcd(numerator, denominator) > 1)
                throw new InvalidObjectException("Improper Deserialisation for finite values ");
        }
    }

    /*
        public method to parse the string to get relevant rational input
        :, / is only allowed for the purpose of separation
    */
    public static Rational parseString (String st) throws NumberFormatException, NullPointerException{
        if(st == null ){
            throw new NullPointerException("Null String passed as argument");   
        }
        if(st.equals ("NaN"))
            return NaN;
        else if(st.equals ("Infinity"))
            return POSITIVE_INFINITY;
        else if(st.equals("-Infinity"))
            return NEGATIVE_INFINITY;
        else {
            int separator_idx = st.indexOf("/");
            if(separator_idx < 0) {
                separator_idx = st.indexOf(":");
            }
            if(separator_idx < 0) {
                throw new NumberFormatException("Number Format Error");
            }
            try {
                return new Rational (Integer.parseInt(st.substring(0 ,separator_idx)), Integer.parseInt(st.substring(separator_idx + 1, st.length())));
            }catch (NumberFormatException ne) {
                throw new NumberFormatException ("Number Format Error");
            }
        }
    }
}

class Test {
    public static void main (String args []) {
        String s = "3/6";
        Rational r = Rational.parseString(s);
        Rational r1 = new Rational (7, 5);
    }
}