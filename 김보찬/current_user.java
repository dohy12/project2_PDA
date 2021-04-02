public class current_user{
    private String ID;
    private String name;
    private String telnum;
    private String email;
    private String group;
    private boolean admin;


    public boolean is_admin()
    {
        return admin;
    } 

    public String getgroup() {
        return group;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getTelnum() {
        return telnum;
    }

    public String getEmail() {
        return email;
    }
}