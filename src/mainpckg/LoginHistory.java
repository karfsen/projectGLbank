package mainpckg;
//TODO TODO TODO TODO TODO
public class LoginHistory {
    private int id;
    private int idl;
    private String datetime;
    private boolean success;

    public LoginHistory(int id, int idl, String datetime,boolean success){
        this.id=id;
        this.idl=idl;
        this.datetime=datetime;
        this.success=success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
