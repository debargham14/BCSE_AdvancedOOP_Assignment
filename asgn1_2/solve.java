package java_assignment.asgn1_2;

import java.util.*;
//designing class Item 
class Item {
    //attributes essential to describe the object
    protected String icode;
    protected String name;
    protected int rate;
    protected int qty;

    //public methods defined to give a public interface to the protected variables 
    //method to get the relevant item details
    public void getData (String pdtName, String code) {
        Scanner sc = new Scanner(System.in);
        icode = new String (code);
        name = new String (pdtName);
        System.out.print ("Enter the Item Rate :- "); 
        rate = sc.nextInt();
        System.out.print ("Enter Quantity :- ");
        qty = sc.nextInt();
    }
    //method to update the item rate
    public void updateRate(int newRate) {
        rate = newRate;
    }
    //method to return item code
    public String retIcode () {
        String s = new String (icode); //so that it returns a deep copy
        return s;
    }
    public String retName () { //method to return name
        String s = new String (name);
        return  s;
    }
    public int retRate () {return rate;} //method to return the rate
    public int retQty () {return qty;} //method to return the quantity
    //method to display all the relevant item data
    public void showData () {
        System.out.println ("---------------Item Details---------------");
        System.out.println ("Item Code :- " + icode);
        System.out.println ("Item Name :- " + name);
        System.out.println ("Current Item Rate :- " + rate);
        System.out.println ("Quantity Available :- " + qty);
        System.out.println("-------------------------------");
    }
}

//user defined exception class to throw an error when an item doesn't exist
class ItemNotFoundException extends Exception {
    private String str;
    public ItemNotFoundException (String s) {
        str = new String (s);
    }
    public String toString () {return str;}
}

class SYSTEM extends Item {
    protected ArrayList<Item> list = new ArrayList<Item> ();
    protected String SysName;

    //method to get a system name
    public void getSysName () {
        Scanner sc = new Scanner (System.in);
        System.out.print ("Enter the System Name :- ");
        SysName = sc.nextLine();
    } 
}

//designing the class ShopKeeper which inherits from item class
class ShopKeeper extends SYSTEM{
    protected static SYSTEM sys = new SYSTEM();
    // to get the system name
    
    public int checkPresence (String code) { // to check the presence of a particular item
        for(int i = 0; i < sys.list.size(); i++) {
            Item it = sys.list.get(i);
            if(code.equals(it.retIcode())) //item present
                return i; //return idx
        }
        return -1; //item not present 
    }
    //method to update stock
    public void updateStock (String code, char c) throws ItemNotFoundException{
        int idx = checkPresence(code);
        String s1 = (c == 'd' ? "deducted" : "added");

        if(idx != -1) { //update the stock entered 
            Scanner sc = new Scanner (System.in);
            int iqty = sys.list.get(idx).qty;
            int val;
            System.out.printf("Enter the quantity to be %s:- ", s1);
            val = sc.nextInt();
            if(c == 'd'){ //to be deducted 
                //check whether that amount is available for dedcution not
                if(iqty >= val){
                    iqty -= val;
                    System.out.println ("Deduction successfull !");
                }
                else
                    System.out.println("Sorry that much quantity is not available !");
            }
            else{
                iqty += val;
                System.out.println ("Restocking Successfull!");
            }
            sys.list.get(idx).qty = iqty; //final updation done
        }
        else {
            throw new ItemNotFoundException("Item with the entered code doesn't exist !");
        }
    }

    //method to check the availabilty of a particular stock
    public void checkAvail (String code) throws ItemNotFoundException{
        int idx = checkPresence(code);
        //show the avaialable quantity
        if(idx != -1)
            System.out.printf("Available Qty for product code {%s} :- %d\n", code, sys.list.get(idx).qty);
        else
            throw new ItemNotFoundException("Item with the Entered Code doesn't exist !");
    }
    //method to display item on the basis of item code
    public void displayItem (String code) throws ItemNotFoundException{
        int idx = checkPresence(code);
        if(idx != -1)
            sys.list.get(idx).showData();
        else{
            throw new ItemNotFoundException("Item with the Entered Code doesn't exist !");
        }
            
    }

    //method to show an item above a particular cost
    public void showItemAboveCost (int cost) {
        int k = 0;
        for(int i = 0; i < sys.list.size(); i++) {
            if(sys.list.get(i).rate > cost){
                sys.list.get(i).showData();
                k++;
            }
        }
        if(k == 0)  //no items present 
            System.out.println("No Such Item Present !");
    }
}

//designing the class Stock Entry Operator
class SEO extends ShopKeeper{
    protected static int entryOrder = 0;

    public SEO () {sys.getSysName();}
    //method to check the uniqueness of the item
    public int checkUnique(String code) {
        for(int i = 0; i < sys.list.size(); i++) {
            Item it = sys.list.get(i);
            if(code.equals(it.retIcode())) //if it matches with previous item code it's not unique
                return i;
        }
        return -1; //it's a unique item code
    }
    //method to register a new Product
    public void registerPdt () {
        Item it = new Item();
        String code = new String ();
        String pdtName = new String ();

        Scanner sc = new Scanner (System.in);
        
        System.out.print ("Enter Item Name :- ");
        pdtName = sc.nextLine ();
        
        //code to generate roll number 
        code = pdtName.substring (0, 3);
        code = code.toUpperCase();
        entryOrder = entryOrder + 1;
        String lastNumber = Integer.toString(entryOrder);

        if(lastNumber.length() == 1)
            code += "00" + lastNumber;
        else if(lastNumber.length() == 2)
            code += "0" + lastNumber;
        else
            code += lastNumber;
        //code to generate the unique id ends here

        it.getData(pdtName, code); //get the other details.
        sys.list.add(it); //add it to the list
        System.out.println("Product Registered Successfully ! Item Code generated :- " + code); //display a acknowledgement message 
    }

    //method to Update the Rate of a particular product
    public void updateRateOfItem () throws ItemNotFoundException{
        //first check whether the item exists or not
        String s = new String ();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Item Code :- "); 
        s = sc.nextLine();
        int idx = checkUnique(s);

        if(idx != -1){ //already present in the list
            int newRate;
            sys.list.get(idx).showData();

            System.out.print("Enter the new Rate :- ");
            newRate = sc.nextInt();
            sys.list.get(idx).updateRate(newRate);
            System.out.println("Rate Changed Successfully !"); // acknowledgement message 
        }
        else { //to be modified with throw statement 
            throw new ItemNotFoundException("Item with the Entered Code doesn't exist !");
        }
    }
}
//Driver class to test the functionality
class Test {

    public static void main (String args []) {
        ShopKeeper sk = new ShopKeeper();
        SEO seo = new SEO();
        
        while (true) {
            int ch;
            System.out.print("1. Add Item\n2. Show Item\n3. Update Rate Of Item\n4. Buy Item\n5. Add Item\n6. Check Availabilty\n7. Show Item Above a Cost\n8. exit\nEnter Choice :- ");
            Scanner sc1 = new Scanner(System.in);
            ch = sc1.nextInt(); //enter choice 
            Scanner sc2 = new Scanner (System.in);
            switch (ch) {
                case 1: seo.registerPdt();
                        break;
                case 2: String code = new String (); 
                        System.out.print ("Enter the Item Code :- ");
                        code = sc2.nextLine();
                        try {
                            sk.displayItem(code);
                        }catch (ItemNotFoundException infe) {
                            System.out.println(infe);
                        }
                        break;
                case 3: try {
                            seo.updateRateOfItem();
                        }catch (ItemNotFoundException infe) {
                            System.out.println(infe);
                        }
                        break;
                case 4: 
                        System.out.print ("Enter the Item Code :- ");
                        String code1 = sc2.nextLine();
                        try {
                            sk.updateStock(code1, 'd');
                        }catch  (ItemNotFoundException infe) {
                            System.out.println (infe);
                        }
                        break;
                case 5: String code2 = new String (); 
                        System.out.print ("Enter the Item Code :- ");
                        code2 = sc2.nextLine();
                        try {
                            sk.updateStock(code2, 'a');
                        }catch  (ItemNotFoundException infe) {
                            System.out.println (infe);
                        }
                        break;
                case 6: String code3 = new String (); 
                        System.out.print ("Enter the Item Code :- ");
                        code3 = sc2.nextLine();
                        try {
                            sk.checkAvail(code3);
                        }catch (ItemNotFoundException infe) {
                            System.out.println (infe);
                        } 
                        break;
                
                case 7: int cost;
                        System.out.print ("Enter the Cost :- ");
                        cost = sc2.nextInt();
                        sk.showItemAboveCost(cost);
                        break;
                default: System.exit(0);
                        break;
            }
        }
    }
}



