package com.Main;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.database.CrimeDAO;
import com.database.PoliceDAO;
import com.database.UserDAO;
import com.loading.Loading;

public class CrimeServices
{

    Scanner sc = new Scanner(System.in);
    HashMap<Integer, Crime> hash;
    CrimeDAO dao = new CrimeDAO();
    PoliceDAO policeDAO = new PoliceDAO();

    // ===== ANSI COLORS =====
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public CrimeServices() {
        hash = dao.loadAllCrimes();
    }

    // ================= ADD CRIME =================
    public void addCrime() {

        try 
        {
        
        	System.out.println(CYAN + "\n════════ ADD NEW CRIME ════════" + RESET);

            System.out.print("Criminal Name : ");
            String name = sc.nextLine();

            System.out.print("Crime Type : ");
            String type = sc.nextLine();

            System.out.print("Location : ");
            String location = sc.nextLine();

            Crime crime = new Crime(name, 0, type, location, "Pending");

            int generatedId = dao.addCrime(crime);

            if (generatedId != -1) {

                crime.setCrimeId(generatedId);
            
                hash.put(generatedId, crime);

                System.out.println(GREEN + "\n* Crime Added Successfully" + RESET);
                System.out.println(BLUE + "Generated Crime ID : " + generatedId + RESET);
            }

        } catch (Exception e) {
            System.out.println(RED + "Error Adding Crime!" + RESET);
        }
    }

    // ================= VIEW ALL CRIMES =================
    public void viewAllCrimes() {

        System.out.println(CYAN + "\n════════ ALL CRIME RECORDS ════════\n" + RESET);

        if (hash.isEmpty()) {
            System.out.println(YELLOW + "* No Crime Records Found\n" + RESET);
            return;
        }

        for (Crime c : hash.values()) {
            System.out.println(c.displayCrime());
        }
    }

    // ================= ASSIGN CASE =================
    public void assignCrimeToPolice() {

        try {
            System.out.println(CYAN + "\n════════ ASSIGN CASE ════════" + RESET);

            System.out.print("Enter Crime ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null) {

                System.out.print("Enter Police Username : ");
                String policeName = sc.nextLine();

                crime.setAssignedPolice(policeName);
                crime.setStatus("Investigating");
                dao.updateCrime(crime);

                System.out.println(GREEN + "\n* Case Assigned Successfully\n" + RESET);

            } else {
                System.out.println(RED + "\n* Crime Not Found\n" + RESET);
            }

        } catch (InputMismatchException e) {
            System.out.println(RED + "Invalid Input! Enter numeric ID.\n" + RESET);
            sc.nextLine();
        }
    }

    // ================= CLOSE CASE =================
    public void closeCase() {

        try {
            System.out.println(CYAN + "\n════════ CLOSE CASE ════════" + RESET);

            System.out.print("Enter Crime ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null && "Solved".equalsIgnoreCase(crime.getStatus())) {

                runLoading();
                crime.setStatus("Closed");
                dao.updateCrime(crime);

                System.out.println(GREEN + "\n* Case Closed Successfully\n" + RESET);

            } else {
                System.out.println(YELLOW + "\n* Only Solved Cases Can Be Closed\n" + RESET);
            }

        } catch (InputMismatchException e) {
            System.out.println(RED + "Invalid Input! Enter numeric ID.\n" + RESET);
            sc.nextLine();
        }
    }

 // ================= LOGIN =================
    public String login() {

        try {

            System.out.println(CYAN + "\n═══════════════════════════════════" + RESET);
            System.out.println(YELLOW + "               🔐 LOGIN PANEL" + RESET);
            System.out.println(CYAN + "═══════════════════════════════════\n" + RESET);

            System.out.print(GREEN + "   Enter Username : " + RESET);
            String username = sc.nextLine();

            System.out.print(GREEN + "   Enter Password : " + RESET);
            String password = sc.nextLine();

            runLoading();

            User user = UserDAO.login(username, password);

            if (user != null) {

                System.out.println(GREEN + "\n* Login Successful!" + RESET);
                System.out.println(BLUE + "   Welcome, " + username + RESET);
                System.out.println(PURPLE + "   Role : " + user.getRole() + "\n" + RESET);

                return user.getRole();   
            } else {

                System.out.println(RED + "\n* Invalid Username or Password!\n" + RESET);
                return "INVALID";
            }

        } catch (Exception e) {

            System.out.println(RED + "\n* Login Error: " + e.getMessage() + "\n" + RESET);
            return "INVALID";
        }
    }

    // ================= POLICE MENU =================
    public void policeMenu(String policeName) {

        int choice = 0;

        do {
            try {
                System.out.println(PURPLE + "\n════════ POLICE MENU ════════" + RESET);
                System.out.println(YELLOW + "1. View Assigned Cases");
                System.out.println(YELLOW + "2. Add Investigation Notes");
                System.out.println(YELLOW + "3. Mark Case as Solved");
                System.out.println(YELLOW + "4. Logout\n" + RESET);

                System.out.print("Enter Choice : ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        viewAssignedCrimes(policeName);
                        break;

                    case 2:
                        addInvestigationNotes(policeName);
                        break;

                    case 3:
                        markCaseSolved(policeName);
                        break;

                    case 4:

                    	runLoading();
                    	System.out.println(YELLOW + "\nLogging Out...\n" + RESET);
                        break;

                    default:
                        System.out.println(RED + "Invalid Choice!\n" + RESET);
                }

            }
            catch (InputMismatchException e)
            {
            
            	System.out.println(RED + "Invalid Input!\n" + RESET);
                sc.nextLine();
            
            }

        } 
        while (choice != 4);
    }

    private void viewAssignedCrimes(String policeName) {

        System.out.println(CYAN + "\n════════ ASSIGNED CASES ════════\n" + RESET);

        boolean found = false;

        for (Crime c : hash.values()) {

            if (policeName.equalsIgnoreCase(c.getAssignedPolice()) &&
                    "Investigating".equalsIgnoreCase(c.getStatus())) {

                System.out.println(c.displayCrime());
                found = true;
            }
        }

        if (!found)
            System.out.println(YELLOW + "No Active Cases Assigned\n" + RESET);
    }

    private void addInvestigationNotes(String policeName) {

        try
        {
          
        	System.out.print("Enter Crime ID : ");
            
        	int id = sc.nextInt();
            
        	sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null && policeName.equalsIgnoreCase(crime.getAssignedPolice()))
            {

                System.out.print("Enter Notes : ");
                String notes = sc.nextLine();

                crime.setInvestigationNotes(notes);
                dao.updateCrime(crime);

                System.out.println(GREEN + "Notes Added Successfully\n" + RESET);

            }
            else
            {
                System.out.println(RED + "Access Denied\n" + RESET);
            }

        } catch (InputMismatchException e) {
            
        	System.out.println(RED + "Invalid Input!\n" + RESET);
            sc.nextLine();
        
        }
    }

    private void markCaseSolved(String policeName) {

        try {
            
        	System.out.print("Enter Crime ID : ");
            int id = sc.nextInt();
           
            sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null &&
            
            		policeName.equalsIgnoreCase(crime.getAssignedPolice()) &&
                    
            		"Investigating".equalsIgnoreCase(crime.getStatus())) {

                
            	crime.setStatus("Solved");
             
                dao.updateCrime(crime);

                System.out.println(GREEN + "Case Marked as Solved\n" + RESET);

            } 
            else
            {
               
            	System.out.println(RED + "Access Denied or Already Solved\n" + RESET);
            
            }

        } catch (InputMismatchException e) {
            System.out.println(RED + "Invalid Input!\n" + RESET);
            sc.nextLine();
        }
    }

   // ================= VIEW POLICE =================
    
    public void viewAllPolice()
    {
    	
    	System.out.println(CYAN + "\n════════ POLICE DETAILS ════════\n" + RESET);

        var policeList = policeDAO.getAllPolice();

        if (policeList.isEmpty()) {
            System.out.println(YELLOW + "No Police Records Found\n" + RESET);
            return;
        }

        for (var p : policeList) {
            System.out.println(BLUE + "Username : " + RESET + p.getUsername());
            System.out.println(GREEN + "Rank     : " + RESET + p.getRank());
            System.out.println(YELLOW + "Station  : " + RESET + p.getStation());
            System.out.println(PURPLE + "Contact  : " + RESET + p.getContact());
            System.out.println(CYAN + "---------------------------------\n" + RESET);
        }
    }
    public void viewParticularCrime(Scanner sc) {

        try {
            System.out.println(CYAN + "\n════════ VIEW PARTICULAR CRIME ════════" + RESET);

            System.out.print(GREEN + "Enter Crime ID : " + RESET);
            int id = sc.nextInt();
            sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null) {

                System.out.println(GREEN + "\n* Crime Found\n" + RESET);
                System.out.println(crime.displayCrime());

            } else {
                System.out.println(RED + "\n* Crime Not Found\n" + RESET);
            }

        } catch (InputMismatchException e) {

            System.out.println(RED + "\n* Invalid Input! Enter numeric ID.\n" + RESET);
            sc.nextLine();
        }
    }

    public void viewSolvedCrimes() {

        System.out.println(CYAN + "\n════════ SOLVED CASES ════════\n" + RESET);

        boolean found = false;

        for (Crime c : hash.values()) {

            if ("Solved".equalsIgnoreCase(c.getStatus())) {

                System.out.println(c.displayCrime());
                found = true;
            }
        }

        if (!found) {
            System.out.println(YELLOW + "* No Solved Cases Found\n" + RESET);
        }
    }

    public void updateCrime(Scanner sc) {

        try {

            System.out.println(CYAN + "\n════════ UPDATE CRIME ════════" + RESET);

            System.out.print(GREEN + "Enter Crime ID : " + RESET);
            int id = sc.nextInt();
            sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null) {

                System.out.println(YELLOW + "\nCurrent Details:\n" + RESET);
                System.out.println(crime.displayCrime());

                System.out.print(BLUE + "New Crime Name : " + RESET);
                String name = sc.nextLine();
                
                System.out.print(BLUE + "New Crime Type : " + RESET);
                String type = sc.nextLine();


                System.out.print(BLUE + "New Notes : " + RESET);
                String notes = sc.nextLine();

                crime.setCrimeType(type);
                crime.setName(name);
                crime.setInvestigationNotes(notes);

                dao.updateCrime(crime);

                System.out.println(GREEN + "\n* Crime Updated Successfully\n" + RESET);

            } else {
                System.out.println(RED + "\n* Crime Not Found\n" + RESET);
            }

        } catch (InputMismatchException e) {

            System.out.println(RED + "\n⚠ Invalid Input! Enter numeric ID.\n" + RESET);
            sc.nextLine();
        }
    }

    public void addPunishment(Scanner sc) {

        try {
            System.out.println(CYAN + "\n════════ ADD PUNISHMENT ════════" + RESET);

            System.out.print(GREEN + "Enter Crime ID : " + RESET);
            int id = sc.nextInt();
            sc.nextLine();

            Crime crime = hash.get(id);

            if (crime != null && "Solved".equalsIgnoreCase(crime.getStatus())) {

                System.out.print(BLUE + "Enter Punishment : " + RESET);
                String punishment = sc.nextLine();

                crime.setPunishment(punishment);
                crime.setStatus("Closed");

                dao.updateCrime(crime);

                System.out.println(GREEN + "\n* Punishment Added & Case Closed\n" + RESET);

            } else {
                System.out.println(RED + "\n* Crime Not Found or Not Solved\n" + RESET);
            }

        } catch (InputMismatchException e) {
            System.out.println(RED + "\n* Invalid Input!\n" + RESET);
            sc.nextLine();
        }
    }

    
    private void runLoading() 
    {
        try 
        {
            Loading load = new Loading();
        
            load.start();
            
            load.join();
        } 
        catch (InterruptedException e) {
        
        	System.out.println(RED + "Loading Interrupted!" + RESET);
        
        }
    }
}
