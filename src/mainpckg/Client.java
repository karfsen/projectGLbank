package mainpckg;

public class Client {
    public String fname;
    public int id;
    public String lname;
    public String email;

    public Client(int id,String fname,String lname,String email) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
        this.email=email;
    }
    public Client(int id,String fname,String lname) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
    }
}
