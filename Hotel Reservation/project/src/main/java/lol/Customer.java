package lol;
public class Customer {
    private String customer_name;
    private String customer_type;
    private int room_number;
    private int nights_stayed;

    public Customer() {
        this.room_number = 0;
        this.customer_name = "";
        this.customer_type = "";
        this.nights_stayed = 0;
    }

    
    public int getNumber() {
        return this.room_number;
    }
    public String getName() {
        return this.customer_name;
    }
    public String getType() {
        return this.customer_type;
    }
    public int getNightsStayed() {
        return this.nights_stayed;
    }

    public void setNumber(int n) {
        this.room_number = n;
    }
    public void setType(String s) {
        this.customer_type = s;
    }
    public void setName(String s) {
        this.customer_name = s;
    }
    public void setNightsStayed(int n) {
        this.nights_stayed = n;
    }

    public String toString() {
        return "Customer Name: "+ this.getName() + "\n" + 
        "Customer type: " + this.getType() + "\n" + 
        "Room Number: " + this.getNumber() + "\n" + 
        "Nights stayed: " + this.getNightsStayed();
    }
}
