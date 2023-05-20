package pl.coderslab.entity;

public class User {

    private int id ;
   // private static int nextId =1;
    private String email;
    private String username;
    private String password;

    public User(){

    }

    public User(String email, String username, String password) {

        this.email = email;
        this.username = username;
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
    this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = UserDao.hashPassword(password);
       // String hashedPassword = UserDao.hashPassword(password);
    }

    public String printUser() {
        //String userData = String.format("Id: %s , email: %s, username: %s, password: %s", id, email, username, password);
        return "Id:" + id + ", email: " + email + ", username: " + username + ", password: " +  password;
    }
}
