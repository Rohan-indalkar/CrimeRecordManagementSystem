package com.Main;

import java.util.Scanner;
import java.util.InputMismatchException;
import com.loading.Loading;

public class Main {

    // ===== COLORS =====
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String ORANGE = "\u001B[38;5;208m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        CrimeServices cs = new CrimeServices();
    
        int choice = 0;

        do {
        
        	try
        	{
         System.out.println(ORANGE + "\n══════════════════════════════════════════════════════" + RESET);
                System.out.println(CYAN + "        🚨 CRIME RECORD MANAGEMENT SYSTEM 🚨" + RESET);
                System.out.println(ORANGE + "══════════════════════════════════════════════════════\n" + RESET);

                System.out.println(YELLOW + "   1. Admin Login" + RESET);
                System.out.println(YELLOW + "   2. Police Login" + RESET);
                System.out.println(YELLOW + "   3. Exit\n" + RESET);

                // === MAIN BANNER ====
       
                System.out.print(GREEN + "   Enter Choice : " + RESET);

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1 -> {
                        String role = cs.login();

                        if (role.equals("ADMIN")) {
                            System.out.println(GREEN + "\n* Admin Login Successful!\n" + RESET);
                            adminMenu(sc, cs);
                        } else {
                            System.out.println(RED + "\n* Invalid Admin Credentials!\n" + RESET);
                        }
                    }

                    case 2 -> {
                        
                    	String role = cs.login();

                        if (role.equals("POLICE")) 
                        {
                            System.out.println(GREEN + "\n* Police Login Successful!\n" + RESET);

                            System.out.print(CYAN + "Enter Police Username Again: " + RESET);
                        
                            String policeName = sc.nextLine();

                            cs.policeMenu(policeName);

                        }
                        else 
                        {
                        
                        	System.out.println(RED + "\n* Invalid Police Credentials!\n" + RESET);
                        
                        }
                    }

                    case 3 ->
                    {
                        
                    	runLoading();
                    
                    	System.out.println(GREEN + "\n* System Closed Successfully\n" + RESET);
                    
                    }

                    default -> System.out.println(RED + "\n* Invalid Option!\n" + RESET);
                }

            } catch (InputMismatchException e) {

                System.out.println(RED + "\n* Invalid Input! Numbers Only.\n" + RESET);
                sc.nextLine();

            } catch (Exception e) {

                System.out.println(RED + "\nUnexpected Error: " + e.getMessage() + RESET);
            }

        } 
        while (choice != 3);

        sc.close();
    
    }

    // ========= ADMIN MENU ===========
    static void adminMenu(Scanner sc, CrimeServices cs) 
    {

        int choice = 0;

    
        do
        {
         
        	try
        	{

                System.out.println(PURPLE + "\n══════════════════════════════════════════════════════" + RESET);
                System.out.println(CYAN + "                    👮 ADMIN MENU 👮" + RESET);
                System.out.println(PURPLE + "══════════════════════════════════════════════════════\n" + RESET);

                System.out.println(YELLOW + "   1. Add Crime" + RESET);
                System.out.println(YELLOW + "   2. View All Crimes" + RESET);
                System.out.println(YELLOW + "   3. View Particular Crime" + RESET);
                System.out.println(YELLOW + "   4. Assign Case to Police" + RESET);
                System.out.println(YELLOW + "   5. View Solved Cases" + RESET);
                System.out.println(YELLOW + "   6. Close Case" + RESET);
                System.out.println(YELLOW + "   7. Logout" + RESET);
                System.out.println(YELLOW + "   8. Update Crime" + RESET);
                System.out.println(YELLOW + "   9. View Police Details\n" + RESET);
                System.out.println(YELLOW + "   10. Add Punishment" + RESET);


                System.out.print(GREEN + "   Enter Choice : " + RESET);

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1 -> cs.addCrime();
                    case 2 -> cs.viewAllCrimes();
                    case 3 -> cs.viewParticularCrime(sc);
                    case 4 -> cs.assignCrimeToPolice();
                    case 5 -> cs.viewSolvedCrimes();
                    case 6 -> {
                    
                    	runLoading();
                        cs.closeCase();
                  
                    }
                    
                    case 8 -> cs.updateCrime(sc);
                    case 9 -> cs.viewAllPolice();
                    case 10 -> cs.addPunishment(sc);

                    case 7 -> {
                        runLoading();
                        System.out.println(GREEN + "\n* Logging Out...\n" + RESET);
                    }

                    default -> System.out.println(RED + "\n* Invalid Choice!\n" + RESET);
                }

            } catch (InputMismatchException e) {

                System.out.println(RED + "\n* Invalid Input! Numbers Only.\n" + RESET);
                sc.nextLine();

            } catch (Exception e) {

                System.out.println(RED + "\nUnexpected Error: " + e.getMessage() + RESET);
            }

        } while (choice != 7);
    }

    // ============ LOADING =========
    
    static void runLoading() throws InterruptedException
    {
      
    	Loading load = new Loading();
        
    	load.start();
        
    	load.join();
    
    }
}
