package com.loading;

public class Loading extends Thread {

    // ===== COLOR CODES =====
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";

    public void run() {

        try {

            String animation = "|/-\\";
            System.out.print("\n" + CYAN + "   Processing " + RESET);

            for (int i = 0; i < 12; i++) {
                System.out.print("\r" + CYAN + "   Processing " 
                        + animation.charAt(i % animation.length()) + RESET);
                Thread.sleep(150);
            }

            System.out.print("\r" + GREEN + "   Completed Successfully     " + RESET);
            System.out.println("\n");

        } catch (InterruptedException e) {

            System.out.println("\nLoading Interrupted!");

        }
    }
}
