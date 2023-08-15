package lol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HotelLobby {
    private static final String url = "jdbc:mysql://localhost:3306/Omar";
    private static final String username = "root";
    private static final String password = "password123";

    private static boolean Boolean(int n) {
        if (n == 0) {
            return false;
        }
        else {
            return true;
        }
        //boolean method to change the 0 & 1 to true and false for UI experience
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static String readChoiceFromKeyboard(Scanner k){
        System.out.print("Please enter your choice: ");
        return k.nextLine();
    }

    

    public static void printAllRooms() {
        String selectSQL = "SELECT * FROM Hotel";
        try (Connection connection = HotelLobby.getConnection();
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
                System.out.println("--------------------------"); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public static void checkIn(Scanner k) {
        System.out.println("Customer Name: ");
        String name = k.nextLine();

        System.out.println("Customer Type: ");
        String type = k.nextLine();

        System.out.println("Duration of Stay: ");
        int time_stayed = k.nextInt();
        k.nextLine();

        System.out.println("Room Number to admit to: ");
        int room_num = k.nextInt();
        k.nextLine();

        Customer customerToAdd = new Customer();
        customerToAdd.setName(name);
        customerToAdd.setType(type);
        customerToAdd.setNightsStayed(time_stayed);
        customerToAdd.setNumber(room_num);

        double room_price = searchRoom(room_num).getPrice();
        double totalPrice = customerToAdd.getNightsStayed() * room_price;

        try (Connection connection = HotelLobby.getConnection()) {
            String customerAddSQL = "INSERT INTO Customer (customer_name,customer_type,customer_room,nights_stayed,total_price) VALUES (?,?,?,?,?)";
            String editHotelSQL = "UPDATE Hotel SET room_status = 1, room_occupant = ? WHERE room_num = ?";
            try (PreparedStatement preparedStatement= connection.prepareStatement(customerAddSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, type);
                preparedStatement.setInt(3, room_num);
                preparedStatement.setInt(4, time_stayed);
                preparedStatement.setDouble(5, totalPrice);
                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement= connection.prepareStatement(editHotelSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, room_num);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
        
    public static void checkOut(Scanner k) {
        System.out.println("Room Number to check out: ");
        int room_num = k.nextInt();
        k.nextLine();
    
        String totalSQL = "SELECT total_price FROM Customer WHERE customer_room = ?";
        String removeSQL = "UPDATE Hotel SET room_status = 0, room_occupant = NULL WHERE room_num = ?";
        String remove2SQL = "DELETE FROM Customer WHERE customer_room = ?";
        
        try (Connection connection = HotelLobby.getConnection()) {
            try (PreparedStatement totalPreparedStatement = connection.prepareStatement(totalSQL)) {
                totalPreparedStatement.setInt(1, room_num);
                
                try (ResultSet resultSet = totalPreparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double totalPrice = resultSet.getDouble("total_price");
                        System.out.println("Room Number " + room_num + "'s total price: " + totalPrice);
                    }
                }
            }
            
            try (PreparedStatement removePreparedStatement = connection.prepareStatement(removeSQL)) {
                removePreparedStatement.setInt(1, room_num);
                removePreparedStatement.executeUpdate();
            }
            try (PreparedStatement removePreparedStatement = connection.prepareStatement(remove2SQL)) {
                removePreparedStatement.setInt(1, room_num);
                removePreparedStatement.executeUpdate();
                System.out.println("Room Number " + room_num + " has been checked out.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }     

    public static Room searchRoom(int room_num) {
        String searchSQL = "SELECT * FROM Hotel WHERE room_num = ?";
        try (Connection connection = HotelLobby.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
            preparedStatement.setInt(1, room_num); // Set the parameter value
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int roomNum = resultSet.getInt("room_num");
                    String roomType = resultSet.getString("room_type");
                    int roomStatus = resultSet.getInt("room_status");
                    double roomPrice = resultSet.getDouble("room_price");
                    //String roomOccupant = resultSet.getString("room_occupant");
                    Room roomToReturn = new Room();
                    roomToReturn.setNumber(roomNum);
                    roomToReturn.setType(roomType);
                    roomToReturn.setStatus(roomStatus);
                    roomToReturn.setPrice(roomPrice);
                    return roomToReturn;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void searchRoomStr(Scanner k) {
        System.out.println("Room Number: ");
        int room_num = k.nextInt();
        k.nextLine();
        String searchSQL = "SELECT * FROM Hotel WHERE room_num = ?";
        try (Connection connection = HotelLobby.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
            preparedStatement.setInt(1, room_num); // Set the parameter value
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int roomNum = resultSet.getInt("room_num");
                    String roomType = resultSet.getString("room_type");
                    int roomStatus = resultSet.getInt("room_status");
                    double roomPrice = resultSet.getDouble("room_price");
                    String roomOccupant = resultSet.getString("room_occupant");
                    String strToReturn = "Room Number: " + roomNum + "\n" +
                    "Room Type: " + roomType + "\n" +
                    "Room Status: " + Boolean(roomStatus) + "\n" +
                    "Price per Night: " + roomPrice + "\n" +
                    "Room Occupant: " + roomOccupant + "\n"+
                    "--------------";
                    System.out.println(strToReturn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("WELCOME HOTEL LOBBY");
        boolean run = true;
        System.out.println("Enter from list Below: ");
        System.out.println("1) Display complete Room List");
        System.out.println("2) Check-In Customer to Room");
        System.out.println("3) Check-Out Customer from Room");
        System.out.println("4) Search for a specific room");
        System.out.println("Q) Quit");
        Scanner user_input = new Scanner(System.in);
        String choice = "";
        while (run) {
            choice = readChoiceFromKeyboard(user_input);
            if (choice.equals("1")) {
                printAllRooms();
            }
            else if (choice.equals("2")) {
                checkIn(user_input);
            }
            else if (choice.equals("3")) {
                checkOut(user_input);
            }
            else if (choice.equals("4")) {
                searchRoomStr(user_input);
            } 
            else if (choice.equals("Q") || choice.equals("q")) {
                run = false;
            }
            else {
                System.out.println("Invalid input. Here are our menu options for you to try again: ");
                System.out.println("1) Display complete Room List");
                System.out.println("2) Check-In Customer to Room");
                System.out.println("3) Check-Out Customer from Room");
                System.out.println("4) Search for a specific room");
                System.out.println("Q) Quit");
            }
        }
    }

}
