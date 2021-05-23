package model;

public class User extends Person {
    private String email;
    private String password;

    public User(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id);
        this.email = email;
        this.password = password;
    }

    /**
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
