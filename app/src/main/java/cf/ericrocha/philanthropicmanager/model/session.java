package cf.ericrocha.philanthropicmanager.model;

public class session {
    public String login;
    public String username;


    private static session uniqueInstance;

    private session() {
    }

    public static synchronized session getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new session();
        return uniqueInstance;
    }

    public void destroy(){
        uniqueInstance = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
