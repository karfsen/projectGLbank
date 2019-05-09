package mainpckg;

public class Card {

    private int id;
    private int accid;
    private String PIN;
    private boolean active;
    private String expireDate;

    public Card(int id,int accnum,String PIN,boolean active,String expireDate){
        this.id=id;
        this.accid=accnum;
        this.PIN=PIN;
        this.active=active;
        this.expireDate=expireDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccnum() {
        return accid;
    }

    public void setAccnum(int accnum) {
        this.accid = accnum;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
