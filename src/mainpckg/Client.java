package mainpckg;

import javafx.collections.ObservableArray;

public class Client {
    public String fname;
    public int id;
    public String lname;

    public Client(int id,String fname,String lname) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
    }
}
