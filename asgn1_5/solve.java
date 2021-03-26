//package java_assignment.asgn1_5;

import java.io.*;
import java.util.*;


class Pair {
    protected int first; 
    protected int second;

    Pair (int a, int b) {first = a; second = b;}
    public int retFirst () {return first;}
    public int retSecond () {return second;}
}

class Solve {
    public static void main (String args []) throws IOException{
        try {
            //opening the file in java using file class
            Scanner sc = new Scanner (System.in);
            System.out.print ("Enter the name of the text file :- ");
            String fileName = sc.nextLine();

            File file = new File (fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            TreeMap<String, ArrayList<Pair>> tm = new TreeMap<String, ArrayList<Pair>> ();

            String line;
            int lineCount = 1;
            while((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                int wordCount = 1;
                while(st.hasMoreTokens()) {
                    String s = st.nextToken();
                    //update the words into the hashmap
                    if(tm.containsKey(s)){  //hashmap already conatins the word
                        ArrayList<Pair> arr = tm.get(s);
                        arr.add(new Pair(lineCount, wordCount));
                        tm.put(s, arr);
                    }
                    else{ //hashmap doesn't contain the word
                        ArrayList<Pair> arr = new ArrayList<Pair>();
                        arr.add(new Pair (lineCount, wordCount));
                        tm.put(s, arr);
                    }
                    wordCount++; //increment the word count by one 
                }
                lineCount++; //increament the line count by one 
            }
            System.out.println ("<----------------Index Page -------------->");
            for(Map.Entry <String, ArrayList<Pair>> entry : tm.entrySet()) {
                System.out.println ("Word :- " + entry.getKey());
                System.out.println ("Locations Found :- ");
                for(Pair p : entry.getValue()) {
                    System.out.printf ("{Line Number :- %d, Word Number :- %d}\n", p.retFirst(), p.retSecond());
                }
                System.out.println ("-----------------------------------------");

            }
        }catch (IOException ie) { //File not found exception
            System.out.println ("File to be indexed not found !");
        }
    }
}