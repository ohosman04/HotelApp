package lol;

public class Room {
    private int room_number;
    private String room_type;
    private int room_booked;
    private double price_per_night;

    public Room() {
        this.room_number = 0;
        this.room_type = "";
        this.room_booked = 0;
        this.price_per_night = 0;
    }

    private boolean Boolean(int n) {
        if (n == 0) {
            return false;
        }
        else {
            return true;
        }
        //boolean method to change the 0 & 1 to true and false for UI experience
    }

    public int getNumber() {
        return this.room_number;
    }
    public String getType() {
        return this.room_type;
    }
    public int getStatus() {
        return this.room_booked;
    }
    public double getPrice() {
        return this.price_per_night;
    }

    public void setNumber(int n) {
        this.room_number = n;
    }
    public void setType(String s) {
        this.room_type = s;
    }
    public void setStatus(int n) {
        this.room_booked = n;
    }
    public void setPrice(double d) {
        this.price_per_night = d;
    }

    public String toString() {
        return "Room Number: "+ this.getNumber() + "\n" + 
        "Room type: " + this.getType() + "\n" + 
        "Price Per Night: " + this.getPrice() + "\n" + 
        "Room Booked: " + Boolean(this.getStatus());
    }
}
