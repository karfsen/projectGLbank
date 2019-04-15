package mainpckg;

public class Card {

    private int id;
    private String accnum;
    private String PIN;
    private boolean active;
    private String expireDate;

    public Card(int id,String accnum,String PIN,boolean active,String expireDate){
        this.id=id;
        this.accnum=accnum;
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

    public String getAccnum() {
        return accnum;
    }

    public void setAccnum(String accnum) {
        this.accnum = accnum;
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
