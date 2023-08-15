package lol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HotelAdmin {
    private static final String url = "jdbc:mysql://localhost:3306/Omar";
    private static final String dbuser = "root";
    private static final String pw = "password123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,dbuser,pw);
    }

     
    private static boolean Boolean(int n) {
        if (n == 0) {
            return false;
        }
        else {
            return true;
        }
        //boolean method to change the 0 & 1 to true and false for UI experience
    }
    

    public static String readChoiceFromKeyboard(Scanner k){
        System.out.print("Please enter your choice: ");
        return k.nextLine();
    }

    public static void printAllRooms() {
        String selectSQL = "SELECT * FROM Hotel";
        try (Connection connection = HotelAdmin.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int roomId = resultSet.getInt("room_num");
                String roomType = resultSet.getString("room_type");
                String roomName = resultSet.getString("room_occupant");
                int roomStatus = resultSet.getInt("room_status");
                double roomPrice = resultSet.getDouble("room_price");

                System.out.println("Room Number: " + roomId);
                System.out.println("Occupant Name: " + roomName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Room Occupied: " + Boolean(roomStatus));
                System.out.println("Price Per Night: " + roomPrice);
                System.out.println("Occupant Name: " + roomName);
                System.out.println("--------------------------"); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public static void addRoom(Scanner k) {
        System.out.println("Room Number: ");
        int room_num = k.nextInt();
        k.nextLine();
    
        double room_price = 0;
        System.out.println("Room Type: ");
        System.out.println("   - Single");
        System.out.println("   - Double");
        System.out.println("   - Triple");
        System.out.println("   - Queen");
        System.out.println("   - Suite");
        System.out.println("   - Presidential");
        String room_type = k.nextLine().toLowerCase();
        if (room_type.equals("single")) {
            room_price = 100.00;
        } else if (room_type.equals("double")) {
            room_price = 150.00;
        } else if (room_type.equals("triple")) {
            room_price = 200.00;
        } else if (room_type.equals("queen")) {
            room_price = 145.00;
        } else if (room_type.equals("suite")) {
            room_price = 350.00;
        } else if (room_type.equals("presidential")) {
            room_price = 800.00;
        } else {
            System.out.println("Invalid input, defaulted room price to $300/night.");
            room_price = 300.00;
        }
        
        Room roomToAdd = new Room();
        roomToAdd.setNumber(room_num);
        roomToAdd.setType(room_type);
        roomToAdd.setPrice(room_price);

        String addSQL = "INSERT INTO HOTEL (room_num, room_type, room_status, room_price, room_occupant) VALUES (?, ?, 0, ?, NULL)";
        try (Connection connection = HotelAdmin.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(addSQL)) {
            preparedStatement.setInt(1, roomToAdd.getNumber());
            preparedStatement.setString(2, roomToAdd.getType());
            preparedStatement.setDouble(3, roomToAdd.getPrice());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public static void removeRoom(Scanner k) {
        System.out.println("Room Number: ");
        int room_num = k.nextInt();
        k.nextLine();

        String removeSQL = "DELETE FROM HOTEL WHERE room_num = ?";
        try (Connection connection = HotelAdmin.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(removeSQL)) {
            preparedStatement.setInt(1, room_num);
            
            int rowsAffected = preparedStatement.executeUpdate(); 
            if (rowsAffected > 0) {
                System.out.println("Room number " + room_num+ " was deleted successfully.");
            } else {
                System.out.println("Room number " + room_num + "was not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public static void modifyRoom(Scanner k) {
        System.out.println("Room number to be modified: ");
        int room_num = k.nextInt();
        k.nextLine();
        System.out.println("What would you like to change: ");
        System.out.println("1- Room Number");
        System.out.println("2- Room Type");
        String choice = k.nextLine();
        boolean loopDone = false;
        while (!loopDone) {
            if (choice.equals("1")) {
                System.out.println("New Room Number: ");
                int new_room_num = k.nextInt();
                k.nextLine();
                String modifySQL = "UPDATE Hotel SET room_num = ? WHERE room_num = ?";
                try (Connection connection = HotelAdmin.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
                    preparedStatement.setInt(1, new_room_num); 
                    preparedStatement.setInt(2, room_num);
                    int rowsAffected = preparedStatement.executeUpdate(); 
                    if (rowsAffected > 0) {
                        System.out.println("Room number " + room_num+ " was modified successfully.");
                    } else {
                        System.out.println("Room number " + room_num + "was not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); 
                }
                loopDone = true;
            }
            else if (choice.equals("2")) {
                System.out.println("New Room Type: ");
                String room_type = k.nextLine().toLowerCase();
                double room_price = 0;
                if (room_type.equals("single")) {
                    room_price = 100.00;
                } else if (room_type.equals("double")) {
                    room_price = 150.00;
                } else if (room_type.equals("triple")) {
                    room_price = 200.00;
                } else if (room_type.equals("queen")) {
                    room_price = 145.00;
                } else if (room_type.equals("suite")) {
                    room_price = 350.00;
                } else if (room_type.equals("presidential")) {
                    room_price = 800.00;
                } else {
                    System.out.println("Invalid input, defaulted room price to $300/night.");
                    room_price = 300.00;
                }

                String modifySQL = "UPDATE Hotel SET room_type = ?, room_price = ? WHERE room_num = ?";
                try (Connection connection = HotelAdmin.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(modifySQL)) {
                    preparedStatement.setString(1, room_type);
                    preparedStatement.setDouble(2, room_price);
                    preparedStatement.setInt(3, room_num);
                    int rowsAffected = preparedStatement.executeUpdate(); 
                    if (rowsAffected > 0) {
                        System.out.println("Room number " + room_num + " was modified successfully.");
                    } else {
                        System.out.println("Room number " + room_num + "was not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); 
                }
                loopDone = true;
            }
            else {
                System.out.println("Invalid Choice. Try again.");
                loopDone = false;
            }
        }
    }

    public static void main(String[] args ) {
        System.out.println("WELCOME HOTEL ADMIN");
        boolean run = true;
        System.out.println("Enter from list below:");
        System.out.println("1) Display complete Room List");
        System.out.println("2) Add Room to the Hotel System");
        System.out.println("3) Remove Room from the Hotel System");
        System.out.println("4) Modify Room Information");
        System.out.println("Q) Quit");
        Scanner user_input = new Scanner(System.in);
        String choice = "";
        while (run) {
            choice = readChoiceFromKeyboard(user_input);
            if (choice.equals("1")) {
                printAllRooms();
            }
            else if (choice.equals("2")) {
                addRoom(user_input);
            }
            else if (choice.equals("3")) {
                removeRoom(user_input);
            }
            else if (choice.equals("4")) {
                modifyRoom(user_input);
            }
            else if (choice.equals("q") || choice.equals("Q")) {
                run = false;
            }
            else {
                System.out.println("Invalid input. Here are our menu options for you to try again: ");
                System.out.println("1) Display complete Room List");
                System.out.println("2) Add Room to the Hotel System");
                System.out.println("3) Remove Room from the Hotel System");
                System.out.println("4) Modify Room Information");
                System.out.println("Q) Quit");
            }
        }
    }
}