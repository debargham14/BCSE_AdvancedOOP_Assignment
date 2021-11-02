//basic java code using multi threading to solve the classic producer consumer problem
package java_assignment.asgn3_1;
import java.util.*;
import java.io.*;

//data class for the consumer and the producer thread to work with the same data
class Data {
    private int data; //data to be produced and consumed
    private boolean empty;
    Data () {data = 0; empty = true;} //relevant constructor

    //synchronized method to produce the item
    public synchronized void produce () //synchronization made at the Data level 
    {
        if(!empty) {
            try {
                wait ();
            }catch (InterruptedException ie) {
                ie.printStackTrace(); //for debugging purposes
            }
        }
        data++;
        System.out.println ("Produced :- " + data);
        empty = false; //once the producer produces something the buffer is no longer empty
        notify(); // to awaken the waiting consumer thread
        try { //for better understanding 
            Thread.sleep (500);
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    //synchronized method to consume an item in a proper imposed sequence
    public synchronized void consume () {
        if(empty) {//wait till the producer produces something
            try {
                wait ();
            }catch (InterruptedException ie) {
                ie.printStackTrace(); //for debugging purposes
            } 
        }
        System.out.println ("Consumed :- " + data);
        empty = true; //once the consumer has consumed something the buffer has become empty
        notify();
        try { //for better understanding 
            Thread.sleep (500);
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

//producer class to implement the producer thread
class Producer implements Runnable {
    Data d;
    Producer (Data d1) {d = d1;} //relevant constructor get the same data to work with

    @Override
    public void run () { //implementing the run method which the thread upon formation starts executing
        int times = 100; //will execute 100 times
        while(times > 0) {
            times--;
            d.produce(); //produce the item
        }
    }
}

//consumer class to implement the consumer thread
class Consumer implements Runnable {
    Data d;
    Consumer (Data d1) {d = d1;} //relevant constructor get the same data to work with

    @Override
    public void run () { //implementing the run method which the thread upon formation starts executing
        int times = 100; //will execute 100 times
        while(times > 0) {
            times--;
            d.consume(); //consume the item
        }
    }
}

//tester class 
class Solve {
    public static void main (String args []) {
        Data d = new Data (); //creating a common data to be acessed by the producer and consumer thread
        Producer p = new Producer (d);
        Consumer c = new Consumer (d);

        Thread pth = new Thread (p); //producer thread created
        Thread cth = new Thread  (c); //consumer thread created 

        pth.start ();
        cth.start ();

        try { //so that it finishes before the main thread
            pth.join ();
            cth.join ();
        }catch (InterruptedException ie) {
            ie.printStackTrace(); //for debugging purposes
        }
    }
}

