package model;

public class Employee extends Person {
    private String email;
    private String password;
    private int id;

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

    /**
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

}
