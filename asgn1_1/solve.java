package java_assignment.asgn1_1;

//importing the necessary packages to provide the necssary support 
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Designing the class Student 
class Student {
    //attributes of the class Student 
    protected String roll;
    protected String name;
    protected String course;
    protected String admDate;
    protected int marks[] = new int[5];
    

    //method to get the basic details
    public boolean getData() {
        //will be overidden in the StudentDepartment class
        return true;
    }

    //public interfaces designed to get the Student Data
    public String retName() { return name; }
    public String retCourse() { return course; }
    public String retAdmDate() { return admDate;}
    public int [] retMarks() { return marks;}
    public String retRoll () {return roll;}
    public int retTotalMarks() { //method to return the total marks obtained 
        int sum = 0;
        for(int i = 0; i < 5; i++)
            sum += marks[i];
        return sum;
    }
}

//deisgning the class StudentDepartment
class StudentDepartment extends Student {
    protected String depName;
    private static HashMap<String, Integer> hm = new HashMap<>();
   

    //interface to get the department name
    public String retDepartment()   {return depName; }

    //method to generate the Roll Number 
    private void getRoll() {
        //generating roll number
        roll = depName + admDate.substring(admDate.length() - 2, admDate.length());
        String id = Integer.toString(hm.get(depName));
        if(id.length() == 1)    { //add two zeroes at the front
            id = "00" + id;
        }
        else if(id.length() == 2){
            id = "0" + id;
        }
        roll += id;
        if(depName.length() == 3)
            roll = "B" + roll;
    }
    private boolean checkDep(String s ) {
        String deps[] = {"CSE", "ETCE", "PRD", "MME"};
        for(int i = 0; i < 4; i++) {
            if(s.equals(deps[i]))
                return true;
        }
        return false;
    }
    //method to get the data from the user
    public boolean getData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name :- ");
        name = sc.nextLine();
        System.out.print("Enter Course :- ");
        course = sc.nextLine();
        System.out.print("Enter Admission date (dd/mm/yy format):- ");
        admDate = sc.nextLine();
        System.out.print("Enter Department Code :- ");
        depName = sc.nextLine();
        if(!checkDep(depName))
            return false;
        System.out.print("Enter Marks in five subjects :- ");
        for(int i = 0; i < 5; i++) {
            System.out.printf("Enter Marks in sub %d :- ", i + 1);
            marks[i] = sc.nextInt();
        }
        if(!hm.containsKey(depName))
            hm.put(depName, 1);
        else
            hm.put(depName, hm.get(depName) + 1); //chaning the count of number of students in each department 
        getRoll(); //calling the getRoll function to get the Roll Number / id
        showRoll();
        return true;
    }

    //method to display the roll number 
    public void showRoll() {
        System.out.println("Roll number  :- " + roll);
    }
    //mehtod to display marksheet
    void displayMarks() {
        System.out.println("<-------Grade Card --------->");
        int total = 0;
        System.out.println("Name of Candidate :- " + name);
        System.out.println("Department Of the Candidate :- " + depName);
        System.out.println("Course Enrolled :- " + course);
        System.out.println("Admission Date :- " + admDate);
        System.out.println("---------------------------------");
        for(int i = 0; i < 5; i++) {
            System.out.printf("Subject %d :- %d  \n", i + 1, marks[i]);
            total += marks[i];
        }
        System.out.printf("Total Marks Obtained :- %d\n", total);
        if((float)total / (float) 500 < 0.35 )
            System.out.println("Result : Fail :'(");
        else
            System.out.println("Result : Pass :)");
        System.out.println("<------------(ABC University)----------->");
    }
    /*comparator for sorting the list on the basis of marjks */
    public static Comparator<StudentDepartment> ByMarks = new Comparator<StudentDepartment>(){
        public int compare (StudentDepartment s1, StudentDepartment s2){
            int m1 = 0, m2 = 0;
            for(int i = 0; i < 5; i++) {
                m1 += s1.marks[i];
                m2 += s2.marks[i];
            }
            //for sorting in descending order of marks  
            return m2 - m1;
        }
    };
}

//deisgning the class University to keep a track of all the students in the university
class University {
    //creating a arraylist of students
    private ArrayList<StudentDepartment> arr = new ArrayList<StudentDepartment> ();
    protected static int totalAdm = 0;

    //method to get the details of a student
    public void getStudent () {
        StudentDepartment st = new StudentDepartment() ;
        if(st.getData()){
            arr.add(st); //new student added
            totalAdm++; //increment the total students present in the university
        }
        else {
            System.out.println("Invalid Department Code ! Try Again ... ");
        }
    }
    //method to check whether a student is present or not
    public int checkPresence(String roll) {
        int k = 0;
        for(StudentDepartment st : arr) {
            if(st.retRoll().equals(roll))
                return k;
            k++;
        }
        return -1;
    }
    //method to remove a student on the basis of roll
    public void removeStudent(String roll) {
        int idx = checkPresence(roll); //check whether the student is present or not
        if(idx == -1)
            System.out.println("Student Not Found !!! Please try again with a valid Roll Number :)");
        else {
            StudentDepartment st = arr.get(idx); //remove the student from the array List
            arr.remove(idx);
            st = null;
            finalize();
            System.gc();
            System.out.println("Student found and removed !, Memory Freed ...");
        }
    }
    //method to print marksheet by roll number 
    public void printMarksByRoll(String Roll) {
        int idx = checkPresence(Roll);
        if(idx == -1)
            System.out.println("Student Not Found !");
        else {
            arr.get(idx).displayMarks();
        }
    }
    //method to sort all the stduents on the basis of marks 
    public void sortByMarksAll () {
        ArrayList<StudentDepartment> arr1 = new ArrayList<StudentDepartment>(arr);
        Collections.sort(arr1, StudentDepartment.ByMarks);

        if(arr1.size() == 0)
            System.out.println("Sorry No Students to Display !");
        else {
            System.out.println("-----------ABC University (LeaderBoard)-------------- ");
            for(StudentDepartment st : arr1) {
                System.out.print(" Name Of the student :- " + st.retName() + " Marks Obtained :- " + st.retTotalMarks() + " ");
                st.showRoll(); //display the details of sorted students <---- to be modified------>
            }
            System.out.println("------------------------------");
        }
    }

    //method to sort the students of one department on the basis of marks
    public void sortByMarksDep(String Dep) {
        ArrayList<StudentDepartment> arr1 = new ArrayList<StudentDepartment>();
        //add students belonging to a particular department
        for(StudentDepartment st : arr) {
            if(st.retDepartment().equals(Dep))
                arr1.add(st);
        }
        Collections.sort(arr1, StudentDepartment.ByMarks); //sorting the students on the basis of marks
        if(arr1.size() == 0)
            System.out.println("Sorry No Students to Display !");
        else{
            System.out.printf("--------Department Of %s (LeaderBoard)---------\n", Dep);
            for(StudentDepartment st : arr1) {
                System.out.print(" Name Of the student :- " + st.retName() + " Marks Obtained :- " + st.retTotalMarks() + " ");
                st.showRoll(); //display the student roll <---- to be modified------>
            }
            System.out.println("------------------------------");
        }
    }
    //static method to implement the total number of students in the university
    public static void showCount() {System.out.println("Total Students in the university :- " + totalAdm);}

    //overriding the finalize method to check the Garbage Collector getting called
    protected void finalize () {totalAdm--;}
}
class Test{
    public static void main(String args []) {
        //menu driven program to test the implemented features
        University un = new University();
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        while(true) {
            int ch;
            System.out.print("1. Add Student\n2. Find Student\n3. Generate Grade Card By Roll\n4. Sort all Students By Marks\n5. Sort Department wise By Marks\n6. Remove Student\n7. View Total Students\n8. exit\nEnter Choice :- ");
            ch = sc.nextInt();
            switch(ch) {
                case 1: un.getStudent();
                        break;
                case 2: System.out.print("Enter the Roll Number of the student :- ");
                        String s = sc1.nextLine();
                        int idx = un.checkPresence(s);
                        if(idx != -1)
                            System.out.println("Student Found Hurray !");
                        else
                            System.out.println("Student Not Found !");
                        break;
                case 3: System.out.print("Enter the Roll Number of the student :- ");
                        String s1 = sc1.nextLine();
                        un.printMarksByRoll(s1);
                        break;
                case 4: un.sortByMarksAll();
                        break;
                
                case 5: System.out.print("Enter the Department Name :- ");
                        String dep = sc1.nextLine();
                        un.sortByMarksDep(dep);
                        break;
                case 6: System.out.print("Enter the Roll Number of the student :- ");
                        String roll = sc1.nextLine();
                        un.removeStudent(roll);
                        break;
                case 7: University.showCount();
                        break;
                default: System.exit(0);
            }
        }
    }
}