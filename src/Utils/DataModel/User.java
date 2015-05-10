package Utils.DataModel;

public class User implements Comparable{
    private String userName;
    private int status;
    private String info;

    public User() {
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public String getUserName() {
        return userName;
    }

    public int getStatus() {
        return status;
    }
    public String getStatusString() {
        switch (status){
            case UserStatus.DISCONECTED:return "Disconected";
            case UserStatus.ONLINE:return "Online";
            case UserStatus.AWAY:return "Away";
            default:return null;
        }
    }

    @Override
    public int compareTo(Object o) {
        User u=(User) o;
        return this.userName.compareTo(u.userName);
    }

    @Override
    public String toString() {
        return userName + " ";
    }
}
