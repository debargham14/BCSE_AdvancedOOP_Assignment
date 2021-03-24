package java_assignment.asgn1_3;
import java.util.Scanner;

//designing the class Stack 
class Stack {
    private char arr[] = new char [100];
    private int top = -1;
    private final int MAX_SIZE = 100;
    //method to determine whether the stack is empty or not
    public boolean isEmpty() {  return top == -1; }

    //method to insert an element at the top of the stack
    public void push (char ele) {
        if(top == MAX_SIZE - 1)
            System.out.println ("Stack Overflow !");
        else {
            arr[++top] = ele; //push it into the stack
        }
    }

    //mehtod to pop a element out of the array
    public void pop () {
        if(top == -1)
            System.out.println("Stack Underflow");
        else top--;
    }

    //method to return the top element of the stack
    public char top () {
        if(top == -1)   return 'n'; //to designate the stack is empty
        else return arr[top];
    }
}

//solver class to test the result 
class Solve {
    public static void main (String args []) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text stream :- "); //input the text stream
        String s = sc.nextLine();
        Stack st = new Stack();
        boolean flag = true;

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(' || c == '{' || c == '[')
                st.push(c); //whenever a opening brace comes we need to push it into the stack
            else {//pop the last entered character and see if there is a mismatch 
                char top_ele = st.top();
                if(top_ele == 'n'){
                    flag = false;
                    break;
                }
                else if ((c == ')' && top_ele == '(') || (c == ']' && top_ele == '[') || (c == '}' && top_ele == '{'))
                {
                    st.pop(); //when proper match is found we can go for popping the last element 
                    continue;
                }
                else{ //mismatch found 
                    flag = false;
                    break;
                }
            }
        }
        if(!st.isEmpty()) //final check 
            flag = false;
        if(flag) //result
            System.out.println("Valid Paratheses !");
        else 
            System.out.println("Invalid Parentheses !");
    }
}