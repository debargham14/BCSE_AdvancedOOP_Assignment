package java_assignment.asgn1_4;

import java.lang.Math;
import java.util.ArrayList;

//designing the Quote Generator class
class QuoteGenerator {
    private ArrayList <String> arr = new ArrayList<String> ();
    QuoteGenerator() {
        arr.add("God helps them that help themselves. -- Benjamin Franklin");
        arr.add("Happiness is not a reward - it is consequence. Suffering is not a punishment - it is a result. -- Robert Green Ingersoll");
        arr.add("Future. That period of time in which our affairs prosper, our friends are true and our happiness is assured. -- Ambrose Bierce");
        arr.add("Honesty is the first chapter of the book of wisdom. --Thomas Jefferson");
    }
    public String giveQuote() {
        double rand = Math.random();
        int idx = (int)(rand * 10) % arr.size(); 
        String quote = arr.get(idx);
        return quote;
    }
}

//class Test to test the above functionality
class QuoteOfTheDay {
    public static void main (String args[]) {
        QuoteGenerator qg = new QuoteGenerator();
        String s = qg.giveQuote();
        System.out.println(s);
    }
}


