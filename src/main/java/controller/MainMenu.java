package controller;

import scanner.ScannerClass;

public class MainMenu {
    public static void showMenu() {
        int selectedNumber;
        while (true)
        {
            System.out.println("(1) Sign-In Customer\n" +
                    "(2) Sign-Up Customer\n" +
                    "(3) Create Account\n" +
                    "(4) End program");
            selectedNumber = ScannerClass.getNumber(1, 4);

            if (selectedNumber == 1) {

            } else if (selectedNumber == 2) {
                SignUpMenu.showMenu();
            } else if (selectedNumber == 3) {

            } else {
                break;
            }
        }
    }
}
