package lol;

import java.util.Scanner;

public class Hotel {
    public static String readPasswordFromKeyboard(Scanner k){
        System.out.print("Password: ");
        return k.nextLine();
    }

    public static void main(String[] args) {
        Scanner user_input = new Scanner(System.in);
        System.out.println("WELCOME TO HOTEL RESERVATION SYSTEM");
        
        boolean loopDone = false;
        while (!loopDone) {
            String password = readPasswordFromKeyboard(user_input).toLowerCase();
            if (password.equals("admin123")) {
                HotelAdmin.main(args);
                loopDone = true;
            }
            else if (password.equals("lobby456")) {
                HotelLobby.main(args);
                loopDone = true;
            }
            else if (password.equals("q") || password.equals("quit")) {
                loopDone = true;
            }
            else {
                System.out.println("Invalid Password. Could not log in to Hotel system.");
            }
        }
    }
}
