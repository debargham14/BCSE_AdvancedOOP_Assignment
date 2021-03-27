package java_assignment.asgn1_6;

import java.io.*;
import java.util.*;

//designing the class patient
class Patient {
    //attributes involve recording the patients medical reports and basic information
    protected String pid;
    protected String name;
    protected int age;
    protected int weight;
    protected int bloodPressure;
    protected int temperature;
    
    //basic methods
    //method to get the patients details
    public void getData (String s1) {
        Scanner sc = new Scanner(System.in);
        System.out.print ("Enter Name of the patient :- ");
        name = sc.nextLine ();
        System.out.print ("Enter Age Of the patient :- ");
        age = sc.nextInt ();
        System.out.print ("Enter the weight of the patient (in kgs):- ");
        weight = sc.nextInt ();
        System.out.print ("Enter the measured Blood Pressure :- ");
        bloodPressure = sc.nextInt();
        System.out.print ("Enter the patient's body temperature :- ");
        temperature = sc.nextInt();

        pid = new String (s1);
        System.out.println ("Patient Id :- " + s1);
        System.out.println("--------------------------------");
    }

    //method to display the basic details about the patient
    public void showData () {
        System.out.println ("Patient Id :- " + pid);
        System.out.println ("Patient Name :- " + name);
        System.out.println ("Patient Weight :- " + weight);
        System.out.println ("Patient blood pressure :- " + bloodPressure);
        System.out.println ("Patient body temperature :- " + temperature);
    }
    //public method to return the patient id
    public String returnId () {return pid;}

    //public method to update the patient's record
    public void updatePatient () {
        Scanner sc = new Scanner(System.in);
        System.out.print ("Enter the new weight of the patient (in kgs):- ");
        weight = sc.nextInt ();
        System.out.print ("Enter the newly measured Blood Pressure :- ");
        bloodPressure = sc.nextInt();
        System.out.print ("Enter the updated patient's body temperature :- ");
        temperature = sc.nextInt();
    }
}

//deisgning the class doctor in a way so that it can have multiple patients
class Doctor implements Comparable <Doctor>{
    protected String doctorId;
    protected String doctorName;
    protected ArrayList<Patient> plist = new ArrayList<Patient> ();
    
    //basic methods 
    public void getData (String s) { //method to get the doctors details
        Scanner sc = new Scanner (System.in);
        System.out.print ("Enter the doctor's Name :- ");
        doctorName = sc.nextLine ();
        doctorId = new String (s);
    }
    //method to display the doctors detail
    public void showData () {
        System.out.println ("Doctor Name :- " + doctorName);
        System.out.println ("Doctor Id :- " + doctorId);
    }
    //overriding the method compareTo
    public int compareTo (Doctor d) { //to keep the doctors with lesser patients at the top of the queue so that they can be assigned with more patients
        return plist.size() - d.plist.size();
    } 
    //public method to add a patient under a doctor
    public void addPatient(Patient p) {
        plist.add(p);
    }
    //method to find a patient in the doctor's patient list
    public int findPatientById (String pid) {
        int idx = -1;
        for (int i = 0; i < plist.size(); i++) {
            if(plist.get(i).returnId().equals(pid))
            {
                idx = i;
                break;
           }
        }
        return idx;
    }
    //public method to remove a patient from a doctor's patient list
    public void removePatient (String pid) {
        int idx = findPatientById(pid);
        if(idx != -1)
            plist.remove(idx);
    }
    //public method to update a patient in the doctor's list
    public void updatePatientRecord (String pid) {
        int idx = findPatientById(pid);
        if(idx != -1) {
            Patient p = plist.get(idx);
            p.updatePatient();
        }
    } 
    //public method to display a doctor's patient list
    public void showPatient () {
        int k = 0;
        for(int i = 0; i < plist.size(); i++) {
            plist.get(i).showData();
            System.out.println ("------------------------------"); 
            k++;
        }
        if(k == 0) {
            System.out.println ("No Patients to be displyed !");
        }
    }
    //public method to return the doctor's id 
    public  String returnId () {return doctorId;}
    //public method to add object by reference 
    public void addPatientByRef (Patient p) {plist.add(p);}
}

class Hospital {
    PriorityQueue <Doctor> dlist = new PriorityQueue<Doctor> ();
    static protected int pcount = 0;
    static protected int pcount1 = 0;
    static protected int dcount = 0;
    ArrayList<Patient> patientList = new ArrayList<Patient> (); //to keep all the patients together 

    void addPatient () { // does both adding patient details and assigning a doctor
        Patient p = new Patient ();
        String pid = new String ("PAT21");
        String lastNumber = Integer.toString(pcount);
        if(lastNumber.length() == 1)
            pid += "00" + lastNumber;
        else if(lastNumber.length() == 2)
            pid += "0" + lastNumber;
        else
            pid += lastNumber;
        
        p.getData(pid);
        patientList.add(p);
        System.out.println ("Patient Registration Succesfull !");
        //code to register a patient ends

        //code to assign a doctor
        if(dlist.isEmpty ()){
            System.out.println ("No Doctors Found !... Please Try Again");
        }
        else {
            Doctor d = dlist.peek();
            System.out.println ("Doctor Assigned --------------------------- ");
            d.showData();
            dlist.remove();
            d.addPatient(p); //adding the patient under the specific doctor
            dlist.add(d); //adding the doctor back in the prority queue
            System.out.println ("---------------------------------");
        }
        pcount++; //incrementing the count used to store the id of the patient 
        pcount1++; //incrementing the count of the total patients admitted in the hospital
    }
    
    //public method to add a doctor
    public void addDoctor () {
        Doctor d = new Doctor ();
        String docId = new String ("DOC21");
        String lastNumber = Integer.toString(dcount);
        if(lastNumber.length() == 1)
            docId += "00" + lastNumber;
        else if(lastNumber.length() == 2)
            docId += "0" + lastNumber;
        else
            docId += lastNumber;
        
        d.getData(docId);
        dlist.add(d);
        System.out.println ("Doctor Registered Successfully ! Doctor's Id :- " + docId);
        dcount++;
    }
    public int findPatientById (String pid) {
        int idx = -1;
        for (int i = 0; i < patientList.size(); i++) {
            if(patientList.get(i).returnId().equals(pid))
            {
                idx = i;
                break;
            }
        }
        return idx;
    }
    //method to remove a patient 
    public void removePatient (String pid){
        int idx = findPatientById(pid);

        if(idx != -1){ //patient to be discharges found 
            System.out.println("Patient Discharge Successful !");
            patientList.remove(idx);
            //remove the patient from the doctor
            PriorityQueue<Doctor> dummy = new PriorityQueue<Doctor> ();
            while(!dlist.isEmpty()) {
                Doctor d = new Doctor();
                d = dlist.peek();
                dlist.remove();
                d.removePatient(pid);
                dummy.add(d);
            }
            dlist = dummy;
            pcount1--; //decrementing the total patient count in the hospital
        }
        else {
            System.out.println ("Patient to be discharged not found ! Try Again ...");
        }
    }
    //method to update the peatient's record time to time
    public void updateRecord (String pid) {
        //search whether the patient exists or not
        int idx = findPatientById(pid);
        if(idx != -1){
            Patient p = patientList.get(idx);
            p.updatePatient();

            //also we need to update the patient's record in the doctor's list
            PriorityQueue<Doctor> dummy = new PriorityQueue<Doctor> ();
            while(!dlist.isEmpty()) {
                Doctor d = new Doctor();
                d = dlist.peek();
                dlist.remove();
                int flag = d.findPatientById(pid);
                d.removePatient(pid);
                if(flag != - 1)
                    d.addPatientByRef(p);
                dummy.add(d);
            }
            dlist = dummy;
        }
        else {
            System.out.println ("Patient Record to be update not found ! Try Again...");
        }
    }

    //public method to show patient by id
    public void showPatientById (String s) {
        int idx = findPatientById(s);
        if(idx != -1)
            patientList.get(idx).showData();
        else
            System.out.println ("Patient Details to be shown Not Found ! Please enter correct Id...");
    }
    //method to display all students 
    public void showAllPatients () {
        System.out.println ("<-----------Currently registered Patients---------->");
        for(int i = 0; i < patientList.size(); i++) {
            patientList.get(i).showData();
            System.out.println ("-----------------------------");
        }
        System.out.println ("Currently Registered Patient Count :- " + pcount1);
    }
    //method to display the patient list under a doctor
    public void displayPatientsUnderDoctor (String docId) {
        int k = 0;
        PriorityQueue<Doctor> dummy = new PriorityQueue<Doctor> ();
            while(!dlist.isEmpty()) {
                Doctor d = new Doctor();
                d = dlist.peek();
                if(d.returnId().equals(docId))
                {
                    d.showData();
                    System.out.println ("<------------Patient's List-------------->");
                    d.showPatient();
                }
                dlist.remove();
                dummy.add(d);
            }
            dlist = dummy;
    }
} 

//to test the above functionalities
class Test  {
    public static void main (String args []) {
        Scanner sc = new Scanner(System.in);
        Hospital h = new Hospital ();
        System.out.println ("<-----------ABC Hospital (We Care For You)------------>");
        while(true) {
            int ch = 0;
            System.out.print ("1. Register Patient\n2. Register Doctor\n3. Remove Patient\n4. Update Patient's Record\n5. Display Patient By Id\n6. Display All Patients\n7. Display Patient's Under a Doctor\n8. exit\nEnter Choice :- ");
            ch = sc.nextInt ();
            switch (ch) {
                case 1: h.addPatient();
                        break;
                case 2: h.addDoctor();
                        break;
                case 3: String pid = new String ();
                        System.out.print ("Enter the patient's Id :- ");
                        pid = sc.next();
                        h.removePatient(pid);
                        break;
                case 4: String pid1 = new String ();
                        System.out.print ("Enter the patient's Id :- ");
                        pid1 = sc.next();
                        h.updateRecord(pid1);
                        break;
                case 5: String pid2 = new String ();
                         System.out.print("Enter the patient's Id :- ");
                        pid2 = sc.next();
                        h.showPatientById(pid2);
                        break;
                case 6: h.showAllPatients();
                        break;
                case 7: String docId = new String();
                        System.out.print ("Enter the doctor's Id :- ");
                        docId = sc.next();
                        h.displayPatientsUnderDoctor(docId);
                        break;
                default: System.exit(0);
            }
        }
    }
}

