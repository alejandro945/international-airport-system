package model;

public class User extends Person implements  Comparable<User> {
    private String email;
    private String password;
    private UserRole role;

    public User(String name, String lastName, long id, String email, String password, UserRole role) {
        super(name, lastName, id);
        this.email = email;
        this.password = password;
        this.role = role;
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

    /**
     * @return UserRole
     */
    public UserRole getRole() {
        return this.role;
    }

    /**
     * @param role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int compareTo(User other) {
        return (int) (this.getId()-other.getId());
    }

    public int compareByRole(User other){
        return role.name().compareTo(other.getRole().name());
    }
}
